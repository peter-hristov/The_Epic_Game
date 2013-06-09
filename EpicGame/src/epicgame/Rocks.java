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
public class Rocks extends Collection_Of_Space_Objects {
    
    public int radius;
    
    public Rocks(int max_count, Player pl,BufferedImage image)
    {
        this.max_count=max_count;
        this.pl=pl;
        this.image=image;
        m=new Asteroid[max_count];    
    }
    
    
    public void get_free_astroid()
    {
        int i=get_free_object_index();
        
        if(i!=-1)
        {
            System.out.println("omg");
            m[i]=new Asteroid(100, 100 , 100 , 100 ,50, image);
        }
              
    }
    
    
    
       
}
