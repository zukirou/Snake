package com.zukirou.games.childofclouds;

import java.util.List;
import com.zukirou.gameFrameWork.Game;
import com.zukirou.gameFrameWork.Graphics;
import com.zukirou.gameFrameWork.Input.TouchEvent;
import com.zukirou.gameFrameWork.Screen;

public class MainMenuScreen extends Screen{
	
	static int vsFlag = 0;
	
	public MainMenuScreen(Game game){
		super(game);
	}
	@Override
	public void update(float deltaTime){
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		int len = touchEvents.size();
		
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				if(inBounds(event, 0, g.getHeight() - 64, 64, 64)){
					Settings.soundEnabled = !Settings.soundEnabled;
					if(Settings.soundEnabled)
						Assets.click.play(1);
				}
				if(inBounds(event, 64, 220, 192, 42)){
					vsFlag = 0;
					game.setScreen(new ReadyScreen(game));
					if(Settings.soundEnabled)
						Assets.click.play(1);
					return;
				}
				if(inBounds(event, 64, 220 + 42, 192, 42)){
					vsFlag = 0;
					game.setScreen(new HighscoreScreen(game));
					if(Settings.soundEnabled)
						Assets.click.play(1);
					return;
				}
				if(inBounds(event, 64, 220 + 84, 192, 42)){
					vsFlag = 0;
					game.setScreen(new HelpScreen(game));
					if(Settings.soundEnabled)
						Assets.click.play(1);
					return;
				}
				if(inBounds(event, 64, 220 + 168, 192, 42)){
					vsFlag = 1;
					game.setScreen(new ReadyScreen(game));
					if(Settings.soundEnabled)
						Assets.click.play(1);
					return;
				}
			}
		}
	}
	
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height){
		if(event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1)
			return true;
		else
			return false;
	}
	
	@Override
	public void present(float deltaTime){
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.background, 0, 0);
		g.drawPixmap(Assets.logo, 32, 32);
		g.drawPixmap(Assets.mainmenu, 64, 220);
		if(Settings.soundEnabled)
			g.drawPixmap(Assets.buttons, 0, 416, 0, 0, 64, 64);
		else
			g.drawPixmap(Assets.buttons, 0, 416, 64, 0, 64, 64);
	}
	
	@Override
	public void pause(){
		Settings.save(game.getFileIO());
	}
	
	@Override
	public void resume(){
		
	}
	
	@Override
	public void dispose(){
		
	}
}