import edu.siena.csis225.threadgraphics.*;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;
import javax.swing.JComponent;

/**
   The BreakoutBall class is responsible for managing the life
   of a ball that can bounce off a paddle and hit bricks, as well
   as bouncing off the walls and ceiling.

   @author Jim Teresco
   @version Spring 2020
*/
class BreakoutBall extends AnimatedGraphicsObject {

    // a static Random object shared among all BreakoutBall objects
    private static Random r = new Random();

    // range of possible start speeds in x and y
    public static final double MIN_SPEED = 3;
    public static final double MAX_SPEED = 10;
    
    // ball size
    public static final int SIZE = 25;

    // delay time between frames of animation (ms)
    public static final int DELAY_TIME = 33;

    // pixels to move each iteration
    private double xSpeed, ySpeed;

    // latest location of the ball
    private double upperLeftX, upperLeftY;

    // max allowed x coordinate of the upper left corner
    private int xMax;

    // reference to the paddle
    private BreakoutPaddle paddle;

    /**
       Construct a new BreakoutBall object, choosing a random location and
       speed.

       @param paddle the BreakoutPaddle we need to see if we hit
       @param container the Swing component in which this ball is being
       drawn to allow it to call that component's repaint method
    */
    public BreakoutBall(JComponent container, BreakoutPaddle paddle) {

	super(container);

	// we place the ball initially so its half way up, near the
	// center
	int width = container.getWidth();
	upperLeftX = width/4 - SIZE/2 + r.nextInt(width/2);
	upperLeftY = container.getHeight() / 2;
	xSpeed = r.nextDouble() * (MAX_SPEED-MIN_SPEED) + MIN_SPEED;
	ySpeed = r.nextDouble() * (MAX_SPEED-MIN_SPEED) + MIN_SPEED;
	xMax = container.getWidth() - SIZE;
	this.paddle = paddle;
    }

    /**
       Draw the ball at its current location.

       @param g the Graphics object on which the ball should be drawn
    */
    @Override
    public void paint(Graphics g) {

	g.fillOval((int)upperLeftX, (int)upperLeftY, SIZE, SIZE);
    }

    /**
       This object's run method, which manages the life of the ball as it
       bounces around the screen.
    */
    @Override
    public void run() {

	done = false;
	while (!done) {

	    sleepWithCatch(DELAY_TIME);

	    // every iteration, update the coordinates
	    // by a pixel
	    upperLeftX += xSpeed;
	    upperLeftY += ySpeed;

	    boolean bounced = false;
	    // bounce off the walls
	    if (upperLeftX < 0) {
		upperLeftX = 0;
		bounced = true;
		xSpeed = -xSpeed;
	    }

	    if (upperLeftX > xMax) {
		upperLeftX = xMax;
		bounced = true;
		xSpeed = -xSpeed;
	    }

	    if (upperLeftY < 0) {
		upperLeftY = 0;
		bounced = true;
		ySpeed = -ySpeed;
	    }

	    // if we've gone off the bottom, we're done
	    if (upperLeftY > container.getHeight()) done = true;

	    container.repaint();
	}
    }
}
