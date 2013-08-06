package cn.edu.ecnu;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.cassandra.db.SchedulerParameter;

public class Penalty extends SchedulerParameter 
{   
    String key_index;
    String userName;
    
    
    public Penalty(String str) {
        super(str);
    }
    
    public Penalty(String str, String userkey, String userName) {
        super(str);
        
        key_index = userkey.substring(userkey.length()-1);
        this.userName = userName;
    }
    
    public double getTotalPenalty()
    {
        return getQoSPenalty()+getQoDPenalty();
    }
    
    
    public double getQoSPenalty()
    {
        return QoS_preference*query_weight*getTardiness();
    }
    
    public double getQoDPenalty()
    {
        return (1-QoS_preference)*query_weight*getStaleness();
    }
    
    public double getTardiness()
    {
        long tardiness = local_finished_time- tardiness_deadline;
        return (tardiness>0)?tardiness:0;
    }
    
    public double getStaleness()
    {
        if (staleness_deadline==Long.MAX_VALUE)
        {
            return 0;
        }
        
        long staleness = local_finished_time-staleness_deadline;
        
        //....
        return (staleness>0)?staleness/1000d:0;
    }
    
    public String toprint()
    {
        StringBuffer sb = new StringBuffer();
        
        Field[] fields = this.getClass().getFields();

        for (int i = 0; i < fields.length; i++)
        {
            String varName = fields[i].getName();
            
            sb.append(varName);
            
            try
            {
                Object o = fields[i].get(this);

                sb.append("=");
                sb.append(o.toString());
                sb.append(",");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return sb.toString();
    }
    
    
    public ArrayList<String> getItem()
    {
        ArrayList<String> item = new ArrayList<String>();
        
        item.add(userName);
        item.add("image/"+ key_index + ".jpg");
        item.add(Long.toString(local_arrival_time));
        item.add(Long.toString(local_finished_time));
        
        if (first_unapplied_time == Long.MAX_VALUE)
        {
            item.add("NULL");
        }
        else {
            item.add(Long.toString(first_unapplied_time));
        }
        
        item.add(Double.toString(getQoSPenalty()));
        item.add(Double.toString(getQoDPenalty()));
        item.add(Double.toString(getTotalPenalty()));
        
        return item;
    }
}
