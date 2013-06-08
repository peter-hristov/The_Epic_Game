package epicgame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class EpicGame extends Canvas implements Runnable {
    
    public boolean running=false;
    public int update_count;
    
    public static final int Widht=1000;
    public static final int Height=(Widht*9)/16;
   

    
    public static final String GameName="Epic Game!";
    
    private JFrame frame;
    
    public static Player player;
    
    private Lasers lasers;
    
    BufferedImage Background=new BufferedImage(Widht,Height, BufferedImage.TYPE_INT_RGB);
    BufferedImage laser_image=new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB);
   
    
    public InputHandler input;
    

    
    //private int pixels[]=((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    
    
    
    public EpicGame()
    {
        
        setMinimumSize(new Dimension(Widht,Height));
        setMaximumSize(new Dimension(Widht,Height));
        setPreferredSize(new Dimension(Widht,Height));
        
        frame=new JFrame(GameName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this,BorderLayout.CENTER);
        frame.pack();
   
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
        input=new InputHandler(this);
        
        frame.requestFocusInWindow();
        
        //BufferedImage Background=new BufferedImage(Widht,Height, BufferedImage.TYPE_INT_RGB);
        BufferedImage player_image=new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB);
        
        
        
        try {
            
            Background = ImageIO.read(new File("src\\epicgame\\images.jpg"));
            
            player_image = ImageIO.read(new File("src\\epicgame\\a.jpg"));
            
            laser_image = ImageIO.read(new File("src\\epicgame\\blue.png"));
             
        } catch (IOException ex) {
            Logger.getLogger(EpicGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        player=new Player(0,0,frame.getWidth()/15,frame.getHeight()/15,5,player_image);
        
        lasers=new Lasers(player.max_ammo,player,laser_image);
        
        
        
        System.out.println(10);
        
    
    }
    
    
    private synchronized void start()
    {
        running = true;
        
        new Thread(this).start();
            
    }
    
    private synchronized void stop()
    {
        running = false;
            
    }
    
    public void update()
    {
        
       int n=player.max_ammo;
        
        int i;
        
        //Controls handling
        if (input.up.is_pressed())
        {
            player.move(0,-1); 
        }
        if (input.down.is_pressed())
        {
            player.move(0,+1); 
        }
        if (input.left.is_pressed())
        {
           player.move(-1,0);
        }
        if (input.right.is_pressed())
        {
            player.move(+1,0);
        }
        
        

        if (input.space.is_pressed() && (System.currentTimeMillis() - player.last_fired > 100) )
        {
            player.last_fired=System.currentTimeMillis();
        
            lasers.get_free_laser();
        }
          
        lasers.advance_lasers();

        update_count++;
     
    }
    
    
    

    public void render()//This is where the shit happens!
    {
        //laser.visible=false;
        
        BufferStrategy bs=getBufferStrategy();
        
        if(bs==null)
        {
            createBufferStrategy(3);
            return;
        }
        

        Graphics a=bs.getDrawGraphics();
        a.setColor(Color.yellow);
    
               
        
        a.drawImage(Background, 0, 0, frame.getWidth(), frame.getHeight(),null);
       
        a.setFont(new Font("serif",10,40));
        a.drawString("Ammo : " + player.current_ammo, 0,frame.getHeight()-40);
       
        
      
        player.paint(a);
        
        lasers.paint_lasers(a);
        
        
        bs.show();
        a.dispose();
   
    }
    
    
    
    
    public static void main(String[] args) {
        EpicGame game=new EpicGame();
        game.start();
        
    }

    
    
    
    
    @Override
    public void run() {
        
        long lastTime=System.nanoTime();
        double ns_per_tick=1000000D; 
        
        int frames=0;
        int updates=0;
        long lastTimer=System.currentTimeMillis();
        double delta=0;
        
        while(running)
        {
            long now=System.nanoTime();
            delta+=(now-lastTime)/ns_per_tick;
            lastTime=now;
            boolean should_render=true;
            
            while(delta>=1)
            {
                updates++;
                update();
                render();
                delta--;
                should_render=true;
                
            }
            
         
            
            
            
            
            if(System.currentTimeMillis()-lastTimer >= 100)
            {
                lastTimer+=1000;
                System.out.println(frames + " " + updates);
                frames=0;
                updates=0;
                
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
    
    
  

    
    
    
}
