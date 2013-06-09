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
    
    public int radius;
    int count;
    int c;
    
    public Rocks(int max_count, Player pl,BufferedImage image)
    {
        this.max_count=max_count;
        this.pl=pl;
        this.image=image;
        m=new Asteroid[max_count];  
        count=0;
        c=0;
    }
    
    
    public void get_free_astroid()
    {
        int i=get_free_object_index();
        
        if(i!=-1)
        {
            Random r=new Random();
            c++;
            m[i]=new Asteroid(EpicGame.frame.getWidth(), r.nextInt(EpicGame.frame.getHeight()) , 100 , 100 ,141, image);
            
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
                
                
                                    
                    m[i].move(-1,0);
             
                
                if(m[i].x < -100)
                {
                    m[i]=null;
                    EpicGame.score++;
                   
                    c--;
                    
                }
            }
       }
    }  
    
    
    
       
}
