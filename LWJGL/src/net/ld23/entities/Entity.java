package net.ld23.entities;

/**
 *
 * @author Kevin
 */
import java.awt.Rectangle;

abstract public class Entity {

    public float x, y, w, h;
    public Rectangle hitbox = null;

    public Entity(float x, float y, float w, float h) {
	this.x = x;
	this.y = y;
	this.w = w;
	this.h = h;
	hitbox = new Rectangle((int) x, (int) y, (int) w, (int) h);
    }
    
    public void updateHitbox() {
	hitbox = null;
    	hitbox = new Rectangle((int) x, (int) y, (int) w, (int) h);
    }
    
    public float getX() {
	return x;
    }

    public float getY() {
	return y;
    }

    public float getW() {
	return w;
    }

    public float getH() {
	return h;
    }

    public void setX(float x) {
	this.x = x;
    }

    public void setY(float y) {
	this.y = y;
    }

    abstract public void render();

    public boolean intersects(Entity other) {
	return hitbox.intersects(other.hitbox);
    }
}
