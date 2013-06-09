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
    
       
   
    
    public Lasers(int max_count, Player pl,BufferedImage image)
    {
        this.max_count=max_count;
        this.pl=pl;
        this.image=image;
        m=new Projectile[max_count];    
    }
    
    
 
    public void get_free_projectile()
    {
        int i=get_free_object_index();
        
        if(i!=-1)
        {
            m[i]=new Projectile(pl.x +10, pl.y-20 , pl.height/3 , pl.width/3 , image);
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
                
                if(m[i].x > EpicGame.Widht + 100)
                {
                    m[i]=null;
                    pl.current_ammo++;      
                }
            }
       }
    } 
}
