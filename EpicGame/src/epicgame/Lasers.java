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
    
       
   int w,h;
    
   public Lasers(int max_count, int w,int h,Player pl,BufferedImage image)
   {
        this.max_count=max_count;
        this.pl=pl;
        this.image=image;
        m=new Projectile[max_count];
        
        this.w=w;
        this.h=h; 
    }
    
    
 
    public void spawn_laser()
    {
        int i=get_free_object_index();
        
        if(i!=-1)
        {
            m[i]=new Projectile(pl.x + pl.width, pl.y + pl.height/2 - h/2 , w , h , image);
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
                m[i].move(1,0);
                
                if(m[i].x > EpicGame.Widht + 1000)
                {
                    m[i]=null;
                    pl.current_ammo++;      
                }
            }
       }
    } 
}
