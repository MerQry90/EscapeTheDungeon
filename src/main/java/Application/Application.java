package Application;

import javax.swing.*;
import java.awt.*;

/**
 * The starting point of the application,
 * it extends JFrame to make the application graphical and is also runnable.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class Application extends JFrame {
	public Application() {
		initUI();
	}

	/*
Creazione dell'oggetto GamePanel e settaggio delle sue proprietà.
GamePanel è ciò che permette di visualizzare a schermo l'applicazione.
	*/
	private void initUI() {
		GamePanel gamePanel = new GamePanel();
		add(gamePanel);
		//l'ordine di setResizable e pack è importante
		setResizable(false);
		pack();
		setTitle("OOPProject");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main (String[] args){
		/*
		tramite EventQueue.invokelater facciamo in modo che l'oggeto Application ex venga eseguito nel
		Dispatch Thread, il Thread in cui viene eseguito il codice della libreria Swing
		 */
		EventQueue.invokeLater(() -> {
			Application ex = new Application();
			ex.setVisible(true);
		});
	}
}
