package com.zukirou.games.childofclouds;

import java.util.ArrayList;
import java.util.List;

public class BlackDrop{

	public int x, y;
	
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	
	public List<BlackBodyCloud> blackbodycloud = new ArrayList<BlackBodyCloud>();
	static List<BlackBodyCloud> blackbodycloudlength = new ArrayList<BlackBodyCloud>();
	
	public int blackdirection;
	
	boolean fields[][] = new boolean[World.WORLD_WIDTH][World.WORLD_HEIGHT];
	
	public BlackDrop(){
		blackdirection = DOWN;
		blackbodycloud.add(new BlackBodyCloud(4,8));
		blackbodycloud.add(new BlackBodyCloud(4,7));
		blackbodycloud.add(new BlackBodyCloud(4,6));
	}
	
	public void eat(){
		BlackBodyCloud end = blackbodycloud.get(blackbodycloud.size() - 1);
		blackbodycloud.add(new BlackBodyCloud(end.x, end.y));
	}
	
	public void advance(){
		BlackBodyCloud blackhead = blackbodycloud.get(0);
		blackbodycloudlength = blackbodycloud;
		
		int len = blackbodycloud.size() - 1; 
		for (int i = len; i > 0; i --){
			BlackBodyCloud before = blackbodycloud.get(i - 1);
			BlackBodyCloud blackbodycloud1 = blackbodycloud.get(i);
			blackbodycloud1.x = before.x;
			blackbodycloud1.y = before.y;
		}
		
		blackdirection = ableTurn(blackhead.x, blackhead.y, blackdirection);

		switch(blackdirection){
		case UP:
			if(checkAdvance(blackhead.x, blackhead.y, 0)){
				blackhead.y -= 1;
				break;
			}
		case LEFT:
			if(checkAdvance(blackhead.x, blackhead.y, 1)){
				blackhead.x -= 1;
				break;
			}
		case DOWN:
			if(checkAdvance(blackhead.x, blackhead.y, 2)){
				blackhead.y += 1;
				break;
			}
		case RIGHT:
			if(checkAdvance(blackhead.x, blackhead.y, 3)){
				blackhead.x += 1;
				break;
			}
		default:
			break;
		}
		
		if(blackhead.x < 0)
			blackhead.x = 9;
		if(blackhead.x > 9)
			blackhead.x = 0;
		if(blackhead.y < 0)
			blackhead.y = 12;
		if(blackhead.y > 12)
			blackhead.y = 0;

	}
	
	public boolean checkBitten(){

		int len = blackbodycloud.size();		
		BlackBodyCloud blackhead = blackbodycloud.get(0);
		int pclen = Drop.cloudlength.size();
		
		for (int i = 1; i < len; i++){
			BlackBodyCloud blackbodycloud1 = blackbodycloud.get(i);
			if(blackbodycloud1.x == blackhead.x && blackbodycloud1.y == blackhead.y)
				return true;
		}
		
		for (int j = 1; j < pclen - 1; j++){
			BodyCloud cloud1 = Drop.cloudlength.get(j);
			if(cloud1.x == blackhead.x && cloud1.y == blackhead.y)
				return true;
		}
		
		return false;
	}
	
	private boolean checkAdvance(int blackdropx, int blackdropy, int direction){		
		//雲ある場所セット
		for(int x = 0; x < World.WORLD_WIDTH; x++){
			for (int y = 0; y < World.WORLD_HEIGHT; y++){
				fields[x][y] = false;
			}
		}
		
		int pclen = Drop.cloudlength.size();
		for(int i = 0; i < pclen; i++){
			BodyCloud cloud = Drop.cloudlength.get(i);
			fields[cloud.x][cloud.y] = true;
		}
		
		int len = blackbodycloud.size();
		for(int i = 0; i < len; i++){
			BlackBodyCloud blackcloud = blackbodycloud.get(i);
			fields[blackcloud.x][blackcloud.y] = true;
		}
		
		
		//前進許可チェック
		switch(direction){
		case 0:
			if(blackdropy - 1 < 0)
				blackdropy = 12;
			if(fields[blackdropx][blackdropy - 1] == false){
				return true;
			}
		case 1:
			if(blackdropx - 1 < 0)
				blackdropx = 9;
			if(fields[blackdropx - 1][blackdropy] == false){
				return true;
			}
		case 2:
			if(blackdropy + 1 > 12)
				blackdropy = 0;
			if(fields[blackdropx][blackdropy + 1] == false){
				return true;
			}
		case 3:
			if(blackdropx + 1 > 9)
				blackdropx = 0;
			if(fields[blackdropx + 1][blackdropy] == false){
				return true;
			}
		default:
			break;
		}
		return false;
	}
	
	private int ableTurn(int blackdropx, int blackdropy, int direction){
		switch(direction){
		case 0://UP
//			blackdropx = screenedgecheck(blackdropx, blackdropy);
			if(blackdropx > World.blackgoalx)
				if(fields[blackdropx - 1][blackdropy] == false)
					return LEFT;
//			blackdropx = screenedgecheck(blackdropx, blackdropy);
			if(blackdropx < World.blackgoalx)
				if(fields[blackdropx + 1][blackdropy] == false)
					return RIGHT;
			return direction;
		case 1://LEFT
//			blackdropy = screenedgecheck(blackdropx, blackdropy);
			if(blackdropy > World.blackgoaly)
				if(fields[blackdropx][blackdropy - 1] == false)
					return UP;
//			blackdropy = screenedgecheck(blackdropx, blackdropy);
			if(blackdropy < World.blackgoaly)
				if(fields[blackdropx][blackdropy + 1] == false)
					return DOWN;
			return direction;
		case 2://DOWN
//			blackdropx = screenedgecheck(blackdropx, blackdropy);
			if(blackdropx > World.blackgoalx)
				if(fields[blackdropx - 1][blackdropy] == false)
					return LEFT;
//			blackdropx = screenedgecheck(blackdropx, blackdropy);
			if(blackdropx < World.blackgoalx)
				if(fields[blackdropx + 1][blackdropy] == false)
					return RIGHT;
			return direction;
		case 3://RIGHT
//			blackdropy = screenedgecheck(blackdropx, blackdropy);			
			if(blackdropy > World.blackgoaly)
				if(fields[blackdropx][blackdropy - 1] == false)
					return UP;
//			blackdropy = screenedgecheck(blackdropx, blackdropy);
			if(blackdropy < World.blackgoaly)
				if(fields[blackdropx][blackdropy + 1] == false)
					return DOWN;
			return direction;
		default:
			break;			
		}
		return blackdirection;
	}
	
	public int screenedgecheck(int blackdropx, int blackdropy){
		if(blackdropx - 1 < 0 && blackdropx - 2 < 0)
			return blackdropx = 9;
		if(blackdropx + 1 > 9 && blackdropx + 2 > 9)
			return blackdropx = 0;
		if(blackdropy - 1 < 0 && blackdropy - 2 < 0)
			return blackdropy = 12;
		if(blackdropy + 1 > 12 && blackdropy + 2 > 12)
			return blackdropy = 0;
		
		return blackdropy;
	}
}
