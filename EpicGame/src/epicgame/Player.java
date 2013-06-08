package epicgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Player {
    
    private BufferedImage player_image=new BufferedImage(EpicGame.Height,EpicGame.Widht, BufferedImage.TYPE_INT_RGB);
    private boolean active=true;
    
    public int max_ammo;
    public int current_ammo;
    
    public long last_fired;
    
    public int x,y;
    public int height,width;
    
    Player(int x,int y,int height,int width,int max_ammo,BufferedImage a)
    {
        
        player_image=a;
        this.x=x;    
        this.y=y;  
        this.height=height;
        this.width=width;
        this.max_ammo=max_ammo;
        current_ammo=max_ammo;
           
    }
    
    
    
    
    void paint(Graphics a)
    {
            a.drawImage(player_image,x,y,height,width, null);  
    }
    
    void move(int offset_x,int offset_y)
    {
        if(active)
        {
            x+=offset_x;
            y+=offset_y;
        }
    }    
}
