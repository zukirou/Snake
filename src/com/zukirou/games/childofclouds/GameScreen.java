package com.zukirou.games.childofclouds;

import java.util.List;

import android.graphics.Color;

import com.zukirou.gameFrameWork.Game;
import com.zukirou.gameFrameWork.Graphics;
import com.zukirou.gameFrameWork.Input.TouchEvent;
import com.zukirou.gameFrameWork.Pixmap;
import com.zukirou.gameFrameWork.Screen;

public class GameScreen extends Screen{
	enum GameState{
		Ready,
		Running,
		Paused,
		GameOver
	}
	
//	GameState state = GameState.Ready;
	GameState state = GameState.Running;
	World world;
	int oldScore = 0;
	String score = "0";
	
	public GameScreen(Game game){
		super(game);
		world = new World();
	}
	
	@Override
	public void update(float deltaTime){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
//		if(state == GameState.Ready)
//			updateReady(touchEvents);
		if(state == GameState.Running)
			updateRunning(touchEvents,deltaTime);
		if(state == GameState.Paused)
			updatePaused(touchEvents);
		if(state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

/*	Ready表示時にタッチすると、Ready終了直後にPauseに行ってしまうためReady.javaを追加。
	private void updateReady(List<TouchEvent> touchEvents){
		if(touchEvents.size() > 0)
			state = GameState.Running;
	}
*/
	
	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime){
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_DOWN ){
				if(event.x > 0 && event.x < 320 && event.y < 400 && event.y > 0 ){
					if(Settings.soundEnabled)
						Assets.click.play(1);
					state = GameState.Paused;
					return;
				}
			}
			if(event.type == TouchEvent.TOUCH_DOWN){
				if(event.x < 64 && event.y > 416){
					world.drop.turnLeft();
				}
				if(event.x > 256 && event.y > 416){
					world.drop.turnRight();
				}
			}
		}
		
		world.update(deltaTime);
		if(world.gameOver){
			if(Settings.soundEnabled)
				Assets.bitten.play(1);
			state = GameState.GameOver;
		}
		
		if(oldScore != world.score){
			oldScore = world.score;
			score = "" + oldScore;
//			if(Settings.soundEnabled)	これがここにあると、スコア要素を追加した場合、スコアに変動があるたびに
//				Assets.eat.play(1);		同じ音がなってしまう。ホコリをたべた時だけなるようにした（World.java）
		}
		
	}
	
	private void updatePaused(List<TouchEvent> touchEvents){
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				if(event.x > 80 && event.x <= 240){
					if(event.y > 100 && event.y <= 148){
						if(Settings.soundEnabled)
							Assets.click.play(1);
						state = GameState.Running;
						return;
					}
					if(event.y > 148 && event.y < 196){
						if(Settings.soundEnabled)
							Assets.click.play(1);
						game.setScreen(new MainMenuScreen(game));
						return;
					}
				}
			}
		}
	}
	
	private void updateGameOver(List<TouchEvent> touchEvents){
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				if(	event.x >= 128 && event.x <= 192 && 
					event.y >= 200 && event.y <= 264){
					if(Settings.soundEnabled)
						Assets.click.play(1);
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}
	
	@Override
	public void present(float deltaTime){
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.background, 0, 0);
		drawWorld(world);
//		if(state == GameState.Ready)
//			drawReadyUI();
		if(state == GameState.Running)
			drawRunningUI();
		if(state == GameState.Paused)
			drawPausedUI();
		if(state == GameState.GameOver)
			drawGameOverUI();
		drawText(g, score, g.getWidth() / 2 - score.length() * 20 / 2, g.getHeight() - 42);
	}
	
	private void drawWorld(World world){
		Graphics g = game.getGraphics();
		Drop drop = world.drop;
		BodyCloud head = drop.cloud.get(0);
		Dust dust = world.dust;
		BlackDrop blackdrop = world.blackdrop;
		
		if(MainMenuScreen.vsFlag == 1){
			BlackBodyCloud blackhead = blackdrop.blackbodycloud.get(0);
			
			int len = blackdrop.blackbodycloud.size();
			for(int j = 1; j < len; j++){
				BlackBodyCloud blackbodycloud = blackdrop.blackbodycloud.get(j);
				int blackbodycloud_x = blackbodycloud.x * 32;
				int blackbodycloud_y = blackbodycloud.y * 32;
				g.drawPixmap(Assets.blackcloud, blackbodycloud_x, blackbodycloud_y);
			}
			Pixmap blackheadPixmap = null;
			if(blackdrop.blackdirection == BlackDrop.UP)
				blackheadPixmap = Assets.blackheadup;
			if(blackdrop.blackdirection == BlackDrop.LEFT)
				blackheadPixmap = Assets.blackheadleft;
			if(blackdrop.blackdirection == BlackDrop.DOWN)
				blackheadPixmap = Assets.blackheaddown;
			if(blackdrop.blackdirection == BlackDrop.RIGHT)
				blackheadPixmap = Assets.blackheadright;
			int blackheadX = blackhead.x * 32 + 16;
			int blackheadY = blackhead.y * 32 + 16;
			g.drawPixmap(blackheadPixmap, 	blackheadX - blackheadPixmap.getWidth() / 2, 
											blackheadY - blackheadPixmap.getHeight() /2);
			
		}
		
		
		Pixmap dustPixmap = null;
		if(dust.type == Dust.TYPE_1)
			dustPixmap = Assets.dust1;
		if(dust.type == Dust.TYPE_2)
			dustPixmap = Assets.dust2;
		if(dust.type == Dust.TYPE_3)
			dustPixmap = Assets.dust3;
		int x = dust.x * 32;
		int y = dust.y * 32;
		g.drawPixmap(dustPixmap, x, y);

		int len = drop.cloud.size();
		for(int i = 1; i < len; i++){
			BodyCloud cloud = drop.cloud.get(i);
			x = cloud.x * 16;
			y = cloud.y * 16;
			g.drawPixmap(Assets.bodycloud, x, y);
		}
		Pixmap headPixmap = null;
		if(drop.direction == Drop.UP)
			headPixmap = Assets.headup;
		if(drop.direction == Drop.LEFT)
			headPixmap = Assets.headleft;
		if(drop.direction == Drop.DOWN)
			headPixmap = Assets.headdown;
		if(drop.direction == Drop.RIGHT)
			headPixmap = Assets.headright;
		x = head.x * 16 + 16;
		y = head.y * 16 + 16;
		g.drawPixmap(headPixmap, 	x - headPixmap.getWidth() / 2,
									y - headPixmap.getHeight() / 2);

	}
/*	
	private void drawReadyUI(){
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.ready, 47, 100);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}
*/	
	private void drawRunningUI(){
		Graphics g = game.getGraphics();
		
//		g.drawPixmap(Assets.buttons, 128, 416, 64, 128, 64, 64);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
		g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
		g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
		g.drawPixmap(Assets.mainmenu, 105, 415, 86, 45, 196, 45);			

	}
	
	private void drawPausedUI(){
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.pause, 80, 100);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}
	
	private void drawGameOverUI(){
		Drop drop = world.drop;
		Graphics g = game.getGraphics();
		BodyCloud head = drop.cloud.get(0);
		
		g.drawPixmap(Assets.gameover, 62, 100);
		g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
		
		Pixmap headPixmap = null;
		if(drop.direction == Drop.UP)
			headPixmap = Assets.headupng;
		if(drop.direction == Drop.LEFT)
			headPixmap = Assets.headleftng;
		if(drop.direction == Drop.DOWN)
			headPixmap = Assets.headdownng;
		if(drop.direction == Drop.RIGHT)
			headPixmap = Assets.headrightng;
		int x = head.x * 32 + 16;
		int y = head.y * 32 + 16;
		g.drawPixmap(headPixmap, 	x - headPixmap.getWidth() / 2,
									y - headPixmap.getHeight() / 2);
		
		
		for(int i = 0; i < 5; i++){//	ハイスコア更新したことを知らせる
			if(oldScore > Settings.highscores[i]){
				g.drawPixmap(Assets.highscoreupdate, 58, 290);
				break;
			}
		}
	}
	
	public void drawText(Graphics g, String line, int x, int y){
		int len = line.length();
		for(int i = 0; i < len; i++){
			char character = line.charAt(i);
			
			if(character == ' '){
				x += 20;
				continue;
			}
			
			int srcX = 0;
			int srcWidth = 0;
			if(character == '.'){
				srcX = 200;
				srcWidth = 20;
			}else{
				srcX = (character - '0') * 20;
				srcWidth = 20;
			}
			
			g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
//			g.drawPixmap(Assets.mainmenu, 105, 415, 86, 45, 196, 45);			
			x += srcWidth;
		}
	}
	
	@Override
	public void pause(){
		if(state == GameState.Running)
			state = GameState.Paused;
		
		if(world.gameOver){
			Settings.addScore(world.score);
			Settings.save(game.getFileIO());
		}
	}
	
	@Override
	public void resume(){
		
	}
	
	@Override
	public void dispose(){
		
	}
}
