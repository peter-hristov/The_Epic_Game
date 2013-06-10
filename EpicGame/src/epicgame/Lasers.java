/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package epicgame;

import java.awt.image.BufferedImage;

/**
 *
 * @author Peter
 */
public class Lasers extends Collection_Of_Space_Objects{
    
        
   Player pl;
    
    Lasers(int max_count,int w,int h,int image_w,int image_h, int delay,int frames,Player pl,BufferedImage image)
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
        
        this.pl=pl;
    }
    
   
    public void spawn_laser()
    {
        int i=get_free_object_index();
        
        if(i!=-1)
        {
            m[i]=new Projectile(pl.x + pl.w, pl.y + pl.h/2 - h/2, w, h, image_w, image_h, delay, frames, image);
            pl.current_ammo--;
        }
    }
    
    
    public void advance_lasers()
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
                    pl.current_ammo++;      
                }
            }
       }
    } 
}


    