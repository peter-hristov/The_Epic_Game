package epicgame;

import java.awt.image.BufferedImage;


public class Player extends Space_Object{
    
  
    public int max_ammo;
    public int current_ammo;
    public long last_fired;
    

    Player(int x,int y,int width,int height,int max_ammo,BufferedImage a)
    {  
        
        image=a;
        
        this.x=x;    
        this.y=y;  
        
        this.width=width;
        this.height=height;
        
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
