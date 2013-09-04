import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.ObjectInputStream.GetField;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author prenderj
 *
 */
public class Game {
	
	private static Game instance = new Game(800, 600);
	private GameWindow window;
	private ArrayList<Entity> entities = new ArrayList<Entity>(), newEntities = new ArrayList<Entity>();
	private KeyboardInput input = new KeyboardInput();
	private Dimension gameArea = new Dimension(1500, 1500);
	private GameMode gameMode;
	private boolean running;
	
	public Game(int width, int height) {
		window = new GameWindow(width, height);
		window.setLocationRelativeTo(null);
		window.addKeyListener(input);
		window.setResizable(false);
		window.setTitle("jpShooter");
	}
	
	public void reset() {
		entities.clear();
		newEntities.clear();
		running = false;
	}
	
	public void start() {
		gameMode.initialize(this);
		running = true;
		
		while (running) {
			// Zoom in or out
			// TODO Timing
			if (input.isKeyDown(KeyEvent.VK_PAGE_UP)) {
				window.addScale(0.02);
			} 
			else if (input.isKeyDown(KeyEvent.VK_PAGE_DOWN)) {
				window.addScale(-0.02);
			}
			
			// Update game state.
			gameMode.update(this);
			
			// Update entities.
			Iterator<Entity> iter = entities.iterator();
			while(iter.hasNext()) {
				Entity e = iter.next();
				e.update();
				
				if (!e.isValid()) iter.remove(); // Delete if invalid (not in use)
			}
			
			// Prepare any entities that were created during the last frame for use next time.
			if (newEntities.size() > 0) {
				entities.addAll(newEntities);
				newEntities.clear();
			}
			
			// Draw GUI elements
			window.setRelativePositioning(false);
			gameMode.drawHud(this);
			window.setRelativePositioning(true);
			
			window.finishedDrawing();
			// Thread.sleep(1);
		}
	}
	
	public void stop() {
		running = false;
	}
	
	public boolean registerEntity(Entity entity) {
		if (!running) return entities.add(entity); else return newEntities.add(entity);
	}
	
	public Player getPlayer() {
		for (Entity e : entities) {
			if (e.getClass().getName() == "Player") return (Player) e;
		}
		return null;
	}
	
	public Entity[] getEntityArray() {
		return entities.toArray(new Entity[entities.size()]);
	}
	
	public int getEntityCount() {
		return entities.size();
	}
	
	public Entity getEntity(int uid) {
		for (Entity e : entities)
			if (e.getUid() == uid) return e;
		
		return null;
	}
	
	public Entity[] getEntity(String type, Entity[] retval) {
		// Get entities by type
		int i = 0;
		for (Entity e : entities) {
			if (e.getClass().getName() == type) {
				retval[i] = e;
				if (++i >= retval.length) break; // Only interested in retval.length entities; exit
			}
		}
		return retval;
	}
	
	public GameWindow getWindow() {
		return window;
	}
	
	public KeyboardInput getInput() {
		return input;
	}

	public Dimension getGameArea() {
		return gameArea;
	}

	public void setGameArea(Dimension gameArea) {
		this.gameArea = gameArea;
	}
	
	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public boolean isRunning() {
		return running;
	}
	
	public static Game instance() {
		// Note: Singletons are bad (introduces global state) but I can't be bothered to make a completely kosher OO design.
		return instance; 
	}

}
