package net.ld23.entities;

/**
 *
 * @author Kevin
 */
import static org.lwjgl.opengl.GL11.*;


public class EntityPlayer extends Entity {
    
    //should be health, but crunched for time so not changing
    public int lives = 640;
    

    
    public EntityPlayer(int x, int y, int w, int h) {
	super(x, y, w, h);
    }
    
    public void substractLives() {
	lives = lives - 20;
    }
    
    public void addLives() { 
	lives = lives + 10;
    }
    
    public void resetLives() {
	lives = 400;
    }

    @Override
    public void render() {
	glColor3f(0, 250, 0);
	glRectf(this.getX(), this.getY(), this.getX() + this.getW(), this.getY() + this.getH());
    }
}
