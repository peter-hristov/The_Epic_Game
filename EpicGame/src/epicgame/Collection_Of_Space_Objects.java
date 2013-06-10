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
    
    public int max_count;
    
    protected BufferedImage image=new BufferedImage(EpicGame.Height,EpicGame.Widht, BufferedImage.TYPE_INT_RGB);
    
    Space_Object m[];
    
    Player pl;
    
    
    
    
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
