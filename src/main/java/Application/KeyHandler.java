package Application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Manages the keyboard inputs if the user, allowing other
 * classes to perform actions on them.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class KeyHandler implements KeyListener {

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean shootUp, shootDown, shootLeft, shootRight;
	public boolean enterPressed, escapePressed, mPressed, cPressed, iPressed, tPressed;

	//DEBUG ONLY*****************************************************************************
	public boolean killAll, tpToBoss, damagePlayer, fast;
	//***************************************************************************************

	@Override
	public void keyTyped(KeyEvent e)  { }

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		switch(code) {
			case KeyEvent.VK_W -> upPressed = true;
			case KeyEvent.VK_S -> downPressed = true;
			case KeyEvent.VK_A -> leftPressed = true;
			case KeyEvent.VK_D -> rightPressed = true;

			case KeyEvent.VK_UP -> shootUp = true;
			case KeyEvent.VK_DOWN -> shootDown = true;
			case KeyEvent.VK_LEFT -> shootLeft = true;
			case KeyEvent.VK_RIGHT -> shootRight = true;

			case KeyEvent.VK_ENTER -> enterPressed = true;
			case KeyEvent.VK_ESCAPE -> escapePressed = true;

			case KeyEvent.VK_C -> cPressed = true;
			case KeyEvent.VK_I -> iPressed = true;
			case KeyEvent.VK_M -> mPressed = true;

			//DEBUG ONLY*****************************************************************************
			case KeyEvent.VK_K -> killAll = true;
			case KeyEvent.VK_B -> tpToBoss = true;
			case KeyEvent.VK_L -> damagePlayer = true;
			case KeyEvent.VK_F -> fast = true;
			case KeyEvent.VK_T -> tPressed = true;
			//***************************************************************************************
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch(code) {
			case KeyEvent.VK_W -> upPressed = false;
			case KeyEvent.VK_S -> downPressed = false;
			case KeyEvent.VK_A -> leftPressed = false;
			case KeyEvent.VK_D -> rightPressed = false;

			case KeyEvent.VK_UP -> shootUp = false;
			case KeyEvent.VK_DOWN -> shootDown = false;
			case KeyEvent.VK_LEFT -> shootLeft = false;
			case KeyEvent.VK_RIGHT -> shootRight = false;

			case KeyEvent.VK_ENTER -> enterPressed = false;
			case KeyEvent.VK_ESCAPE -> escapePressed = false;

			case KeyEvent.VK_C -> cPressed = false;
			case KeyEvent.VK_I -> iPressed = false;
			case KeyEvent.VK_M -> mPressed = false;

			//DEBUG ONLY*****************************************************************************
			case KeyEvent.VK_K -> killAll = false;
			case KeyEvent.VK_B -> tpToBoss = false;
			case KeyEvent.VK_L -> damagePlayer = false;
			case KeyEvent.VK_F -> fast = false;
			case KeyEvent.VK_T -> tPressed = false;
			//***************************************************************************************
		}
	}
}

