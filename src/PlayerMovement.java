import java.awt.event.KeyEvent;

/**
 * @author prenderj
 *
 */
public class PlayerMovement extends Movable {
	
	private double baseAcceleration = 0.2, drag = -0.35, rotationSpeed = Math.PI / 18;
	
	public PlayerMovement() {
		setMaxSpeed(10);
	}
	
	/**
	 * @see Movable#move(Game)
	 */
	@Override
	public void move(Entity e) {
		/* Accelerate if UP key is down, slowly decelerate if DOWN key is down. Quicker if DOWN key is down.
		 * Rotate if LEFT or RIGHT keys are DOWN. */
		Game game = Game.instance();
		KeyboardInput input = game.getInput();
		
		// Changes in movement speed.
		if (input.isKeyDown(KeyEvent.VK_UP))
			setAcceleration(baseAcceleration);
		else if (input.isKeyDown(KeyEvent.VK_DOWN))
			setAcceleration(drag * 2.5);
		else
			setAcceleration(drag);
		
		// Changes in angle.
		double rotation = e.getRotation(), mul = (getMoveSpeed() > 0.15) ? 1 : 0.5;
		if (input.isKeyDown(KeyEvent.VK_LEFT))
			e.setRotation(rotation + (rotationSpeed*mul));
		else if (input.isKeyDown(KeyEvent.VK_RIGHT))
			e.setRotation(rotation - (rotationSpeed*mul));
		
		super.move(e);
	}
	
	public double getDrag() {
		return drag;
	}

	public void setDrag(double drag) {
		this.drag = drag;
	}
	
	public double getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(double rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public double getBaseAcceleration() {
		return baseAcceleration;
	}

	public void setBaseAcceleration(double baseAcceleration) {
		this.baseAcceleration = baseAcceleration;
	}
	
}
