package com.zukirou.games.childofclouds;

import com.zukirou.gameFrameWork.Game;
import com.zukirou.gameFrameWork.Graphics;
import com.zukirou.gameFrameWork.Screen;
import com.zukirou.gameFrameWork.Graphics.PixmapFormat;

public class LoadingScreen extends Screen{
	public LoadingScreen(Game game){
		super(game);
	}
	
	@Override
	public void update(float deltaTime){
		Graphics g = game.getGraphics();
		Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
		Assets.logo	=	g.newPixmap("logo.png", PixmapFormat.ARGB4444);
		Assets.mainmenu =	g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
		Assets.buttons	=	g.newPixmap("buttons.png", PixmapFormat.ARGB4444);
		Assets.help1	=	g.newPixmap("help1.png", PixmapFormat.ARGB4444);
		Assets.help2	=	g.newPixmap("help2.png", PixmapFormat.ARGB4444);
		Assets.help3	=	g.newPixmap("help3.png", PixmapFormat.ARGB4444);
		Assets.numbers	=	g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
		Assets.ready	=	g.newPixmap("ready.png", PixmapFormat.ARGB4444);
		Assets.pause	=	g.newPixmap("pause.png", PixmapFormat.ARGB4444);
		Assets.gameover	=	g.newPixmap("gameover.png", PixmapFormat.ARGB4444);
		Assets.headup	=	g.newPixmap("headup.png", PixmapFormat.ARGB4444);
		Assets.headleft	=	g.newPixmap("headleft.png", PixmapFormat.ARGB4444);
		Assets.headdown	=	g.newPixmap("headdown.png", PixmapFormat.ARGB4444);
		Assets.headright=	g.newPixmap("headright.png", PixmapFormat.ARGB4444);
		Assets.bodycloud=	g.newPixmap("bodycloud.png", PixmapFormat.ARGB4444);
		Assets.dust1	=	g.newPixmap("dust1.png", PixmapFormat.ARGB4444);
		Assets.dust2	=	g.newPixmap("dust2.png", PixmapFormat.ARGB4444);
		Assets.dust3	=	g.newPixmap("dust3.png", PixmapFormat.ARGB4444);
		Assets.click	=	game.getAudio().newSound("click.ogg");
		Assets.eat		=	game.getAudio().newSound("eat.ogg");
		Assets.bitten	=	game.getAudio().newSound("bitten.ogg");

		Assets.highscoreupdate	=	g.newPixmap("highscoreupdate.png", PixmapFormat.ARGB4444);
		Assets.blackcloud	=	g.newPixmap("blackcloud.png", PixmapFormat.ARGB4444);
		Assets.blackheadup	=	g.newPixmap("blackheadup.png", PixmapFormat.ARGB4444);
		Assets.blackheadleft	=	g.newPixmap("blackheadleft.png", PixmapFormat.ARGB4444);
		Assets.blackheadright	=	g.newPixmap("blackheadright.png", PixmapFormat.ARGB4444);
		Assets.blackheaddown	=	g.newPixmap("blackheaddown.png", PixmapFormat.ARGB4444);
		Assets.headupng	=	g.newPixmap("headupng.png", PixmapFormat.ARGB4444);
		Assets.headleftng	=	g.newPixmap("headleftng.png", PixmapFormat.ARGB4444);
		Assets.headdownng	=	g.newPixmap("headdownng.png", PixmapFormat.ARGB4444);
		Assets.headrightng=	g.newPixmap("headrightng.png", PixmapFormat.ARGB4444);
		Assets.help4	=	g.newPixmap("help4.png", PixmapFormat.ARGB4444);
		
		Settings.load(game.getFileIO());
		game.setScreen(new MainMenuScreen(game));
	}
	
	@Override
	public void present(float deltaTime){
		
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