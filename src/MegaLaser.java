
public class MegaLaser extends Weapon {

	public MegaLaser(Entity owner) {
		super(owner);
	}
	
	@Override
	public void fire() {
		Bullet b = new Bullet(getOwner());
		b.setModel(Model.MLASER_BULLET);
		b.setDamage(2);
		
		Movable movable = b.getMovableComponent();
		movable.setMaxSpeed(17);
		movable.setAcceleration(1000);
		
		Game.instance().registerEntity(b);
		b.fire();
		
		setNextShot(System.currentTimeMillis() + getShotDelay());
	}

}
