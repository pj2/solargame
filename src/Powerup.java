import java.awt.geom.Point2D.Double;

public class Powerup extends Entity {

	private int framesToLive = 400;
	
	public Powerup(Double pos) {
		super(pos);
		setModel(Model.POWERUP);
		getDrawComponents().add(new ModelDrawable());
		setCollisionComponent(new PowerupCollision());
		setBoundingRadiusFromModel(getModel());
	}
	
	@Override
	public void update() {
		if (framesToLive-- <= 0)
			invalidate();
		else
			super.update();
	}

}
