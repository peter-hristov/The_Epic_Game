/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package epicgame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Peter
 */
public class Player {
    
    public BufferedImage player_image=new BufferedImage(EpicGame.Height,EpicGame.Widht, BufferedImage.TYPE_INT_RGB);
    
    public Point loc=new Point();
    
    Player(int x,int y,BufferedImage a)
    {
        
        player_image=a;
        loc.x=x;    
        loc.y=y;
        
    }
    
    void paint(Graphics a)
    {
        a.drawImage(player_image,loc.x,loc.y, null);
        
    }
    
    
}
