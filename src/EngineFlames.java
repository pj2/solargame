import java.awt.geom.Point2D;

public class EngineFlames extends Drawable {
	
	private int[][] enginePositions;
	private double flameSize = 1;
	private String flameColor = "CYAN";
	
	public EngineFlames(int[][] enginePositions) {
		super();
		this.enginePositions = enginePositions;
	}

	@Override
	public void paint(Entity e, GameWindow window) {
		// Draw engine 'flames' for coolness
		double diameter = e.getMovableComponent().getMoveSpeed()*getFlameSize();
		
		for (int[] engine : enginePositions) {
			if (diameter > 1.5) {
				window.drawEllipseAbout(e.getX(), e.getY(), engine[0], engine[1] - diameter/2,
						e.getRotation(), diameter, getFlameColor());
			}
			if (diameter > 5)
				window.drawEllipseAbout(e.getX(), e.getY(), engine[0], engine[1] - diameter,
						e.getRotation(), diameter/2, getFlameColor());
		}
	}

	public int[][] getEnginePositions() {
		return enginePositions;
	}

	public void setEnginePositions(int[][] enginePositions) {
		this.enginePositions = enginePositions;
	}

	public double getFlameSize() {
		return flameSize;
	}

	public void setFlameSize(double flameSize) {
		this.flameSize = flameSize;
	}

	public String getFlameColor() {
		return flameColor;
	}

	public void setFlameColor(String flameColor) {
		this.flameColor = flameColor;
	}

}
