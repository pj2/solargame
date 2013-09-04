
public class PeaShooter extends Weapon {

	public PeaShooter(Entity owner) {
		super(owner);
	}
	
	@Override
	public void fire() {
		Bullet b = new Bullet(getOwner());
		Game.instance().registerEntity(b);
		b.fire();
		
		setNextShot(System.currentTimeMillis() + getShotDelay());
	}

}
