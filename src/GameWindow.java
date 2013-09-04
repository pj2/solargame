import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.Random;

public class GameWindow extends SolarSystem {
	
	private double scale = 1.1;
	private static final Random rng = new Random();
	private long screenShakeEnd;
	private double[] transformationTemp = new double[2]; // Store temporary values here to avoid allocations
	private Entity focus; // Follow this entity around.
	private boolean relativePositioning;

	public GameWindow(int width, int height) {
		super(width, height);
	}
	
	public void drawEllipseAbout(double centreX, double centreY, double offsetX, double offsetY, double rotation, double diameter, String color) {
		if (focus != null && relativePositioning) {
			double[] newCoords = applyScreenTransformation(centreX, centreY);
			centreX = newCoords[0];
			centreY = newCoords[1];
		}
		
		// Rendering will look a bit 'jumpy' because of precision loss (radians to degrees and back is bad).
		double mul = relativePositioning ? scale : 1;
		drawSolarObjectAbout(Math.hypot(offsetX, offsetY) * mul,
				Math.toDegrees(Math.atan2(offsetX, offsetY)) + Math.toDegrees(rotation),
				diameter * mul,
				color, 
				Math.hypot(centreX, centreY) * mul, Math.toDegrees(Math.atan2(centreX, centreY)));
	}
	
	public void drawEllipse(double x, double y, double diameter, String color) {
		drawEllipseAbout(x, y, 0, 0, 0, diameter, color);
	}
	
	public double[] applyScreenTransformation(double x, double y) {
		// Follow the focus.
		Game game = Game.instance();
		Point2D.Double pos = focus.getPos();
		Dimension gameBounds = game.getGameArea(), cameraBounds = game.getWindow().getContentPane().getSize();
		double dx = pos.x * 0.7, dy = pos.y * 0.7;
		
		// Shake the screen! (aka break the graphics engine for a few ms)
		if (isScreenShaking()) {
			double a = rng.nextDouble() * 5 * (1 - rng.nextInt(2)*2),
					b = rng.nextDouble() * 5 * (1 - rng.nextInt(2)*2);
			dx += a;
			dy += b;
		}
		
		// Keep camera within the game area.
		double w1 = cameraBounds.width/2*scale, h1 = cameraBounds.height/2*scale,
				w2 = gameBounds.width/2*scale, h2 = gameBounds.height/2*scale;
		
		if (dx + w1 > w2) 
			dx = w2 - w1;
		else if (dx - w1 < -w2)
			dx = -w2 + w1;
		
		if (dy + h1 > h2)
			dy = h2 - h1;
		else if (dy - h1 < -h2)
			dy = -h2 + h1;
				
		transformationTemp[0] = x - dx;
		transformationTemp[1] = y - dy;
		return transformationTemp;
	}
	
	@Override
	public void paint(Graphics gr) {
		super.paint(gr);
	}

	public Entity getFocus() {
		return focus;
	}

	public void setFocus(Entity focus) {
		this.focus = focus;
	}

	public boolean isRelativePositioning() {
		return relativePositioning;
	}

	public void setRelativePositioning(boolean relativePositioning) {
		this.relativePositioning = relativePositioning;
	}
	
	public boolean isScreenShaking() {
		return System.currentTimeMillis() < screenShakeEnd;
	}
	
	public void applyScreenShake(long duration) {
		screenShakeEnd = System.currentTimeMillis() + duration;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = Math.max(0.9, Math.min(1.75, scale));
	}
	
	public void addScale(double add) {
		setScale(getScale() + add);
	}

}
