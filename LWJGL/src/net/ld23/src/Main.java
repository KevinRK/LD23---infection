package net.ld23.src;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ld23.entities.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

public class Main {

    private static enum State {

	MAIN_MENU, GAME, GAME_OVER;
    }
    private static State state = State.MAIN_MENU;
    public static EntityPlayer player = new EntityPlayer(0, 0, 20, 20);
    public static final EntityBorder border = new EntityBorder(0, 0, 640, 480);
    public EntityEnemy enemy[] = new EntityEnemy[50];
    public Random randomCoords = new Random();
    public EntityMissles missles[] = new EntityMissles[8];
    public EntityBullet bullets[] = new EntityBullet[30];
    public EntityMatrix matrix[] = new EntityMatrix[400];
    public EntityFile files = new EntityFile(randomCoords.nextInt(620), randomCoords.nextInt(460), 30, 30, this);
    public int enemyCount = 0;
    private int fileCount = 0;
    private int framesCount = 0;
    private int misslesLeft = 0;
    private int ammountFired = 0;
    private boolean misslesFired = false;
    private int bulletsLeft = 0;
    private int bulletsFired = 0;
    private int bulletsActive = 0;
    private boolean bulletsActivated = false;
    private Audio song;

    public Main() {


	enemy[enemyCount] = new EntityEnemy(300, 300, 20, 20, this);



	try {
	    Display.setDisplayMode(new DisplayMode(640, 480));
	    Display.setTitle("C:\\ Infection");
	    Display.create();
	} catch (LWJGLException e) {
	    e.printStackTrace();
	}

	// Initialization code OpenGL
	glEnable(GL_TEXTURE_2D);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(0, 640, 480, 0, 1, -1);
	glMatrixMode(GL_MODELVIEW);


	/*
	 * try { song = AudioLoader.getAudio("WAV",
	 * this.getClass().getResourceAsStream("/res/song.wav")); } catch
	 * (IOException ex) {
	 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex); }
	 * song.playAsMusic(1.0f, 1.0f, true);
	 */

	while (!Display.isCloseRequested()) {
	    // Render
	    glClear(GL_COLOR_BUFFER_BIT);

	    render();
	    getInput();
	    gameLogic();

	    Display.update();
	    Display.sync(60);
	}

	Display.destroy();
	AL.destroy();
    }

    private void render() {

	for (int count = 0; count < 400; count++) {
	    matrix[count] = new EntityMatrix(randomCoords.nextInt(480), randomCoords.nextInt(680), randomCoords.nextInt(480), randomCoords.nextInt(2));
	}

	switch (state) {
	    case MAIN_MENU:
		//Puts the button in the middle. Subtracts 50 because the length is 100
		int x1 = (640 / 2) - 50;
		int y1 = (480 / 2) - 50;
		glColor3f(0, 250, 0);
		glRectd(x1, y1, x1 + 100, y1 + 25);
		break;
	    case GAME:

		for (int count = 0; count < 400; count++) {
		    matrix[count].render();
		}

		//render health bar
		glColor3f(100, 0, 100);
		glRecti(0, 20, player.lives, 10);

		//render count of bullets
		glColor3f(0.0f, 0.75f, 1.0f);
		glRecti(0, 40, bulletsLeft, 20);

		//Missles
		glColor3f(0.75f, 0.00f, 1.0f);
		glRecti(0, 60, misslesLeft, 20);


		player.render();

		//render enemies
		for (int count = 0; count <= enemyCount; count++) {
		    enemy[count].render();
		}



		files.render();

		if (misslesFired) {
		    for (int count = 0; count <= 7; count++) {
			missles[count].render();
		    }
		}

		if (bulletsActivated) {
		    for (int count = 0; count < bulletsActive; count++) {
			bullets[count].render();
		    }
		}

		break;
	    case GAME_OVER:
		x1 = (640 / 2) - 50;
		y1 = (480 / 2) - 50;
		glColor3f(250, 0, 0);
		glRectd(x1, y1, x1 + 100, y1 + 25);
		break;
	}
    }

    private void gameLogic() {

	if (state == State.GAME) {
	    player.updateHitbox();

	    misslesLeft = (fileCount / 2) - ammountFired;
	    bulletsLeft = (fileCount / 1) * 30 - bulletsFired;
	    if (!player.intersects(border)) {
		if (player.getX() > 640) {
		    player.setX(635);
		}

		if (player.getX() < 0) {
		    player.setX(5);
		}

		if (player.getY() > 480) {
		    player.setY(475);
		}

		if (player.getY() < 0) {
		    player.setY(5);
		}

	    }



	    for (int count = 0; count <= enemyCount; count++) {
		if (!enemy[count].intersects(player)) {
		    enemy[count].chasePlayer();
		}

		if (enemy[count].intersects(player) && enemy[count].stunned == false) {
		    player.substractLives();
		    if (player.lives <= 0) {
			state = State.GAME_OVER;
		    }
		}

	    }

	    files.updateHitbox();

	    if (files.intersects(player)) {
		if (enemyCount < 49) {
		    fileCount++;
		    enemyCount++;

		    files.setX(randomCoords.nextInt(600));
		    files.setY(randomCoords.nextInt(400));
		    player.addLives();
		    enemy[enemyCount] = new EntityEnemy(randomCoords.nextInt(600), randomCoords.nextInt(400), 20, 20, this);
		} else {
		    files.setX(randomCoords.nextInt(600));
		    files.setY(randomCoords.nextInt(400));
		}
	    }

	    framesCount++;

	    if (framesCount == 350) {
		framesCount = 0;
		for (int count = 0; count <= enemyCount; count++) {
		    enemy[count].scatterEnemy();
		}
	    }

	    if (misslesFired) {

		int misslesGone = 0;

		for (int count = 0; count <= 7; count++) {
		    missles[count].updatePath();
		    for (int count2 = 0; count2 <= enemyCount; count2++) {
			if (missles[count].intersects(enemy[count2])) {
			    enemy[count2].stun();
			}
		    }
		    if (missles[count].destroyed) {
			misslesGone++;
		    }
		}

		if (misslesGone == 8) {
		    misslesFired = false;
		}
	    }

	    if (bulletsActivated) {
		for (int count = 0; count < bulletsActive; count++) {
		    bullets[count].updatePath();
		}

		for (int count = 0; count < bulletsActive; count++) {
		    for (int count2 = 0; count2 <= enemyCount; count2++) {
			if (bullets[count].intersects(enemy[count2])) {
			    enemy[count2].stun();
			}
		    }
		}

	    }
	}



	if (state == State.GAME_OVER) {
	    enemyCount = 0;
	    bulletsLeft = 0;
	    bulletsFired = 0;
	    bulletsActive = 0;
	    bulletsActivated = false;
	    misslesLeft = 0;
	    misslesFired = false;
	    ammountFired = 0;
	    fileCount = 0;
	    player = new EntityPlayer(0, 0, 20, 20);
	    enemy[enemyCount] = new EntityEnemy(300, 300, 20, 20, this);
	}

    }

    private void getInput() {
	if (state.equals(State.GAME)) {

	    if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
		player.setY(player.getY() - 5);
	    }

	    if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
		player.setY(player.getY() + 5);
	    }

	    if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
		player.setX(player.getX() - 5);
	    }

	    if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
		player.setX(player.getX() + 5);
	    }

	    if (Mouse.isButtonDown(1) && misslesLeft > 0) {
		float missleX = player.getX();
		float missleY = player.getY();

		missles[0] = new EntityMissles((int) missleX - 10, (int) missleY - 10, 25, 25, 0);
		missles[1] = new EntityMissles((int) missleX, (int) missleY - 10, 25, 25, 1);
		missles[2] = new EntityMissles((int) missleX + 10, (int) missleY - 10, 25, 25, 2);
		missles[3] = new EntityMissles((int) missleX - 10, (int) missleY, 25, 25, 3);
		missles[4] = new EntityMissles((int) missleX + 10, (int) missleY, 25, 25, 4);
		missles[5] = new EntityMissles((int) missleX - 10, (int) missleY + 10, 25, 25, 5);
		missles[6] = new EntityMissles((int) missleX, (int) missleY + 10, 25, 25, 6);
		missles[7] = new EntityMissles((int) missleX + 10, (int) missleY + 10, 25, 25, 7);

		misslesFired = true;
		ammountFired++;
	    }

	    if (Mouse.isButtonDown(0) && bulletsLeft > 0) {
		bulletsActivated = true;
		bulletsFired++;

		if (bulletsActive >= 29) {
		    bulletsActive = 0;
		}

		double mouseY = (double) -Mouse.getY() + 480 - 1;

		bullets[bulletsActive] = new EntityBullet((int) player.getX(), (int) player.getY(), 10, 10, (double) Mouse.getX(), mouseY);
		bulletsActive++;
	    }

	}

	//Main menu Controls

	if (state.equals(State.MAIN_MENU)) {

	    if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
		state = State.GAME;
	    }

	}

	if (state.equals(State.GAME_OVER)) {
	    if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
		state = State.MAIN_MENU;
	    }
	}
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	Main game = new Main();
    }
}
