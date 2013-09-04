
public class BulletCollision extends Collidable {

	@Override
	public void collide(Entity me, Entity them) {
		Bullet b = (Bullet) me;
		them.takeDamage(b.getDamage());
		me.setValid(false);
		
		// HACK hack hack
		Entity owner = b.getOwner();
		if (owner != null && owner instanceof Player) {
			((Player) owner).addScore(1);
		}			
	}

	@Override
	public boolean canCollide(Entity me, Entity them) {
		Bullet b = (Bullet) me;
		Entity owner = b.getOwner();
		return owner == null ? true : owner.getAllegiance() != them.getAllegiance() && 
				owner != them && !(them instanceof Bullet);
	}

}
