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
public class Lasers {
    
    public int max_lasers;
    
    private BufferedImage projectile_image=new BufferedImage(EpicGame.Height,EpicGame.Widht, BufferedImage.TYPE_INT_RGB);
     
    Player pl;
    
    Projectile m[];
    
    public Lasers(int n, Player pl,BufferedImage a)
    {
        max_lasers=n;
        this.pl=pl;
        projectile_image=a;
        
        m=new Projectile[max_lasers];    
    }
    
    
   
    
    public void get_free_laser()
    {
        int n=max_lasers;
        int i;
        
        for(i=0;i<n;i++)  
                if(m[i]==null)          
                    break;
            
           if(i!=n)
           {
               m[i]=new Projectile(pl.x , pl.y , pl.height/3 , pl.width/3 , projectile_image);
               pl.current_ammo--;
           }
              
    }
    
    
    public void advance_lasers()
    {
        int i;
        int n=max_lasers;
    
         for(i=0;i<n;i++)
       {
            if(m[i]!=null)
            {   
                m[i].advance(1);
                
                if(m[i].x > EpicGame.Widht + 100)
                {
                    m[i]=null;
                    pl.current_ammo++;      
                }
            }
       }
    }
    
    public void paint_lasers(Graphics a)
    {
        int i;
        int n=max_lasers;
        
        for(i=0 ; i < n;i++)
            
            if(m[i]!=null)
                
                m[i].paint(a);   
    }
    
}
