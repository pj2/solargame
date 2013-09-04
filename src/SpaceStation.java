import java.awt.geom.Point2D;

public class SpaceStation extends Entity {

	public SpaceStation(Point2D.Double pos) {
		super(pos);
		setModel(Model.SPACE_STATION);
		getDrawComponents().add(new ModelDrawable());
	}

}
