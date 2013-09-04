
/**
 * @author prenderj
 *
 */
public enum Model {
	
	PLAYER(new double[][] {{0,-14}, {0,0}, {14,-24}, {-14,-24}, {8,-21}, {-8,-21}},
			new double[] {17, 16, 5, 5, 12, 12, 5},
			new String[] {"DARKGREY", "WHITE", "GREY", "GREY", "GREY", "GREY"}),
			
	FIGHTER(new double[][] {{0,0}, {0,-10}, {5,-10}, {-5, -10}, {9,-8}, {-9, -8}},
			new double[] {13, 11, 7, 7, 5, 5},
			new String[] {"RED", "GREY", "DARKGREY", "DARKGREY", "GREY", "GREY"}),
			
	BOMBER(new double[][] {{-7,9}, {7,9}, {0,7}, {0,-10}, {0,0}, {9,-8}, {-9,-8}},
			new double[] {8, 8, 10, 12, 11, 10, 10},
			new String[] {"RED", "RED", "RED", "DARKGREY", "GREY", "GREY", "GREY"}),
			
	PEA_BULLET(new double[][] {{0,0}, {0,5}},
			new double[] {5, 5},
			new String[] {"ORANGE", "ORANGE"}),
			
	MLASER_BULLET(new double[][] {{7,0}, {-7,0}, {0, 0}},
			new double[] {10, 10, 10},
			new String[] {"CYAN", "CYAN", "WHITE"}),
			
	SPACE_STATION(new double[][] {{0,0}, {-35,10}, {35,-15}},
			new double[] {130, 30, 20},
			new String[] {"DARKGREY", "GREY", "GREY"}),
			
	POWERUP(new double[][] {{0,0}, {-5,0}, {5,0}, {0,5}, {0,-5}},
			new double[] {10, 10, 10, 10, 10},
			new String[] {"RED", "RED", "RED", "RED", "RED"});
	
	private double[][] structure;
	private double[] diameters;
	private String[] colors;
	
	Model(double[][] structure, double[] diameters, String[] colors) {
		this.structure = structure;
		this.diameters = diameters;
		this.colors = colors;
	}
	
	public double[][] getStructure() {
		return structure;
	}
	
	public double[] getDiameters() {
		return diameters;
	}
	
	public String[] getColors() {
		return colors;
	}

}
