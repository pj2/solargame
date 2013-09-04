import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

import javax.swing.JOptionPane;

/**
 * @author prenderj
 *
 */
public class Player extends Entity {
	
	private long score, invulnerabilityEnd;
	private int lives = 3;
	private Weapon weapon = new MegaLaser(this);
	
	public Player(Point2D.Double pos) {
		super(pos);
		
		setModel(Model.PLAYER);
		setBoundingRadiusFromModel(getModel());
		setRotation(Math.PI);
		setHealth(10);
		setInvulnerable(false);
		setAllegiance(Allegiance.FRIENDLY);
		
		setMovableComponent(new PlayerMovement());
		getDrawComponents().add(new ModelDrawable());
		getDrawComponents().add(new EngineFlames(new int[][] {{8,-27}, {-8,-27}}));
	}

	/**
	 * @see Entity#update(Game)
	 */
	@Override
	public void update() {
		super.update();
		
		if (weapon.isReady() && Game.instance().getInput().isKeyDown(KeyEvent.VK_SPACE))
			weapon.fire();
		
		// System.out.println("x = " + getX() + ", y = " + getY());
	}
	
	@Override
	public void invalidate() {
		if (--lives == 0) {
			Game game = Game.instance();
			GameWindow window = game.getWindow();
			Object[] options = {"Play again", "Quit"};
			
			int n = JOptionPane.showOptionDialog(window,
				"Oh no, you died! You took "+getScore()+" enemy scum with you!",
				"Game Over",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
			
			if (n == JOptionPane.YES_OPTION) {
				game.reset();
				game.start();
			} 
			else if (n == JOptionPane.NO_OPTION) {
				game.stop();
				window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
			}
		} else {
			invulnerabilityEnd = System.currentTimeMillis() + 3500;
			setHealth(5);
		}
	}
	
	@Override
	public void takeDamage(int damage) {
		if (System.currentTimeMillis() >= invulnerabilityEnd) {
			super.takeDamage(damage);
			Game.instance().getWindow().applyScreenShake((long) (250 + Math.random()*100));
		}
	}
	
	@Override
	public void leftGameArea() {
		setRotation(angleTo(0, 0, getX(), getY()));
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public void addScore(int i) {
		setScore(getScore() + i);
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
}
