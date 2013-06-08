package epicgame;

import java.awt.BorderLayout;
import java.awt.BufferCapabilities;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class EpicGame extends Canvas implements Runnable {
    
    public boolean running=false;
    public int update_count;
    
    public static final int Actual_Widht=160;
    public static final int Actual_Height=(Actual_Widht*9)/16;
    public static final int Scale=5;
    
    public static final int Widht=Actual_Widht*Scale;
    public static final int Height= Actual_Height*Scale;

    
    public static final String GameName="Epic Game!";
    
    private JFrame frame;
    
    private BufferedImage image=new BufferedImage(Widht,Height, BufferedImage.TYPE_INT_RGB);
    private BufferedImage image2=new BufferedImage(Widht,Height, BufferedImage.TYPE_INT_RGB);
    
    private int pixels[]=((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    
    
    
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
        update_count++;
        
        for(int i=0;i<pixels.length;i++ )
            pixels[i]=i+update_count;
    
    }
    
    public void render_dis(BufferStrategy bs)
    {
        
        Graphics a=bs.getDrawGraphics();
        
        a.setColor(Color.yellow);
        a.fillRect(0, 0, 10000, 10000);
        a.drawImage(image, 0, 0, frame.getWidth(), frame.getHeight(),null);
        
        a.drawImage(image2, 10, 100, null);
        bs.show();
        a.dispose();
    }
    

    public void render()
    {
        
        BufferStrategy bs=getBufferStrategy();
        
        if(bs==null)
        {
            createBufferStrategy(3);
            return;
        }
        
        
        
        try {
            
            image = ImageIO.read(new File("src\\epicgame\\images.jpg"));
            image2 = ImageIO.read(new File("src\\epicgame\\a.jpg"));
            
        } catch (IOException ex) {
            Logger.getLogger(EpicGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        Graphics a=bs.getDrawGraphics();
        
        render_dis(bs);

        
        
        //a.setColor(Color.yellow);
        //a.fillRect(0, 0, 10000, 10000);
//        a.drawImage(image, 0, 0, frame.getWidth(), frame.getHeight(),null);
//        
//        a.drawImage(image2, 10, 100, null);
       // bs.show();
//        a.dispose();
        
        
      
        
//        try {
//            
//            image = ImageIO.read(new File("C:\\Users\\Peter\\Documents\\GitHub\\test\\EpicGame\\src\\epicgame\\a.jpg"));
//            
//        } catch (IOException ex) {
//            Logger.getLogger(EpicGame.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//
//        
//        
//        Graphics g=frame.getGraphics();
//        
//        
//        g.setColor(Color.black);
//        //g.fillRect(0, 0, Widht  , Height);
//        boolean drawImage = g.drawImage(image, Widht, Height, null);
//        System.out.println(drawImage);
//        
//        g.dispose();
        

        
    }
    
    
    
    
    public static void main(String[] args) {
        EpicGame game=new EpicGame();
        game.start();
        
    }

    
    
    
    
    @Override
    public void run() {
        
        long lastTime=System.nanoTime();
        double ns_per_tick=10000000D; 
        
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
                delta--;
                should_render=true;
                
            }
            
            try {
                Thread.sleep(22);
            } catch (InterruptedException ex) {
                Logger.getLogger(EpicGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            if(should_render)
            {
                frames++;
            
                render();
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
