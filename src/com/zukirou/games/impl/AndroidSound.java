package com.zukirou.games.impl;

import android.media.SoundPool;

import com.zukirou.gameFrameWork.Sound;

public class AndroidSound implements Sound{
	int soundId;
	SoundPool soundPool;
	
	public AndroidSound(SoundPool soundPool,int soundId){
		this.soundId = soundId;
		this.soundPool = soundPool;
	}
	
	@Override
	public void play(float volume){
		soundPool.play(soundId, volume, volume, 0, 0, 1);
	}
	
	@Override
	public void dispose(){
		soundPool.unload(soundId);
		soundPool.release();//Unable to load sample:(null) ���o���̂Œǉ����Ă݂��B���ʃA�����킩���B
	}
}