package net.ld23.entities;

import net.ld23.entities.Entity;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Rectangle;

import net.ld23.src.Main;

/**
 *
 * @author Kevin
 */
public class EntityEnemy extends Entity {

    private int count = 0;
    int scatterCounter = 0;
    Main main;
    Rectangle destRect = null;
    int randomX;
    int randomY;
    public boolean stunned;
    int framesSinceStunned;

    public EntityEnemy(int x, int y, int w, int h, Main main) {
	super(x, y, w, h);
	this.main = main;
    }
   

    @Override
    public void render() {

	if (count <= 5) {
	    glColor3f(250, 0, 0);
	    count++;
	} else if (count <= 9) {
	    glColor3f(0, 0, 250);
	    count++;
	} else {
	    glColor3f(250, 250, 250);
	    count = 0;
	}

	glRectf(this.getX(), this.getY(), this.getX() + this.getW(), this.getY() + this.getH());
    }

    public void chasePlayer() {
	if (!stunned) {
	    float x2 = main.player.getX();
	    float y2 = main.player.getY();

	    //Move the enemy closer to the player
	    if (x > x2) {
		x = x - 2.5f;
	    } else if (x < x2) {
		x = x + 2.5f;
	    }

	    if (y > y2) {
		y = y - 2.5f;
	    } else if (y < y2) {
		y = y + 2.5f;
	    }
	    this.updateHitbox();
	}
	
	if(stunned) {
	    if(framesSinceStunned >= 300)
		stunned = false;
	    framesSinceStunned++;
	}
	
    }

    public void scatterEnemy() {
	if (!stunned) {
	    float xNew = main.randomCoords.nextInt(250) + main.player.getX();
	    float yNew = main.randomCoords.nextInt(250) + main.player.getY();
	    setX(xNew);
	    setY(yNew);
	    this.updateHitbox();
	}
    }
    
    public void stun() {
	stunned = true;
	int frameSinceStunned = 0;
    }
}
