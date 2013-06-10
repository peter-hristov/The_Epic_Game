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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class EpicGame extends Canvas implements Runnable {
    
    public volatile boolean running=false;
    public int update_count;
    
    public static final int Widht=1024;
    public static final int Height=(Widht*9)/16;
   
  
    public static final String GameName="Epic Game!";
    
    static public JFrame frame;
    

    private int max_player_ammo=10;
    private int max_ast_count=3;
    private int max_exp_count=50;
    
    private Player player;
    private Lasers lasers;
    private Rocks rocks;
    private Explosions explosions;
    
    BufferedImage Background=new BufferedImage(Widht,Height, BufferedImage.TYPE_INT_RGB);

    
    int br=0;
    int delay=0;
    
    
    public static int score=0;
    
       
    public InputHandler input;
    

    public double dist(int a,int b,int c,int d)
    {
        return Math.sqrt( (a-c)*(a-c) + (b-d)*(b-d) );
    }
    
    
    
    
    
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
        BufferedImage explosion_image=new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB);
        
        try {
            
            Background = ImageIO.read(new File("images\\background.jpg"));
            player_image = ImageIO.read(new File("images\\player.png"));
            laser_image = ImageIO.read(new File("images\\projectile.png"));
            asteroid_image = ImageIO.read(new File("images\\ast.png"));
            explosion_image = ImageIO.read(new File("images\\Splosion.png"));
           
        } catch (IOException ex) {
            Logger.getLogger(EpicGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        player=new Player(0,0,80,80,100,100,30,4,max_player_ammo,player_image);      
        
        lasers=new Lasers(player.max_ammo,55,20,55,20,30,7,player,laser_image);
        rocks=new Rocks(max_ast_count,100,100,64,64,30,64,asteroid_image);
        explosions=new Explosions(max_exp_count,100,100,64,64,30,13,explosion_image);
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
        player.update(0,0);
        
        if (input.up.is_pressed())
        {
            player.update(0,-1); 
        }
        if (input.down.is_pressed())
        {
            player.update(0,+1); 
        }
        if (input.left.is_pressed())
        {
           player.update(-1,0);
        }
        if (input.right.is_pressed())
        {
            
            player.update(+1,0);
           
        }
        
        //Space
        if (input.space.is_pressed() && ( System.currentTimeMillis() - player.getLast_fired() ) > 100 )
        {
            player.last_fired=System.currentTimeMillis();
            lasers.spawn_laser();
        }
        
        Random r=new Random();
        if(update_count%200==0)
            rocks.spawn_rock(1000,r.nextInt(800) );

        lasers.advance_lasers();
        rocks.update();
        explosions.update();
        
        detect_laser_rock_collision();
        detect_player_rock_collision();
        
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
        //a.drawImage(rocks.image, 0, 0, 4000, 64,null);
        a.setColor(Color.yellow);
       
        //Painting important stuff
        player.paint(a);
        lasers.paint(a);
        rocks.paint(a);
        explosions.paint(a);
        
            
        
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
                System.out.println("Frames : " + frames + " | Updates : " + updates);
                frames=0;
                updates=0;    
            }
        }    
    }
    
    
    void detect_laser_rock_collision()
    {
        for(int i=0 ; i < lasers.max_count ; i++)
        {
            if(lasers.m[i]!=null)
            {
                for(int j=0 ; j<rocks.max_count ; j++)
                    
                    if(rocks.m[j]!=null)
                    {
                        double d=dist(  lasers.m[i].x+lasers.m[i].w,
                                        
                                        lasers.m[i].y+lasers.m[i].h/2, 
                                        
                                        rocks.m[j].x + rocks.m[j].w/2, 
                                        
                                        rocks.m[j].y + rocks.m[j].h/2);
                
                
                        if( d < 70 )
                        {
                           explosions.spawn_explosion(rocks.m[j].x, rocks.m[j].y);
                           
                           lasers.m[i]=null;
                           rocks.m[j]=null;
                           
                           player.current_ammo++;
                           score-=1;
                           
                           break;
                        }
                   }
            }
        }    
    }
    
    void detect_player_rock_collision()
    {
        for(int i=0 ; i<rocks.max_count ; i++)
        {            
            if( rocks.m[i] != null )
            {
                double d=dist(  player.x+player.w/2,
                                
                                player.y+player.h/2,
                                
                                rocks.m[i].x + rocks.m[i].w/2,
                                
                                rocks.m[i].y + rocks.m[i].h/2);
                
                if( d < 70 )
                {
                    //player=null;
                    running = false;
                    return;
                }       
            }
        }
    }    
}
