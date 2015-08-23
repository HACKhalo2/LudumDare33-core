package com.hackhalo2.ld.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.hackhalo2.ld.entity.component.TextBubble;
import com.hackhalo2.ld.util.CharToRegion;

public class TextBubbleActor extends Actor {
	private TextureRegion[][] regions;
	private Texture alphabit = new Texture("Alphabit.png");
	private TextBubble component;
	private TextureRegion[] message;
	private Array<TextureRegion> builder;
	private boolean building = false;
	
	private boolean isDrawing = false;
	private boolean shouldFade = false;
	private boolean fadeIn = true;
	private float messageAlpha = 0f;
	private float elapsedTime = 0f;
	private Color messageColor = Color.WHITE;

	public TextBubbleActor(TextBubble component) {
		this.regions = TextureRegion.split(this.alphabit, 16, 16);
		this.component = component;
	}
	
	@Override
	public void act(float delta) {
		if(this.message != null) {
			if(this.elapsedTime != 0f) this.elapsedTime += delta;
			
			if(this.shouldFade) {
				if(this.elapsedTime == 0f) this.elapsedTime += delta;
				
				if(this.fadeIn) {
					this.messageAlpha += delta;
					 if(this.messageAlpha > 1f) {
						 this.messageAlpha = 1f;
						 this.shouldFade = false;
						 this.fadeIn = false;
					 }
				} else {
					this.messageAlpha -= delta;
					if(this.messageAlpha < 0f) {
						this.messageAlpha = 0f;
						this.shouldFade = false;
						this.fadeIn = true;
						this.message = null;
					}
				}
			}
			
			if(!this.shouldFade && this.elapsedTime >= 5f) this.shouldFade = true;
		}
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		if(this.message != null) {
			if(!this.shouldFade && this.elapsedTime == 0f) { //start the fade in
				this.shouldFade = true;
				this.isDrawing = true;
				return;
			}
			
			int offset = 0;
			Color batchColor = batch.getColor();
			batch.setColor(this.messageColor.r, this.messageColor.g, this.messageColor.b, this.messageAlpha);
			
			for(TextureRegion region : this.message) {
				batch.draw(region, component.x + offset, component.y);
				offset += 16;
			}
			
			batch.setColor(batchColor);
		} else if(this.isDrawing) this.isDrawing = false;
	}
	
	public boolean isDrawing() {
		return this.isDrawing;
	}
	
	public TextBubbleActor startMessage() {
		this.building = true;
		this.builder = new Array<>(16);
		
		return this;
	}
	
	public TextBubbleActor setMessageColor(Color color) {
		if(this.building && !this.isDrawing) {
			this.messageColor = color;
		}
		
		return this;
	}
	
	public void endMessage() {
		if(this.building  && !this.isDrawing) {
			this.building = false;
			if(this.builder.size > 0) {
				this.message = this.builder.toArray(TextureRegion.class);
			}
		}
	}
	
	public TextBubbleActor append(String message) {
		if(this.building  && !this.isDrawing) {
			char[] chars = message.toLowerCase().toCharArray();
			Vector2 temp;
			TextureRegion region;
			
			for(char character : chars) {
				temp = CharToRegion.get(character);
				region = this.regions[(int)temp.x][(int)temp.y];
				this.builder.add(region);
			}
		}
		
		return this;
	}
	
	public TextBubbleActor appendElipses() {
		if(this.building  && !this.isDrawing) {
			this.builder.add(this.regions[3][7]);
		}
		
		return this;
	}

}
