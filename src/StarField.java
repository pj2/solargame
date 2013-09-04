import java.awt.geom.Point2D;

public class StarField extends Entity {

	public StarField() {
		super(new Point2D.Double());
		getDrawComponents().add(new StarFieldEffect(200));
	}

}
