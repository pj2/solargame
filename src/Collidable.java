
public abstract class Collidable {
	public abstract void collide(Entity me, Entity them);
	
	public boolean checkForCollision(Entity me, Entity them) {
		double dx = me.getX() - them.getX(), dy = me.getY() - them.getY();
		return (dx*dx + dy*dy) < me.getBoundingRadius()*them.getBoundingRadius()*2;
	}
	
	public boolean canCollide(Entity me, Entity them) {
		return true;
	}
}
