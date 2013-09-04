import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

/**
 * @author prenderj
 *
 */
public class KeyboardInput implements KeyListener {
	
	private static final int MAX_KEYS = 256;
	private HashSet<Integer> keys = new HashSet<Integer>();
	
	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode > 0 && keyCode < MAX_KEYS)
			keys.add(keyCode);
	}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode > 0 && keyCode < MAX_KEYS)
			keys.remove(keyCode);
	}

	/**
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// Do nothing
	}

	public boolean isKeyDown(int keyCode) {
		return keys.contains(keyCode);
	}

}
