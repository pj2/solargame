
/**
 * The default entity movement strategy, which incorporates velocity and acceleration.
 * @author prenderj
 *
 */
public class Movable {
	
	private double maxSpeed, // Maximum movement speed
			acceleration, // Actual acceleration applied this frame
			moveSpeed; // Current movement speed
	
	public Movable() {
	}
	
	public Movable(double acceleration, double startSpeed, double maxSpeed) {
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
		this.moveSpeed = startSpeed;
	}

	public void move(Entity e) {
		setMoveSpeed(Math.max(0, Math.min(getMoveSpeed() + acceleration, getMaxSpeed()))); // Clamp speed between 0 and maxSpeed
		
		// Apply movement.
		e.getPos().x += moveSpeed * Math.sin(e.getRotation());
		e.getPos().y += moveSpeed * Math.cos(e.getRotation());
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public double getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(double moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	
}
