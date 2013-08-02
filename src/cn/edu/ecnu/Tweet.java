package cn.edu.ecnu;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.SuperColumn;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;


public class Tweet
{
    private String userName;
    private Image userImage;
    
    private String tweetID;
    private String tweetContent;
    public long tweetPosttime;
    
    
    public String getTweetId() {
        return tweetID;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public Image getUserImage()
    {
        return userImage;
    }
    
    public String getTweetContent()
    {
        return tweetContent;
    }
    
    public String getTweetPosttime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = new Date(tweetPosttime);
        
        return sdf.format(dt);
    }
    
    public Tweet()
    {
        
    }
    
    public Tweet(String tweetID, String userName, Image userImage, String tweetContent, long tweetPosttime)
    {
        this.tweetID = tweetID;
        this.userName = userName;
        this.userImage = userImage;
        this.tweetContent = tweetContent;
        this.tweetPosttime = tweetPosttime;
    }
    
    public Tweet(SuperColumn supercol)
    {
        List<Column> colList = supercol.getColumns();
        
        try
        {
            tweetID = ByteBufferUtil.string(ByteBufferUtil.clone(supercol.name));
        
            for (Column column : colList)
            {
                String colname = new String();
               
                colname = ByteBufferUtil.string(ByteBufferUtil.clone(column.name));
                
                if (colname.equals(Schema.userName))
                {
                    userName = ByteBufferUtil.string(column.value);
                }

                if (colname.equals(Schema.userImage))
                {
                    byte [] data = ByteBufferUtil.getArray(column.value);
                    ByteArrayInputStream byteinput = new ByteArrayInputStream(data);

                    
                    userImage = new Image(Display.getCurrent(), byteinput);
                }
                
                if (colname.equals(Schema.tweetContent))
                {
                    tweetContent = ByteBufferUtil.string(column.value);
                }
                
                if (colname.equals(Schema.tweetPosttime))
                {
                    ByteBuffer buffer = ByteBuffer.allocate(8);
                    buffer.put(column.getValue());
                    buffer.flip();//need flip 
                    tweetPosttime = buffer.getLong();
                }
                
                
            }
        }
        catch (CharacterCodingException e)
        {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }
}
