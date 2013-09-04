import java.awt.geom.Point2D;

/**
 * @author prenderj
 *
 */
public class Bullet extends Entity {
	
	private Entity homingTarget;
	private int framesToLive = 50; // Remove from the game after this many frames
	private int damage = 1;
	private Entity owner;
	
	public Bullet(Entity owner) {
		super(new Point2D.Double());
		
		this.owner = owner;
		setModel(Model.PEA_BULLET);
		setBoundingRadiusFromModel(getModel());
		
		setMovableComponent(new Movable(0.2, 14, 16));
		getDrawComponents().add(new ModelDrawable());
		setCollisionComponent(new BulletCollision());
	}
	
	@Override
	public void update() {
		if (--framesToLive <= 0) setValid(false);
		super.update();
	}

	public void fire() {
		setPos((Point2D.Double) owner.getPos().clone());
		setRotation(owner.getRotation());
		spawn();
	}
	
	@Override
	public void leftGameArea() {
		invalidate();
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getFramesToLive() {
		return framesToLive;
	}

	public void setFramesToLive(int framesToLive) {
		this.framesToLive = framesToLive;
	}

	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}

	public Entity getHomingTarget() {
		return homingTarget;
	}

	public void setHomingTarget(Entity homingTarget) {
		this.homingTarget = homingTarget;
	}
	
}
