import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/*
 * A class representing a bouncing shape object. 
 */

public abstract class BouncingShape {
    public static final int DEFAULT_WIDTH = 25;
    public static final int DEFAULT_HEIGHT = 25;    
    
    protected Pair curPosition;      // position of the center
    protected Pair curVelocity;      // disk velocity (px / sec)
    protected Pair nextPosition;     // position in next frame
    protected Pair nextVelocity;     // velocity in next frame    
    protected double width;
    protected double height;
    protected Color color;
    protected World world;           // the world to which the disk belongs
    
    public BouncingShape(World world, double width, double height, Color color, Pair position, Pair velocity) {
	this.world = world;
	this.width = width;
	this.height = height;
	this.color = color;
	curPosition = new Pair(position);
	curVelocity = new Pair(velocity);
	nextPosition = new Pair(position);
	nextVelocity = new Pair(velocity);
    }

    /*
     * Compute the state of the BouncingShape in the next frame. The
     * parameter 'double time' is the amount of time in seconds
     * elapsed between the current frame and the next frame.
     */
    public void update (double time) {
	Pair delta = curVelocity.times(time);
        nextPosition.set(curPosition.plus(delta));
        bounce();
    }

    /*
     * Advances state to the next frame by setting curPostion and
     * curVelocity to their corresponding next values.
     */
    public void advance () {
	curPosition.set(nextPosition);
	curVelocity.set(nextVelocity);
    }

    // set the current position of the center of the BouncingDisk
    public void setPosition(Pair p){
        curPosition.set(p);
    }

    // set the current velocity of the BouncingDisk
    public void setVelocity(Pair v){
        curVelocity.set(v);
    }

    // draw this BouncingDisk to graphics object g
    public abstract void draw(Graphics g);

    /*
     * Detect a collision with the BouncingDisk and the boundaries of
     * the World, and respond accordingly. For example, when a disk
     * collides with a vertical wall, the horizontal component of its
     * velocity should be negated.
     */
    protected void bounce() {
	
        if (nextPosition.getX() - width / 2 <= 0){
            nextVelocity.flipX();
            nextPosition.setX(width / 2);
        } else if (nextPosition.getX() + width / 2 >= world.getWidth()){
            nextVelocity.flipX();
            nextPosition.setX(world.getWidth() - width / 2);
        }
	
        if (nextPosition.getY() - height / 2 <= 0){
            nextVelocity.flipY();
            nextPosition.setY(height / 2);
        } else if (nextPosition.getY() + height / 2 >=  world.getHeight()) {
            nextVelocity.flipY();
            nextPosition.setY(world.getHeight() - height / 2);
        }
    }
}
