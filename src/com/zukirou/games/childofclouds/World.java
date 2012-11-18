package com.zukirou.games.childofclouds;

import java.util.Random;



public class World{
	static final int WORLD_WIDTH = 10;
	static final int WORLD_HEIGHT = 13;
	static final int SCORE_INCREMENT = 10;
	static final float TICK_INITIAL = 0.5f;
	static final float TICK_DECREMENT = 0.05f;
	
	public int eatcount = 0;
	
	public Drop drop;
	public Dust dust;
	public BlackDrop blackdrop;
	public boolean gameOver = false;
	public boolean dustAppear = true;
	public int score;
	
	boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
	Random random = new Random();
	float tickTime = 0;
	public float tick = TICK_INITIAL;
	
	static int blackgoalx;
	static int blackgoaly;
	
//	static float tick = TICK_INITIAL; //staticだと最初からやり直しても速度が初期値にならない。ゲームオーバー時の速度のままになる。
	
	public World(){
		drop = new Drop();
		placeDust();
				
		if(MainMenuScreen.vsFlag == 1)
			blackdrop = new BlackDrop();
			
	}
	
	private void placeDust(){
		for(int x = 0; x < WORLD_WIDTH; x++){
			for (int y = 0; y < WORLD_HEIGHT; y++){
				fields[x][y] = false;
			}
		}
		
		int len = drop.cloud.size();
		for(int i = 0; i < len; i++){
			BodyCloud cloud = drop.cloud.get(i);
			fields[cloud.x][cloud.y] = true;
		}
		
		int blacklen = BlackDrop.blackbodycloudlength.size();
		for(int i = 0; i < blacklen; i++){
			BlackBodyCloud blackcloud = BlackDrop.blackbodycloudlength.get(i);
			fields[blackcloud.x][blackcloud.y] = true;
		}

		
		int dustX = random.nextInt(WORLD_WIDTH);
		int dustY = random.nextInt(WORLD_HEIGHT);
		while(true){
			if(fields[dustX][dustY] == false)
				break;
			dustX += 1;
			if(dustX  >= WORLD_WIDTH){
				dustX = 0;
				dustY += 1;
				if(dustY >= WORLD_HEIGHT){
					dustY = 0;
				}
			}
		}
		blackgoalx = dustX;
		blackgoaly = dustY;
		dust = new Dust(dustX, dustY, random.nextInt(3));
	}
		
	public void update(float deltaTime){
		if(gameOver)
			return;
		
		tickTime += deltaTime;
		
		while(tickTime > tick){
						
			tickTime -= tick;
			drop.advance();
			if(drop.checkBitten()){
				gameOver = true;
				return;
			}
			BodyCloud head = drop.cloud.get(0);
			if(head.x == dust.x && head.y == dust.y){
				if(Settings.soundEnabled){
					Assets.eat.play(1);
				}
				score += SCORE_INCREMENT;
				eatcount ++;
				drop.eat();
				dustAppear = false;
				if(drop.cloud.size() == WORLD_WIDTH * WORLD_HEIGHT){//体がフィールドを埋め尽くした
					gameOver = true;
					return;
				}
				if(eatcount == 5 && tick - TICK_DECREMENT > 0){
					tick -= TICK_DECREMENT;
					eatcount = 0;
				}
				
/*
				if(score % 100 == 0 && tick - TICK_DECREMENT > 0){
					tick -= TICK_DECREMENT;
				}
*/				
			}
			if(score > 150)
				score += 1;//150点以上ならば、移動するだけで得点が入るように
						
			if(dustAppear == false){
				placeDust();
				dustAppear = true;
			}
			
			if(MainMenuScreen.vsFlag == 1){
				blackdrop.advance();
				if(blackdrop.checkBitten()){
					score += 10 + (BlackDrop.blackbodycloudlength.size() * 10 / 2);
					blackdrop = new BlackDrop();
					return;
				}
				BlackBodyCloud blackhead = blackdrop.blackbodycloud.get(0);
				if(blackhead.x == dust.x && blackhead.y == dust.y){
					blackdrop.eat();
					dustAppear = false;
				}
				if(blackdrop.blackbodycloud.size() == WORLD_WIDTH * WORLD_HEIGHT){
					gameOver = true;
					return;
				}
			}			
		}
	}
}