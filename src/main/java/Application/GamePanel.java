package Application;

import GameStates.End;
import GameStates.GameState;
import GameStates.MainGame;
import GameStates.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
la classe GamePanel implementa ActionListener in quanto è il "ciclo principale" del gioco,
ciò che gli permette di funzionare e andare avanti
GamePanel è fondamentalmente la classe che si occupa, oltre appunto del ciclo principale,
 di gestire gli stati del gioco, il resto viene gestito da ogni stato singolarmente
 */
public class GamePanel extends JPanel implements ActionListener {

	private final int FPS = 30;
	private GameState gameState;
	private Timer timer;

	public GamePanel() {

		//lo stato iniziale dell'applicazione è quello di menu principale
		gameState = new MainMenu();

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
	}

	/*
	nextState controlla cos'è gameState: se un MainMenu, un MainGame o End, e in base allo stato in cui si trova
	passa a quello successivo
	 */
	public void nextState(){
		if (gameState instanceof MainMenu) {
			gameState = new MainGame();
		}
		else if (gameState instanceof MainGame) {
			gameState = new End();
		}
		else if (gameState instanceof End) {
			gameState = new MainMenu();
		}
	}
}
