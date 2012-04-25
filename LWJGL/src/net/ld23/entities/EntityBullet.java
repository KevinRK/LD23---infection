package net.ld23.entities;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Kevin
 */
public class EntityBullet extends Entity {

    private int count = 0;
    private double destX, destY;

    public EntityBullet(int x, int y, int w, int h, double destx, double desty) {
	super(x, y, w, h);
	this.destX = destx;
	this.destY = desty;
    }

    public void updatePath() {
	int x1 = (int) getX();
	int y1 = (int) getY();
	int x2 = (int) destX;
	int y2 = (int) destY;

	if (x > x2) {
	    x = x - 10f;
	} else if (x < x2) {
	    x = x + 10f;
	}

	if (y > y2) {
	    y = y - 10f;
	} else if (y < y2) {
	    y = y + 10f;
	}
	this.updateHitbox();
    }

    @Override
    public void render() {

	if (count <= 5) {
	    glColor3f(0, 255, 255);
	    count++;
	} else if (count <= 9) {
	    glColor3f(0, 255, 0);
	    count++;
	} else {
	    glColor3f(0, 200, 200);
	    count = 0;
	}

	glRectf(this.getX(), this.getY(), this.getX() + this.getW(), this.getY() + this.getH());
    }
}
