import java.awt.geom.Point2D;

public class Spawner extends Entity {
	
	private long nextSpawn, spawnDelay = (long) (5700 + (600*Math.random()));

	public Spawner(Point2D.Double pos) {
		super(pos);
		nextSpawn = System.currentTimeMillis() + spawnDelay;
	}
	
	@Override
	public void update() {
		long now = System.currentTimeMillis();
		if (now >= nextSpawn) {
			Player pl = Game.instance().getPlayer();
			Fighter enemy = new Fighter(new Point2D.Double(getX(), getY()));
			
			enemy.setRotation(Entity.angleTo(pl.getX(), pl.getY(), getX(), getY()));
			enemy.setAllegiance(Allegiance.HOSTILE);
			enemy.setTarget(pl);
			
			Game.instance().registerEntity(enemy);
			nextSpawn = now + spawnDelay;
		}
	}

}
