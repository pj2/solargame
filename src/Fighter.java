import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Fighter extends Entity {
	
	private Entity target;
	private double range = 200;
	private Weapon weapon = new PeaShooter(this);

	public Fighter(Point2D.Double pos) {
		super(pos);
		weapon.setShotDelay((long) (2800 + (400*Math.random())));
		
		double random = Math.random();
		EngineFlames fx;
		
		if (random < 0.7) {
			setModel(Model.FIGHTER);
			setHealth(2);
			fx = new EngineFlames(new int[][] {{8,-12}, {-8,-12}});
		}
		else {
			setModel(Model.BOMBER);
			setHealth(3);
			fx = new EngineFlames(new int[][] {{8,-12}, {-8,-12}});
		}

		fx.setFlameSize(0.85);
		fx.setFlameColor("WHITE");
		
		setBoundingRadiusFromModel(getModel());
		setInvulnerable(false);
		
		getDrawComponents().add(new ModelDrawable());
		getDrawComponents().add(fx);
		setMovableComponent(new FighterMovement());
	}
	
	@Override
	public void update() {
		super.update();
		
		if (target == null)
			acquireTarget();
		
		if (target != null) {
			boolean targetLocked = Math.abs(angleTo(target) - getRotation()) < 0.10d,
					inRange = Math.hypot(getX() - target.getX(), getY() - target.getY()) <= getRange();
			if (weapon.isReady() && targetLocked && inRange)
				weapon.fire();
		}
	}
	
	@Override
	public void invalidate() {
		if (Math.random() < 0.1) {
			Powerup powerup = new Powerup((Point2D.Double) getPos().clone());
			Game.instance().registerEntity(powerup);
			powerup.spawn();
		}
		super.invalidate();
	}
	
	public void acquireTarget() {
		// setTarget(Game.instance().getPlayer());
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

}
