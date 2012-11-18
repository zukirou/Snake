package com.zukirou.games.childofclouds;

import com.zukirou.gameFrameWork.Screen;
import com.zukirou.games.impl.AndroidGame;

public class ChildOfClouds extends AndroidGame{
	@Override
	public Screen getStartScreen(){
		return new LoadingScreen(this);
	}
}