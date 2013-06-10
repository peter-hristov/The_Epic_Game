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
    public boolean game_over=false;
    int w=0;
    
    public static final int Width=1024;
    public static final int Height=(Width*9)/16;
   
  
    public static final String GameName="Epic Game!";
    
    static public JFrame frame;
    

    private int max_player_ammo=10;
    private int max_ast_count=5;
    private int max_exp_count=6;
    
    private Player player;
    private Collection_Of_Space_Objects lasers;
    private Collection_Of_Space_Objects rocks;
    private Collection_Of_Space_Objects explosions;
    
    BufferedImage Background=new BufferedImage(Width,Height, BufferedImage.TYPE_INT_RGB);

    
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
        setMinimumSize(new Dimension(Width,Height));
        setMaximumSize(new Dimension(Width,Height));
        setPreferredSize(new Dimension(Width,Height));
        
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
            
            Background = ImageIO.read(new File("images\\bg.jpg"));
            player_image = ImageIO.read(new File("images\\player.png"));
            laser_image = ImageIO.read(new File("images\\projectile.png"));
            asteroid_image = ImageIO.read(new File("images\\ast.png"));
            explosion_image = ImageIO.read(new File("images\\Splosion.png"));
           
        } catch (IOException ex) {
            Logger.getLogger(EpicGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        player=new Player(0,0,80,80,100,100,50,4,max_player_ammo,player_image);              
        lasers=new Collection_Of_Space_Objects(player.max_ammo,75,30,55,20,120,9,laser_image);
        rocks=new Collection_Of_Space_Objects(max_ast_count,100,100,64,64,40,64,asteroid_image);
        explosions=new Collection_Of_Space_Objects(max_exp_count,100,100,64,64,25,13,explosion_image);
        
        player.x=0;
        player.y=Height/2-player.h/2;
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
               
        
        if(input.enter.is_pressed() && game_over)
        {
            reset_game();
        }
        //Controls handling
        
        //Player movement
        if (input.up.is_pressed() && player.y>0)
        {
            player.update(0,-1); 
        }
       
        else if (input.down.is_pressed() && player.y < (Height - player.h) )
        {
            player.update(0,+1); 
        }
        
        else if (input.left.is_pressed() && player.x>0)
        {
           player.update(-1,0);
        }
        
        else if (input.right.is_pressed() && player.x < (Width - player.w) )
        {    
            player.update(+1,0);  
        }
        
        else
            player.update(0, 0);
        
        //Space
        if (input.space.is_pressed() && ( System.currentTimeMillis() - player.getLast_fired() ) > 300 )
        {
            player.last_fired=System.currentTimeMillis();
            
            if(lasers.spawn(player.x + 30, player.y))player.current_ammo--;
            
            if(lasers.spawn(player.x + 30 , player.y +50))player.current_ammo--;
        }
        
        if(update_count%200==0)
            rocks.spawn(Width+500,new Random().nextInt(Height) );

        lasers.update(1,0);
        rocks.update(-1,0);
        explosions.update(-1,0);
        
        check_lasers();
        check_rocks();
        check_explosions();
        
        
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
        int delay=5;
        
        w--;
        w%=(delay*1024);
        
        a.drawImage(Background, w/delay, 0, frame.getWidth()+1024, frame.getHeight(),null);
        //a.drawImage(rocks.image, 0, 0, 4000, 64,null);
        a.setColor(Color.yellow);
       
        //Painting important stuff
        player.paint(a);
        lasers.paint(a);
        rocks.paint(a);
        explosions.paint(a);
        
            
        
         //TEXT AND STUFF 
        if(game_over)
        {
            a.setFont(new Font("serif",10,100));
            a.drawString("Game Over!", Width/2-250,frame.getHeight()/2-50);
            a.setFont(new Font("serif",10,40));
            
            a.drawString("Your score was : " + score,350,frame.getHeight()-220);    
            a.drawString("Press enter to reset... ",350,frame.getHeight()-40);    
        }
        else
        {
            a.setFont(new Font("serif",10,40));
            a.drawString("Ammo : " + player.current_ammo, 0,frame.getHeight()-40);
            a.drawString("Score : " + score,300,frame.getHeight()-40);    
        }
            
        
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
    
    
    
    void check_lasers()
    {
        for(int i=0;i<lasers.max_count;i++)
            
            if(lasers.m[i]!=null)
                
                if(lasers.m[i].x > Width)
                {
                    lasers.m[i]=null;
                    player.current_ammo++;
                }
    }
    
    void check_rocks()
    {
        for(int i=0;i<rocks.max_count;i++)
            
            if(rocks.m[i]!=null)
                
                if(rocks.m[i].x < - 100)
                {   
                    rocks.m[i]=null;
                    if(!game_over)score++;
                }
    }
    
    void check_explosions()
    {
        for(int i=0;i<explosions.max_count;i++)
            
            if(explosions.m[i]!=null)
                
                if(explosions.m[i].br==explosions.m[i].frames-1)
                    
                    explosions.m[i]=null;
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
                
                
                        if( d < rocks.sample.h/2 + 5 )
                        {
                           explosions.spawn(rocks.m[j].x, rocks.m[j].y);
                           
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
                
                if( d < 80 )
                {
                    explosions.spawn(rocks.m[i].x, rocks.m[i].y);
                    explosions.spawn(player.x, player.y);
                 
                    rocks.m[i]=null;
                    
                    player.x=-100000;
                    player.y=-100000;
                    
                    game_over=true;
                    
                    return;
                }       
            }
        }
    }    


    void reset_game()
    {
            lasers.reset();
            rocks.reset();
            explosions.reset();
 
            player.x=0;
            player.y=Height/2-player.h/2;
            
            game_over=false;
            score=0;
            player.current_ammo=player.max_ammo;
    }
    
}