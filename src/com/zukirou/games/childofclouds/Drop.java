package com.zukirou.games.childofclouds;

import java.util.ArrayList;
import java.util.List;

public class Drop{
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	
	public List<BodyCloud> cloud = new ArrayList<BodyCloud>();
	static List<BodyCloud> cloudlength = new ArrayList<BodyCloud>();
	public int direction;
	
	public Drop(){
		direction = UP;
		cloud.add(new BodyCloud(5, 5));
		cloud.add(new BodyCloud(5, 6));
		cloud.add(new BodyCloud(5, 7));
	}
	
	public void turnLeft(){
		direction += 1;
		if(direction > RIGHT)
			direction = UP;
	}
	
	public void turnRight(){
		direction -= 1;
		if(direction < UP)
			direction = RIGHT;
	}
	
	public void eat(){
		BodyCloud end = cloud.get(cloud.size() - 1);
		cloud.add(new BodyCloud(end.x, end.y));
	}
	
	public void advance(){
		BodyCloud head = cloud.get(0);
		
		int len = cloud.size() - 1;
		cloudlength = cloud;
			
		for(int i = len; i > 0; i--){
			BodyCloud before = cloud.get(i - 1);
			BodyCloud cloud1 = cloud.get(i);
			cloud1.x = before.x;
			cloud1.y = before.y;
		}
		
		if(direction == UP)
			head.y -= 1;
		if(direction == LEFT)
			head.x -= 1;
		if(direction == DOWN)
			head.y += 1;
		if(direction == RIGHT)
			head.x += 1;
		if(head.x < 0)
			head.x = 9;
		if(head.x > 9)
			head.x = 0;
		if(head.y < 0)
			head.y = 12;
		if(head.y > 12)
			head.y = 0;
	}
		
	public boolean checkBitten(){
		int len = cloud.size();
		BodyCloud head = cloud.get(0);
		for (int i = 1; i < len; i++){
			BodyCloud cloud1 = cloud.get(i);
			if(cloud1.x == head.x && cloud1.y == head.y)
				return true;
		}
		
		if(MainMenuScreen.vsFlag == 1){
			int blacklen = BlackDrop.blackbodycloudlength.size();
			for(int i = 1; i < blacklen; i++){
				BlackBodyCloud blackbodycloud = BlackDrop.blackbodycloudlength.get(i);
				if(head.x == blackbodycloud.x && head.y == blackbodycloud.y)
					return true;
			}
		}
		return false;
	}
		
	
}