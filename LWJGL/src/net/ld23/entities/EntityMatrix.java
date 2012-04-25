package net.ld23.entities;

/**
 *
 * @author Kevin
 */

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

public class EntityMatrix extends Entity {
    Random rand = new Random();
    private int count = 0;
    
    public EntityMatrix(int x, int y, int w, int h) {
	super(x, y, w, h);
    }
    
    @Override
    public void render() {
	glColor3f(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
	glRectf(this.getX(), this.getY(), this.getX() + this.getW(), this.getY() + this.getH());
    }

}
