package epicgame;

import java.awt.image.BufferedImage;


public class Player extends Space_Object{
    
    public int max_ammo;
    public int current_ammo;
    public long last_fired;
   
    Player (int x,int y,int w,int h,int image_w,int image_h, int delay,int frames,int max_ammo,BufferedImage image)
    {
        this.x=x;
        this.y=y;
        
        this.w=w;
        this.h=h;
        
        this.frame_w=image_w;
        this.frame_h=image_h;
        
        this.delay=delay;
        this.frames=frames;
        
        this.image=image;
        
        this.max_ammo=max_ammo;
        current_ammo=max_ammo;     
    }

    public int getMax_ammo() {
        return max_ammo;
    }

    public int getCurrent_ammo() {
        return current_ammo;
    }

    public long getLast_fired() {
        return last_fired;
    }

    
}
