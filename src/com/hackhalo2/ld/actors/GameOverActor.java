package com.hackhalo2.ld.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameOverActor extends Actor {
	private Texture gameOver;
	public boolean scrollup = false;
	private Vector2 position;

	public GameOverActor() {
		this.gameOver = new Texture("GameOver.png");
		this.position = new Vector2((Gdx.graphics.getWidth() / 2) - (this.gameOver.getWidth() / 2),
				-(this.gameOver.getHeight()));
		
	}
	
	@Override
	public void act(float delta) {
		if(this.scrollup) {
			if(((this.position.y + this.gameOver.getHeight()) - this.gameOver.getHeight()) <= 20) this.position.y += 20;
			
			if(this.position.y + this.gameOver.getHeight() >= this.gameOver.getHeight())
				this.scrollup = false;
		}
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(this.gameOver, this.position.x, this.position.y);
	}

}
