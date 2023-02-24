package Components;

import Application.Application;
import Application.GamePanel;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;

public class AudioManager {
	
	private Clip clip;
	private Map<String, URL> audioUrlMap;
	
	public AudioManager(){
		audioUrlMap = new HashMap<>();
		
		audioUrlMap.put("player got hit", getClass().getResource("src/resources/Audio/playerGotHit.wav"));
	}
	
	public void setClip(String audioName){
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(audioUrlMap.get(audioName));
			clip = AudioSystem.getClip();
			clip.open(ais);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public void play(){
		clip.start();
	}
	public void loop(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop(){
		clip.stop();
	}
}
