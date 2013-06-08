package epicgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Player {
    
    public BufferedImage player_image=new BufferedImage(EpicGame.Height,EpicGame.Widht, BufferedImage.TYPE_INT_RGB);
    
    int x,y;
    
    Player(int x,int y,BufferedImage a)
    {
        
        player_image=a;
        this.x=x;    
        this.y=y;
        
    }
    
    void paint(Graphics a)
    {
        a.drawImage(player_image,x,y,100,60, null);
        
    }
    
    
}
