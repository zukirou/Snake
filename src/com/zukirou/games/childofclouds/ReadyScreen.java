package com.zukirou.games.childofclouds;

import java.util.List;

import com.zukirou.gameFrameWork.Game;
import com.zukirou.gameFrameWork.Graphics;
import com.zukirou.gameFrameWork.Input.TouchEvent;
import com.zukirou.gameFrameWork.Screen;

public class ReadyScreen extends Screen{
	public ReadyScreen(Game game){
		super(game);
	}
	@Override
	public void update(float deltaTime){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		int len = touchEvents.size();
		for(int i = 0; i < len ; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				if(event.x > 0 && event.x < 320 && event.y < 400 && event.y > 0 ){
					game.setScreen(new GameScreen(game));;
					if(Settings.soundEnabled)
						Assets.click.play(1);
					return;
				}
			}
		}
	}
	
	@Override
	public void present(float deltaTime){
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.background, 0, 0);
		g.drawPixmap(Assets.ready, 47, 100);
	}
	
	@Override
	public void pause(){
		
	}
	
	@Override
	public void resume(){
		
	}
	
	@Override
	public void dispose(){
		
	}
	
}