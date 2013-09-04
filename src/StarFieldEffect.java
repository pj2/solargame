import java.util.Random;


public class StarFieldEffect extends Drawable {
	
	private int numStars;
	private double[] variation;
	
	public StarFieldEffect(int numStars) {
		this.numStars = numStars;
		variation = new double[numStars];
		
		Random rng = new Random();
		for (int i = 0; i < numStars; i++) {
			double mul = rng.nextFloat() > 0.5 ? -1 : 1;
			variation[i] = rng.nextDouble() * mul;
		}
	}

	@Override
	public void paint(Entity e, GameWindow window) {
		double boundX = Game.instance().getGameArea().width, boundY = Game.instance().getGameArea().height;
		for (int i = 0; i < numStars; i++) {
			window.drawEllipse(boundX/2*variation[i], boundY/2*variation[numStars-i-1], 3, i%2==0 ? "DARKGREY" : "GREY");
		}
	}

}
