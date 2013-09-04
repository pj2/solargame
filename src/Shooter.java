import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Shooter implements GameMode {

	private static final Random rng = new Random();
	private int maxSpawners;
	private long nextSpawnerSpawn, baseSpawnerSpawnDelay;
	private ArrayList<Spawner> spawners = new ArrayList<Spawner>();
	
	public static void main(String[] args) {
		Game.instance().setGameMode(new Shooter());
		Game.instance().start();
	}
	
	@Override
	public void initialize(Game game) {
		spawners.clear();
		this.baseSpawnerSpawnDelay = 10000;
		this.maxSpawners = 5;
		
		game.registerEntity(new StarField());
		game.registerEntity(new SpaceStation(new Point2D.Double()));
		game.registerEntity(new Player(new Point2D.Double(0, -150)));
		// game.registerEntity(new Enemy(new Point2D.Double(0, 150)));
		game.getWindow().setFocus(game.getPlayer());
	}
	
	@Override
	public void update(Game game) {
		long now = System.currentTimeMillis();
		if (now > nextSpawnerSpawn && spawners.size() < maxSpawners) {
			addSpawner(game);
			nextSpawnerSpawn = now + (baseSpawnerSpawnDelay*spawners.size());
		}
	}

	@Override
	public void drawHud(Game game) {
		Player pl = game.getPlayer();
		GameWindow window = game.getWindow();
		
		double w = window.getWidth()/2, h = window.getHeight()/2;
		double max = Math.floor(pl.getHealth()), diameter = 12, spacing = 20;
		
		// Draw health.
		for (double i = 0; i < max; i++) {
			window.drawEllipse(w - diameter - 15d, i * spacing - h + 50d, diameter, "RED");
		}
		
		max = pl.getLives();
		// Draw lives.
		for (double i = 0; i < max; i++) {
			window.drawEllipse(w - diameter*2 - 20d, i * spacing - h + 50d, diameter, "ORANGE");
		}
	}
	
	public void addSpawner(Game game) {
		double w = game.getGameArea().width/2, h = game.getGameArea().height/2,
				mul = 1 - rng.nextInt(2)*2, mul2 = 1 - rng.nextInt(2)*2;
		
		Spawner spawner = new Spawner(new Point2D.Double(w*mul + (100*mul), h*rng.nextDouble()*mul2));
		spawners.add(spawner);
		game.registerEntity(spawner);
	}

}
