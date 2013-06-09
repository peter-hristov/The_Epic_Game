package epicgame;

import java.awt.image.BufferedImage;


public class Player extends Space_Object{
    
  
    public int max_ammo;
    public int current_ammo;
    public long last_fired;
    

    Player(int x,int y,int height,int width,int max_ammo,BufferedImage a)
    {  
        
        image=a;
        active=true;
        
        this.x=x;    
        this.y=y;  
        
        this.height=height;
        this.width=width;
        
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
