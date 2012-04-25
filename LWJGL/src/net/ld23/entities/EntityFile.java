package net.ld23.entities;

import static org.lwjgl.opengl.GL11.*;

import net.ld23.src.Main;

/**
 *
 * @author Kevin
 */
public class EntityFile extends Entity {

    Main main;
    
    public EntityFile(float x, float y, float w, float h, Main main) {
	super(x, y, w, h);
    }
    
    @Override
    public void render() {
	glColor3f(100, 100, 0);
	glRectf(this.getX(), this.getY(), this.getX() + this.getW(), this.getY() + this.getH());
    }
    
}
