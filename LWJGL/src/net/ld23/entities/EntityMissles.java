package net.ld23.entities;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Kevin
 */
public class EntityMissles extends Entity {

    int direction;
    private int count;
    public boolean destroyed = false;

    public EntityMissles(int x, int y, int w, int h, int dir) {
	super(x, y, w, h);
	direction = dir;
    }

    public void updatePath() {
	if (direction == 0) {
	    setX(getX() - 1);
	    setY(getY() - 1);
	} else if (direction == 1) {
	    setX(getX());
	    setY(getY() - 1);
	} else if (direction == 2) {
	    setX(getX() + 1);
	    setY(getY() - 1);
	} else if (direction == 3) {
	    setX(getX() - 1);
	    setY(getY());
	} else if (direction == 4) {
	    setX(getX() + 1);
	    setY(getY());
	} else if (direction == 5) {
	    setX(getX() - 1);
	    setY(getY() + 1);
	} else if (direction == 6) {
	    setX(getX());
	    setY(getY() + 1);
	} else if (direction == 7) {
	    setX(getX() + 1);
	    setY(getY() + 1);
	}
	this.updateHitbox();
    }

    @Override
    public void render() {
	if (count <= 5) {
	    glColor3f(1.0f, .5f, 0f);
	    count++;
	}else if(count <= 10) {
	    glColor3f(1.0f , 0f, 0f);
	    count++;
	}else {
	    count = 0;
	    glColor3f(1.0f, 1.0f, 0.0f);
	}
	glRectf(this.getX(), this.getY(), this.getX() + this.getW(), this.getY() + this.getH());
	
    }

    public void destroy() {
	destroyed = true;
    }
}
