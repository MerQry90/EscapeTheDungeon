package Application;

import Components.AudioManager;
import Components.Background;
import GameStates.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main loop of the game, updated 30 times per second.
 * GamePanel also manages the various states of the game.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class GamePanel extends JPanel implements ActionListener {

	/**
	 * FPS stands for frame per seconds
	 */
	private final int FPS = 30;
	private GameState gameState;
	private Timer timer;
	private Background background;
	private KeyHandler keyH;
	private AudioManager audioManager;

	/**
	 * initialize application window parameters, the input handler, the audio manager and the first state of the game
	 */
	public GamePanel() {
		
		//impostazioni finestra 17x9
		setPreferredSize(new Dimension(1088, 576));
		setFocusable(true);
		setDoubleBuffered(true);

		//inizializzazione keyHandler
		keyH = new KeyHandler();
		addKeyListener(keyH);
		
		//audiomanager
		audioManager = new AudioManager();
		
		//lo stato iniziale dell'applicazione è quello di menu principale
		nextState();
		
		//avvio del programma
		timer = new Timer(Delay(), this);
		timer.start();
	}

	/*
	la funzione si chiama Delay perché è letteralmente il delay tra più intervalli in cui viene chiamato
	il metodo ActionPerformed
	 */
	private int Delay() {
		return 1000 / FPS;
	}

	/*
	il metodo actionPerformed controlla se il gameState è "Attivo", ovvero se la variabile active è true o false,
	se è false vuol dire che è stato ricevuto l'input per il passaggio al gameState successivo,
	e chiamerà quindi nextState.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (gameState.isActive()){
			gameState.update();
		}
		else {
			nextState();
		}
		repaint();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		gameState.render(g);
	}

	/*
	nextState controlla cos'è gameState: se un MainMenu, un MainGame o End, e in base allo stato in cui si trova
	passa a quello successivo
	 */
	public void nextState(){
		if (gameState instanceof MainMenu) {
			gameState = new MainGame(keyH, audioManager);
		}
		else if (gameState instanceof MainGame) {
			if(((MainGame) gameState).win) {
				gameState = new GameWin(keyH, audioManager);
			}
			else {
				gameState = new GameOver(keyH, audioManager);
			}
		}
		else {
			gameState = new MainMenu(keyH, audioManager);
		}
	}
}
