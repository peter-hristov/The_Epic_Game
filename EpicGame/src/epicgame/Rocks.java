/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package epicgame;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Peter
 */
public class Rocks extends Collection_Of_Space_Objects {
    
        
    Rocks(int max_count,int w,int h,int image_w,int image_h, int delay,int frames,BufferedImage image)
    {
        this.max_count=max_count;
        m=new Projectile[max_count];
        
        this.w=w;
        this.h=h;
        
        this.image_w=image_w;
        this.image_h=image_h;
        
        this.delay=delay;
        this.frames=frames;
        
        this.image=image;
    }
    
   
    public void spawn_rock()
    {
        int i=get_free_object_index();
        
        Random r=new Random();
        
        if(i!=-1)
        {
            m[i]=new Asteroid(EpicGame.Widht+100, r.nextInt(EpicGame.Height), w, h, image_w, image_h, delay, frames, image);
        }
    }
    
    
    public void advance_rocks()
    {
        int i;
        int n=max_count;
    
         for(i=0;i<n;i++)
       {
            if(m[i]!=null)
            {   
                m[i].update(1,0);
                
                if(m[i].x > EpicGame.Widht + 1000)
                {
                    m[i]=null;
                }
            }
       }
    } 
    }  
    

