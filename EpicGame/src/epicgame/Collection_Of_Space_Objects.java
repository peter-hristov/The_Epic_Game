package epicgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Peter
 */
public class Collection_Of_Space_Objects {
    
        public Space_Object m[];
        public Space_Object sample;
        int max_count;
    
 
        Collection_Of_Space_Objects (int max_count,int w,int h,int frame_w,int frame_h, int delay,int frames,BufferedImage image)
        {
            this.max_count=max_count;
            m=new Space_Object[max_count];
            sample = new Space_Object(0,0,w,h,frame_w,frame_h,delay,frames,image);
        }

        
        
    public int get_free_object_index()
    { 
        int i;
        for( i=0 ; i<max_count ; i++)  
                if(m[i]==null)          
                    break;
            
        if(i!=max_count)
 
            return i;
         
        return -1;
    }
    
    
    public boolean spawn(int x,int y)
    {
        int i=get_free_object_index();
        if(i!=-1)
        {
            m[i]=new Space_Object(x, y, sample.w, sample.h, sample.frame_w, sample.frame_h, sample.delay, sample.frames, sample.image);
            return true;
        }
        return false;
    }
    
    public void update(int offset_x,int offset_y)
    {
        for(int i=0;i<max_count;i++)
            
            if(m[i]!=null)
            
                m[i].update(offset_x,offset_y);
          
    }
    
    
    public void paint(Graphics a)
    {
        for(int i=0 ; i < max_count ;i++)
            
            if(m[i]!=null)
                
                m[i].paint(a);   
    }
}