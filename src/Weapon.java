
public abstract class Weapon {
	
	private Entity owner;
	private long nextShot = System.currentTimeMillis(), shotDelay = 400;
	
	public Weapon(Entity owner) {
		this.owner = owner;
	}

	public void fire() {
	}
	
	public boolean isReady() {
		return System.currentTimeMillis() >= getNextShot() && owner != null;
	}
	
	public long getNextShot() {
		return nextShot;
	}

	public void setNextShot(long nextShot) {
		this.nextShot = nextShot;
	}

	public long getShotDelay() {
		return shotDelay;
	}

	public void setShotDelay(long shotDelay) {
		this.shotDelay = shotDelay;
	}

	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}
	
}
