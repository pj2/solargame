import java.util.Random;


public class FighterMovement extends Movable {
	
	private static final Random rng = new Random();
	private double rotationSpeed, avoidDistance = 125, avoidAngle;
	private int avoidFrames, minAvoidFrames = 25, maxAvoidFrames = 40; // TODO make FPS independent
	private boolean avoiding;
	
	public FighterMovement() {
		setMaxSpeed(8);
		setAcceleration(2);
	}
	
	@Override
	public void move(Entity e) {
		Game.instance();
		Entity target = ((Fighter) e).getTarget();
		
		if (target != null) {
			double angle = e.angleTo(target);
			
			if (System.currentTimeMillis() < e.getSpawnTime() + 700) {
				// Get into the action quicker
				e.setRotation(angle);
				setMoveSpeed(getMaxSpeed()*3);
			}
			else {
				double distance = Math.hypot(e.getX() - target.getX(), e.getY() - target.getY());
				
				boolean inRetreatRange = distance <= avoidDistance;
				if (inRetreatRange && !avoiding) {
					// Add some entropy to make the movement less mechanical
					avoidFrames = minAvoidFrames + rng.nextInt(maxAvoidFrames - minAvoidFrames);
					avoidAngle = angle + Math.PI-(Math.PI/6) + ((Math.PI/3)*rng.nextDouble());
					avoiding = true;
				}
				
				if (avoiding) {
					setRotationSpeed(Math.PI / 16);
					angle = avoidAngle;
					
					if (!inRetreatRange) {
						--avoidFrames;
						if (avoidFrames <= 0) avoiding = false;
					}
				}
				else {
					setRotationSpeed(Math.PI / 32);
				}
				
				double delta = Entity.clampAngle(angle) - e.getRotation();
				double add = Math.max(-rotationSpeed, Math.min(rotationSpeed, delta));
				
				if (distance > avoidDistance*2.5) add = Math.abs(add); // Avoid oscillating between adding and removing angle
				e.setRotation(e.getRotation() + add);
				
				// System.out.println("avoiding: "+avoiding+", rawAngle: "+angle+", clamped: "+clampedAngle+", dist: "+distance+
				// ", angleTo: "+ delta +", curAngle: "+e.getRotation()+", diff: "+delta);
			}
			
			super.move(e);
		}
	}
	
	public double getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(double rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public double getAvoidDistance() {
		return avoidDistance;
	}

	public void setAvoidDistance(double avoidDistance) {
		this.avoidDistance = avoidDistance;
	}

}
