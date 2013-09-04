import java.awt.geom.Point2D;

public class ModelDrawable extends Drawable {

	public void paint(Entity e, GameWindow window) {
		Model model = e.getModel();
		if (model == null) return;
		
		double[][] structure = model.getStructure();
		double[] diameters = model.getDiameters();
		String[] colors = model.getColors();
		
		// Draw each 'point' in this entity's model.
		Point2D.Double pos = e.getPos();
		for (int i = 0; i < structure.length; i++) {
			window.drawEllipseAbout(pos.x, pos.y, structure[i][0], structure[i][1], e.getRotation(), diameters[i], colors[i]);
		}
	}

}
