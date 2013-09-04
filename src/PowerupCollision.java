
public class PowerupCollision extends Collidable {

	@Override
	public void collide(Entity me, Entity them) {
		Player pl = (Player) them;
		double random = Math.random();
		
		if (random < 0.2) {
			pl.addHealth(4);
		}
		else if (random >= 0.2 && random < 0.25) {
			pl.setLives(pl.getLives() + 1);
		}
		else if (random >= 0.25 && random < 0.45) {
			pl.getWeapon().setShotDelay(Math.max(150, pl.getWeapon().getShotDelay() / 2));
		}
		else if (random >= 0.45) {
			pl.addHealth(2);
		}
		
		me.setValid(false);
	}
	
	@Override
	public boolean canCollide(Entity me, Entity them) {
		return them instanceof Player;
	}

}
