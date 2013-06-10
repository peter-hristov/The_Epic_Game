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
public abstract class Collection_Of_Space_Objects {
    
        public Space_Object m[];
        int max_count;
    
        public int w,h;
    
        public int image_w,image_h;
    
        protected BufferedImage image;
    
        int counter=0;
        int frames;
        int br=0;
        int delay;
    
    
    
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
    
    
    
    public void paint(Graphics a)
    {
        for(int i=0 ; i < max_count ;i++)
            
            if(m[i]!=null)
                
                m[i].paint(a);   
    }
}
