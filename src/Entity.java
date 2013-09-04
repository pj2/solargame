import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author prenderj
 *
 */
public abstract class Entity {
	
	private static long lastUid = 0;
	private final long uid;
	
	private Allegiance allegiance = Allegiance.NEUTRAL;
	private String name;
	private Model model;
	private int health, maxHealth;
	private double rotation, boundingRadius; // Angle is in radians, boundingRadius = approximate area filled by this entity
	private long spawnTime;
	private Point2D.Double pos = new Point2D.Double(); // X and Y component with origin WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2
	private long invalidateStart = -1, deathLength = 300;
	
	// valid - true deletes the instance; invulnerable - true prevents changes to health; active - true allows entity updates
	private boolean valid = true, invulnerable = true, active; 
	
	protected Movable movableComponent;
	protected ArrayList<Drawable> drawComponents = new ArrayList<Drawable>();
	protected Collidable collisionComponent;
	
	public Entity(Point2D.Double pos) {
		this.uid = lastUid++;
		this.pos = pos;
		this.name = getClass().getName() + "_" + uid;
	}

	public void update() {
		Game game = Game.instance();
		if (movableComponent != null) movableComponent.move(this);
		
		// Prevent from leaving game area
		if (isOutOfBounds())
			leftGameArea();
		
		for (Drawable d : drawComponents) d.paint(this, game.getWindow());

		// Do collisions
		if (collisionComponent != null)
			for (Entity e : game.getEntityArray())
				if (this != e && collisionComponent.canCollide(this, e) && collisionComponent.checkForCollision(this, e)) {
					collisionComponent.collide(this, e);
				}
		
		// Finish off the entity
		if (isDead()) {
			long now = System.currentTimeMillis();
			if (invalidateStart == -1)
				invalidateStart = now + deathLength; // Give time for death animations
			
			if (now >= invalidateStart) {
				invalidate();
				invalidateStart = -1;
			}
			else {
				// Go boom
				game.getWindow().drawEllipse(getX(), getY(), Math.cos(now - invalidateStart) * (50+50*Math.random()), "YELLOW");
			}
		}
	}

	public boolean isDead() {
		return !invulnerable && health <= 0;
	}
	
	public boolean isOutOfBounds() {
		// If the model if fully hidden it is out of bounds
		Game game = Game.instance();
		Dimension area = game.getGameArea();
		
		double w = area.width / 2, h = area.height / 2, wiggleRoom = getBoundingRadius()*game.getWindow().getScale();
		return pos.x + wiggleRoom < -w || pos.x - wiggleRoom >= w || pos.y - wiggleRoom >= h || pos.y + wiggleRoom < -h;
	}
	
	public void leftGameArea() {
	}
	
	public void spawn() {
		setActive(true);
		setSpawnTime(System.currentTimeMillis());
	}

	public void invalidate() {
		valid = false;
	}
	
	public void takeDamage(int damage) {
		addHealth(-damage);
	}

	public void setBoundingRadiusFromModel(Model m) {
		double maxDistance = 0;
		double[][] structure = m.getStructure();
		double[] diameters = m.getDiameters();
		
		// Figure out an approximate size
		for (int i = 0; i < structure.length; i++) {
			maxDistance = Math.max(maxDistance, Math.hypot(structure[i][0], structure[i][1]) + diameters[i]);
		}
		
		setBoundingRadius(maxDistance);
		// System.out.println(getClass().getName()+" has radius "+getBoundingRadius());
	}
	
	public static double clampAngle(double angle) {
		angle %= Math.PI*2;
		while (angle < 0) angle += Math.PI*2;
		return angle;
	}
	
	public static double angleTo(double x1, double y1, double x2, double y2) {
		double d = Math.atan2(y2 - y1, x2 - x1);
		d = -d - Math.PI/2; // HACK - rotate to the weird broken system I'm using
		return clampAngle(d);
	}
	
	public double angleTo(Entity e) {
		return angleTo(e.getX(), e.getY(), getX(), getY());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = Math.max(Math.min(health, 15), 0);
	}

	public void addHealth(int health) {
		setHealth(this.health + health);
	}
	
	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		rotation = clampAngle(rotation);
		this.rotation = rotation;
	}

	public Point2D.Double getPos() {
		return pos;
	}

	public void setPos(Point2D.Double pos) {
		this.pos = pos;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Movable getMovableComponent() {
		return movableComponent;
	}

	public void setMovableComponent(Movable movableComponent) {
		this.movableComponent = movableComponent;
	}

	public ArrayList<Drawable> getDrawComponents() {
		return drawComponents;
	}

	public void setDrawComponents(ArrayList<Drawable> drawComponents) {
		this.drawComponents = drawComponents;
	}

	public Collidable getCollisionComponent() {
		return collisionComponent;
	}

	public void setCollisionComponent(Collidable collisionComponent) {
		this.collisionComponent = collisionComponent;
	}

	public long getUid() {
		return uid;
	}
	
	public double getX() {
		return pos.x;
	}
	
	public double getY() {
		return pos.y;
	}
	
	public double getBoundingRadius() {
		return boundingRadius;
	}

	public void setBoundingRadius(double boundingRadius) {
		this.boundingRadius = boundingRadius;
	}
	
	public boolean isInvulnerable() {
		return invulnerable;
	}
	
	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = Math.max(maxHealth, 0);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
	public Allegiance getAllegiance() {
		return allegiance;
	}

	
	public void setAllegiance(Allegiance allegiance) {
		this.allegiance = allegiance;
	}

	
	public long getSpawnTime() {
		return spawnTime;
	}

	
	public void setSpawnTime(long spawnTime) {
		this.spawnTime = spawnTime;
	}

	
	public long getDeathLength() {
		return deathLength;
	}

	
	public void setDeathLength(long deathLength) {
		this.deathLength = deathLength;
	}
	
}
