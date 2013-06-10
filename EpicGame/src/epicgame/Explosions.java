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
public class Explosions {
    
    public Explosion m[];
    int e_count;
    
    public int w,h;
    
    public int image_w,image_h;
    
    protected BufferedImage image;
    
    int counter=0;
    int frames;
    int br=0;
    int delay;
    
    Explosions(int e_count,int w,int h,int image_w,int image_h, int delay,int frames,BufferedImage image)
    {
        this.e_count=e_count;
        m=new Explosion[e_count];
        
        this.w=w;
        this.h=h;
        
        this.image_w=image_w;
        this.image_h=image_h;
        
        this.delay=delay;
        this.frames=frames;
        
        this.image=image;
    }
    
    private int get_free_explosion()
    {
        int i;
        
        for( i=0;i<e_count;i++)
            if(m[i]==null)
                break;
        
        if(i==e_count)
            return -1;
        
        else return i;
        
        
    }
    
    public void spawn_explosion(int x,int y)
    {
        int i=get_free_explosion();
        if(i!=-1)
        {
            m[i]=new Explosion(x, y, w, h, image_w, image_h, delay, frames, image);
        }
    }
    
    public void paint(Graphics a)
    {
        for(int i=0;i<e_count;i++)
            if(m[i]!=null)
                m[i].paint(a);   
    }
    
    public void update()
    {
        for(int i=0;i<e_count;i++)
            if(m[i]!=null)
            {
                m[i].update();
                if(m[i].br==m[i].frames)
                    m[i]=null;
            }   
    }
    
    
    
}
