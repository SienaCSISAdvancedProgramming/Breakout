import edu.siena.csis225.threadgraphics.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
   A basic "Breakout" game, with a bouncing ball, paddle, and bricks to
   hit for points.

   @author Jim Teresco
   @version Spring 2020
*/

public class Breakout extends ThreadGraphicsController implements MouseListener, MouseMotionListener {

    // dimensions of the paddle
    public static final int PADDLE_WIDTH = 50;
    public static final int PADDLE_HEIGHT = 20;

    // current x-coordinate of the left side of the paddle
    private int paddleX;

    /**
       Constructor, which simply calls the superclass constructor
       with an appropriate window label and dimensions.
    */
    public Breakout() {

	super("Breakout", 800, 800);
    }

    /**
       Draw the bricks and paddle.

       @param g the Graphics object in which to draw
    */
    @Override
    protected void paint(Graphics g) {

	// paddle at its current position
	g.setColor(Color.black);
	g.fillRect(paddleX, panel.getHeight() - 2*PADDLE_HEIGHT,
		   PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    /**
       Add the mouse listeners to the panel.  Here, we need methods
       from both MouseListener and MouseMotionListener.

       @param p the JPanel to which the mouse listeners will be
       attached
    */
    @Override
    protected void addListeners(JPanel panel) {

	panel.addMouseListener(this);
	panel.addMouseMotionListener(this);
    }

    /**
       Mouse press event handler to

       @param e mouse event info
    */
    @Override
    public void mousePressed(MouseEvent e) {

	BreakoutBall newBall = new BreakoutBall(panel);
	synchronized(lock) {
	    list.add(newBall);
	}
	newBall.start();
	panel.repaint();
    }
    /**
       Mouse move event handler to control the paddle

       @param e mouse event info
    */
    @Override
    public void mouseMoved(MouseEvent e) {

	paddleX = e.getPoint().x;
	if (paddleX > panel.getWidth() - PADDLE_WIDTH) {
	    paddleX = panel.getWidth() - PADDLE_WIDTH;
	}

	panel.repaint();
    }

    // fill in unused methods needed to satify the interfaces, which
    // are needed since we can't use the MouseAdapter, as this class
    // now needs to extend the abstract class
    public void mouseReleased(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    public static void main(String args[]) {

	// construct our object and have its run method invoked to
	// set up the GUI once its thread is ready
	javax.swing.SwingUtilities.invokeLater(new Breakout());
    }
}
   
