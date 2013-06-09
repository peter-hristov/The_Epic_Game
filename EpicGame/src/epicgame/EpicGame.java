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
    
    public volatile boolean running=false;
    public int update_count;
    
    public static final int Widht=1200;
    public static final int Height=(Widht*9)/16;
   
  
    public static final String GameName="Epic Game!";
    
    static public JFrame frame;
    
    private Player player;
    private int max_player_ammo=3;
    private int max_ast_count=8;
    
    private Lasers lasers;
    private Rocks rocks;
    
    BufferedImage Background=new BufferedImage(Widht,Height, BufferedImage.TYPE_INT_RGB);
    
    public static int score=0;
    
   
    
    public InputHandler input;
    

    public double dist(int a,int b,int c,int d)
    {
        return Math.sqrt( (a-c)*(a-c) + (b-d)*(b-d) );
    }
    
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
        
         
        BufferedImage player_image=new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB);
        BufferedImage laser_image=new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB);
        BufferedImage asteroid_image=new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB);
        
        try {
            Background = ImageIO.read(new File("images\\background.jpg"));     
            player_image = ImageIO.read(new File("images\\player.jpg"));
            laser_image = ImageIO.read(new File("images\\projectile.png"));
            asteroid_image = ImageIO.read(new File("images\\asteroid.jpg")); 
        } catch (IOException ex) {
            Logger.getLogger(EpicGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        player=new Player(0,0,frame.getWidth()/15,frame.getHeight()/15,max_player_ammo,player_image);      
        lasers=new Lasers(player.max_ammo,player.width/3,player.height/3,player,laser_image);
        rocks=new Rocks(max_ast_count,player,asteroid_image);
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
               
        //Controls handling
        //Player movement
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
        
        //Space
        if (input.space.is_pressed() && ( System.currentTimeMillis() - player.getLast_fired() ) > 100 )
        {
            player.last_fired=System.currentTimeMillis();
        
            lasers.get_free_projectile();
        }
        
        if(update_count%300==0)
        
            rocks.get_free_astroid();

        
        lasers.advance_lasers();
        rocks.advance_rocks();
        
        for(int i=0 ; i < lasers.max_count ; i++)
        {
            if(lasers.m[i]!=null)
            {
                for(int j=0 ; j<rocks.max_count ; j++)
                    
                    if(rocks.m[j]!=null)
                    {
                        double d=dist(lasers.m[i].x+lasers.m[i].width, lasers.m[i].y+lasers.m[i].height/2, rocks.m[j].x + rocks.m[j].width/2, rocks.m[j].y + rocks.m[j].height/2);
                
                
                        if( d < 70 )
                        {
                           lasers.m[i]=null;
                           rocks.m[j]=null;
                           player.current_ammo++;
                           
                           break;
                        }
                        
                   }
            }
        }
        
        for(int i=0;i<rocks.max_count;i++)
        {
            
            if(rocks.m[i]!=null)
            {
                double d=dist(player.x+player.width/2, player.y+player.height/2, rocks.m[i].x + rocks.m[i].width/2, rocks.m[i].y + rocks.m[i].height/2);
                //System.out.println("Distance between " + i + " is " + d);
                
                if( d < 70 )
                    
                    player=null;       
            }
        }
        
        update_count++;
     
    }
    
    
    

    public void render()//This is where the shit happens!
    {
        
        
        BufferStrategy bs=getBufferStrategy();
        
        if(bs==null)
        {
            createBufferStrategy(3);
            return;
        }
        

        Graphics a=bs.getDrawGraphics();
       
        //BACKGROUND
        a.drawImage(Background, 0, 0, frame.getWidth(), frame.getHeight(),null);
        a.setColor(Color.yellow);
       
       
       
        
        //Painting important stuff
        player.paint(a);
        lasers.paint(a);
        rocks.paint(a);
        
         //TEXT AND STUFF 
        a.setFont(new Font("serif",10,40));
        a.drawString("Ammo : " + player.current_ammo, 0,frame.getHeight()-40);
        a.drawString("Score : " + score,300,frame.getHeight()-40);
        
        //The end
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
        double ns_per_tick=1000000D/60D; 
        
        int frames=0;
        int updates=0;
        
        long lastTimer=System.currentTimeMillis();
        
        double delta=0;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(EpicGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(running)
        {
            long now=System.nanoTime();
            delta+=(now-lastTime)/ns_per_tick;
            lastTime=now;
 
            delta=5;
            while(delta>=1)
            {
                updates++;
                update();
                delta--; 
            }
            
            render();
            frames++;
  
            if(System.currentTimeMillis()-lastTimer >= 1000)
            {
                lastTimer+=1000;
                System.out.println(frames + " " + updates);
                frames=0;
                updates=0;    
            }
        }    
    }
    
    

    
    
    
    
    
    
  

    
    
    
}
