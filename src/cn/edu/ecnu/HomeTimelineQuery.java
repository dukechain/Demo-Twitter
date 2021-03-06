package cn.edu.ecnu;

import java.nio.ByteBuffer;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.antlr.grammar.v3.ANTLRv3Parser.element_return;
import org.apache.cassandra.thrift.Agreement_parameters;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.thrift.SuperColumn;
import org.apache.cassandra.thrift.Cassandra.Processor.system_add_column_family;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;


public class HomeTimelineQuery
{
    private final static int num_followees = 4;
    
    AtomicInteger count;
    
    CopyOnWriteArrayList<Tweet> hometimeline;
    
    CopyOnWriteArrayList<Penalty> penalties;
    
    //scheduler parameter
    Agreement_parameters para;
    
    public HomeTimelineQuery()
    {
        count = new AtomicInteger(num_followees);
        hometimeline = new CopyOnWriteArrayList<Tweet>();
        
        penalties = new CopyOnWriteArrayList<Penalty>();
    }
    
    public HomeTimelineQuery(List<String> paraList)
    {
        this();
        
        
        para = new Agreement_parameters(Long.parseLong(paraList.get(0)), 
                Long.parseLong(paraList.get(1))*1000);
        
        if (paraList.size() > 2)
        {
            para.setQoS_preference(Double.parseDouble(paraList.get(2)));
            para.setQuery_weight(Double.parseDouble(paraList.get(3)));
        }

    }
    
    public double getTotalPenalty()
    {
        double total = Double.MIN_VALUE;
        
        for (Penalty p : penalties)
        {
            double temp = p.getTotalPenalty();
            if (temp > total)
            {
                total = temp;
            }
        }
        
        return total;
    }
    
    public List<Penalty> getpenalties()
    {
        List<Penalty> penaltyList = new ArrayList<Penalty>();
        
        for (Penalty penalty : penalties)
        {
            penaltyList.add(penalty);
        }
        
        return penaltyList;
    }
    
    public List<Tweet> query() {
        for (int i = 0; i < num_followees; i++)
        {
            ReadRunnable read = new ReadRunnable("user"+i);
            Thread notifyThread = new Thread(read);
            notifyThread.start();
        }
        
        
        synchronized (count) {
            long waitTime = System.currentTimeMillis();
            while (count.get() != 0) {
                //System.out.println(getName() + " begin waiting!");
                
                try {
                    count.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            }
            waitTime = System.currentTimeMillis() - waitTime;
            System.out.println("hometimeline query time: " + waitTime+"ms");
            //System.out.println(getName() + " end waiting!");
        }
        
        List<Tweet> tweets = new ArrayList<Tweet>();
        
        for (Tweet tweet : hometimeline)
        {
            tweets.add(tweet);
        }
        
        Collections.sort(tweets, new Comparator<Tweet>()
        {
            @Override
            public int compare(Tweet o1, Tweet o2)
            {
                return (int)(o1.tweetPosttime-o2.tweetPosttime);
            }
        });
        
        int fromindex = (tweets.size()-11)>0? tweets.size()-11 : 0;
        List<Tweet> peformtweets = tweets.subList(fromindex, tweets.size());
        
        return peformtweets;
    }

    /*public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Thread Run!");
        NotifyTest test = new NotifyTest();
        
        test.run();
        NotifyThread notifyThread = test.new NotifyThread("notify01");
        WaitThread waitThread01 = test.new WaitThread("waiter01");
        WaitThread waitThread02 = test.new WaitThread("waiter02");
        WaitThread waitThread03 = test.new WaitThread("waiter03");
        notifyThread.start();
        waitThread01.start();
        waitThread02.start();
        waitThread03.start();
    }*/
    
    
    
    class ReadRunnable implements Runnable {
        String key;
        
        public ReadRunnable(String key)
        {
            this.key = key;
        }
        
        public void run() {
            try {
                TTransport tr = new TFramedTransport(new TSocket("localhost", 9160));
                TProtocol proto = new TBinaryProtocol(tr);
                Cassandra.Client client = new Cassandra.Client(proto);
                tr.open();
                
                client.set_keyspace(Schema.keyspace);      
                ColumnParent parent = new ColumnParent(Schema.columnfamily);
                
                SlicePredicate predicate = new SlicePredicate();
                SliceRange sliceRange = new SliceRange(ByteBuffer.wrap(new byte[0]), 
                        ByteBuffer.wrap(new byte[0]), true, 10);
                
               /* SliceRange sliceRange = new SliceRange(
                        ByteBufferUtil.bytes(System.currentTimeMillis()), 
                        ByteBuffer.wrap(new byte[0]), true, 10);*/
                predicate.setSlice_range(sliceRange);
                
                //Agreement_parameters para = new Agreement_parameters();
                
                
                
               /* List<ColumnOrSuperColumn> results = client.get_slice(
                        ByteBufferUtil.bytes(key), parent, predicate, ConsistencyLevel.ONE);*/
                
                List<ColumnOrSuperColumn> results = client.get_slice_Q(
                        ByteBufferUtil.bytes(key), parent, predicate, ConsistencyLevel.ONE,
                        para);
                
                if (results.size() > 1)
                {
                    SuperColumn schedulerColumn = null;
                    String username = null;
                    
                    for (ColumnOrSuperColumn result : results)
                    {
                        SuperColumn supercolumn = result.super_column;
                        
                        String nameString = null;
                        try {
                            nameString = ByteBufferUtil.string(
                                    ByteBufferUtil.clone(supercolumn.name));
                        }
                        catch(MalformedInputException me)
                        {
                            nameString = String.valueOf(ByteBufferUtil.toLong(
                                    ByteBufferUtil.clone(supercolumn.name)));
                        }
                        
                        
                        if (!nameString.equals("scheduler"))
                        {
                            Tweet tweet = new Tweet(supercolumn);
                            hometimeline.add(tweet);
                            
                            username = tweet.getUserName();
                        }         
                        else {
                            /*Column schedulerCol = supercolumn.getColumns().get(0);
                            String schedulerStr = ByteBufferUtil.string(
                                    ByteBufferUtil.clone(schedulerCol.value));
                            
                            Penalty penalty = new Penalty(schedulerStr);
                            
                            penalties.add(penalty);*/
                            
                            schedulerColumn = supercolumn;
                        }
                        //System.out.println(toString(column.name) + " -> " + toString(column.value));
                    }
    
                    
                    Column schedulerCol = schedulerColumn.getColumns().get(0);
                    String schedulerStr = ByteBufferUtil.string(
                            ByteBufferUtil.clone(schedulerCol.value));
                    
                    Penalty penalty = new Penalty(schedulerStr, key, username);
                    
                    penalties.add(penalty);
                }
                else {
                    /*Column schedulerCol = results.get(0).column;
                    
                    String schedulerStr = ByteBufferUtil.string(
                            ByteBufferUtil.clone(schedulerCol.value));
                    
                    Penalty penalty = new Penalty(schedulerStr, key, username);
                    
                    penalties.add(penalty);*/
                }
                
                
                tr.close();
                
                
                //System.out.println(getName()+" is running");
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            synchronized (count) {                
                count.decrementAndGet();
                count.notify();
            }
        }
    };
}
