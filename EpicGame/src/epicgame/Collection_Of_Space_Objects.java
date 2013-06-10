/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package epicgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Peter
 */
public class Collection_Of_Space_Objects {
    
        public Space_Object m[];
        int max_count;
    
        public int w,h;
    
        public int image_w,image_h;
    
        protected BufferedImage image;
    
        int counter=0;
        int frames;
        int br=0;
        int delay;
    
    
    
        Collection_Of_Space_Objects (int max_count,int w,int h,int image_w,int image_h, int delay,int frames,BufferedImage image)
        {
            this.max_count=max_count;
            m=new Space_Object[max_count];
        
            this.w=w;
            this.h=h;
        
            this.image_w=image_w;
            this.image_h=image_h;
        
            this.delay=delay;
            this.frames=frames;
        
            this.image=image;
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
            m[i]=new Space_Object(x, y, w, h, image_w, image_h, delay, frames, image);
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