package cn.edu.ecnu;


import inter.ResultPanel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;


import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.SuperColumn;
import org.apache.cassandra.utils.ByteBufferUtil;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;

import cn.edu.ecnu.util.distributiongenerator.ExponentialGenerator;
import cn.edu.ecnu.util.distributiongenerator.UniformGenerator;


public class GlobalTimelineGenerator extends Thread
{    
    private AtomicInteger mid = new AtomicInteger(0);
    
    private List<String> userNameList;
    private List<String> tweetContentList;
    private List<Double> tweetGenRateList;
    
    private ThreadGroup g;
    
    public GlobalTimelineGenerator()
    {
        userNameList = new ArrayList<String>();
        tweetContentList = new ArrayList<String>();
        tweetGenRateList = new ArrayList<Double>();
        
        loadUserList("userlist.txt");
        loadContentList("contentlist.txt");
    }
    
    public void loadContentList(String fileName)
    {
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null) {

                tweetContentList.add(line);
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadUserList(String fileName) 
    {
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null) {

                StringTokenizer tokenizer = new StringTokenizer(line, "\t");
                
                while (tokenizer.hasMoreTokens())
                {
                    userNameList.add(tokenizer.nextToken());
                    tweetGenRateList.add(Double.parseDouble(tokenizer.nextToken()));    
                }
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void gen(ResultPanel resultPanel) 
    {
        String groupName = "followees";
        
        g = new ThreadGroup(groupName);
        for (int i = 0; i < userNameList.size(); i++)
        {            
            Thread thread = new Thread(g, new WriteRunnable(i,resultPanel));
            
            thread.start();
        }
    }
    
    public void end() {
        g.interrupt();
        System.out.println("-ending");
    }
    
    
    class WriteRunnable implements Runnable {
        private String key;
        private String userName;
        private String userImagepath;
        private String tweetContent;
        private String tweetID;
        
        private double tweetGenRate;
        
        private ResultPanel resultPanel;
        
        public WriteRunnable(int userkey,ResultPanel resultPanel)
        {
            key = "user"+userkey;
            userName = userNameList.get(userkey);
            userImagepath = userkey+".jpg";
            
            tweetContent = tweetContentList.get(UniformGenerator.nextInt(0, tweetContentList.size()-1));
            
            tweetGenRate = tweetGenRateList.get(userkey);
            
            this.resultPanel = resultPanel;
        }
        
        //tweets gen
        public Tweet tweetsGen()
        {
            Tweet tweet = null;
            
            TTransport tr = new TFramedTransport(new TSocket("localhost", 9160));
            TProtocol proto = new TBinaryProtocol(tr);
            Cassandra.Client client = new Cassandra.Client(proto);
            
            try {
                tr.open();

                client.set_keyspace(Schema.keyspace);
            
                long timestamp = System.currentTimeMillis();                
                
                SuperColumn supercol = new SuperColumn();
                
                int messageid = mid.getAndIncrement();
                tweetID = "tweetID_" + messageid;
                supercol.setName(ByteBufferUtil.bytes(tweetID));

                // user name
                Column nameColumn = new Column(ByteBufferUtil.bytes(Schema.userName));
                nameColumn.setValue(ByteBufferUtil.bytes(userName));
                nameColumn.setTimestamp(timestamp);
                supercol.addToColumns(nameColumn);
                
                // user image
                Column imageColumn = new Column(ByteBufferUtil.bytes(Schema.userImage));
                final Image image = new Image(Display.getDefault(), userImagepath);
               
                ImageLoader loader = new ImageLoader();
                loader.load(userImagepath);
                
                
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                
                loader.save(outStream, SWT.IMAGE_JPEG);
                ByteBuffer buf = ByteBuffer.wrap(outStream.toByteArray());
                
                
                imageColumn.setValue(buf);
                imageColumn.setTimestamp(timestamp);
                supercol.addToColumns(imageColumn);
                
                // tweet content
                Column tweetContentColumn = new Column(ByteBufferUtil.bytes(Schema.tweetContent));
                tweetContentColumn.setValue(ByteBufferUtil.bytes(tweetContent));
                tweetContentColumn.setTimestamp(timestamp);
                supercol.addToColumns(tweetContentColumn);
                
                // tweet post time
                Column tweetPosttimeColumn = new Column(ByteBufferUtil.bytes(Schema.tweetPosttime));
                long currentTime = System.currentTimeMillis();
                tweetPosttimeColumn.setValue(ByteBufferUtil.bytes(currentTime));
                tweetPosttimeColumn.setTimestamp(timestamp);
                supercol.addToColumns(tweetPosttimeColumn);
                
                
                ColumnOrSuperColumn col_or_supercol = new ColumnOrSuperColumn();
                col_or_supercol.setSuper_column(supercol);
                
                Mutation mutation = new Mutation();
                mutation.setColumn_or_supercolumn(col_or_supercol);
                
                List<Mutation> mutations = Arrays.asList(mutation);
                
                Map<String, List<Mutation>> colfamily = new HashMap<String, List<Mutation>>();
                colfamily.put(Schema.columnfamily, mutations);
                
                Map<ByteBuffer, Map<String, List<Mutation>>> mutation_map = 
                        new HashMap<ByteBuffer, Map<String,List<Mutation>>>();
                
                mutation_map.put(ByteBufferUtil.bytes(key), colfamily);
                
                client.batch_mutate(mutation_map, ConsistencyLevel.ONE);
                
                tr.close();
                
                tweet = new Tweet(tweetID, userName, image, tweetContent, currentTime);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
            return tweet;
        }
        
        public void run()
        {
            while(true) {
                final Tweet newTweet = tweetsGen();
                Display.getDefault().asyncExec(new Runnable()
                {
                    
                    @Override
                    public void run()
                    {
                        resultPanel.updateResultList(newTweet);
//                        System.out.println("....");
                    }
                });
               
                System.out.println("generating");
                
                try
                {
                    sleep(ExponentialGenerator.nextInt(tweetGenRate/1000d));
                }
                catch (InterruptedException e)
                {
                    return;
                }
            }
        }
    }
    
}
