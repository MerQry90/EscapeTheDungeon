package Components;

import Entities.DynamicEntities.Player;

import java.awt.*;

public class UI {

	public void drawUI(Graphics g, Player player){
		drawPlayerHeart(g, player);
		//chiamare qui tutti i metodi della UI
	}

	public void drawPlayerHeart(Graphics g, Player player){
		for(int i = 0; i < player.getMaxHealth(); i += 1){
			if(i < player.getHealth()) {
				g.drawImage(player.HEART_FULL, 2 + i * 40, 0, null);
			}
			else{
				g.drawImage(player.HEART_EMPTY, 2 + i * 40, 0, null);
			}
		}
	}
}
