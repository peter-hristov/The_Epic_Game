
package epicgame;

import java.awt.event.KeyEvent;

/**
 *
 * @author Peter
 */
public class InputHandler implements java.awt.event.KeyListener {
    
    
    
    
    public class Key
    {
        private boolean pressed=false;
        
        public void toggle(boolean is_pressed)
        {
            pressed=is_pressed;
        }
        
        public boolean is_pressed()
        {
            return pressed;
        }
    }
    
    
  
    public Key up=new Key();
    public Key down=new Key();
    public Key left=new Key();
    public Key right=new Key();
    public Key space=new Key();
    
    
    
    
    
     public InputHandler (EpicGame a)
    {
        a.addKeyListener(this);
    }
            
    
    
    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    
    public void keyPressed(KeyEvent ke) {
             
        toggle(ke.getKeyCode(), true);    
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
        toggle(ke.getKeyCode(), false);
        
    }
    
    
    public void toggle(int key_code,boolean is_pressed)
    {
        if(key_code == KeyEvent.VK_UP){up.toggle(is_pressed);}
        
        if(key_code == KeyEvent.VK_DOWN){down.toggle(is_pressed);}
        
        if(key_code == KeyEvent.VK_LEFT){left.toggle(is_pressed);}
        
        if(key_code == KeyEvent.VK_RIGHT){right.toggle(is_pressed);}
        
        if(key_code == KeyEvent.VK_SPACE){space.toggle(is_pressed);}
    }
    
}
