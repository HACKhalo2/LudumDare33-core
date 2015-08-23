package com.hackhalo2.ld.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.hackhalo2.ld.entity.component.Components;
import com.hackhalo2.ld.entity.component.GirlComponent;
import com.hackhalo2.ld.entity.component.Player;
import com.hackhalo2.ld.entity.component.Position;
import com.hackhalo2.ld.entity.component.TextBubble;
import com.hackhalo2.ld.util.MessageStrings;

public class GirlAISystem extends EntitySystem {
	private TextBubble bubble;
	private Entity theGirl;
	private Entity thePlayer;

	public GirlAISystem(TextBubble bubble) {
		super(1);
		this.bubble = bubble;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		this.theGirl = engine.getEntitiesFor(Family.all(GirlComponent.class).get()).first();
		this.thePlayer = engine.getEntitiesFor(Family.all(Player.class).get()).first();
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		this.theGirl = null;
		this.thePlayer = null;
	}
	
	@Override
	public void update(float delta) {
		Position girlPos = Components.POSITION.get(this.theGirl);
		Position playerPos = Components.POSITION.get(this.thePlayer);
		Player player = Components.PLAYER.get(this.thePlayer);
		GirlComponent girl = Components.GIRL.get(this.theGirl);
		girl.bounds.setPosition(girlPos.x, girlPos.y);
		
		if(player.respond && !this.bubble.actor.isDrawing()) {
			this.bubble.actor.startMessage().setMessageColor(Color.CYAN).append(MessageStrings.nice1[player.messageIndex]).endMessage();
			player.messageIndex++;
			player.respond = false;
			player.isResponding = true;
		} else if(player.isResponding && !this.bubble.actor.isDrawing()) {
			player.isResponding = false;
			girl.respond = true;
			System.out.println("Girl should respond");
			girl.bubblePos.x = 0;
			return;
		}
		
		if((girl.messageIndex < 6 || (girl.respond && girl.firstIncounter)) && !this.bubble.actor.isDrawing()) {
			if(girl.messageIndex > 8) {
				Components.RENDER.get(thePlayer).forceRender = true;
				this.getEngine().removeEntity(this.theGirl);
				this.getEngine().removeSystem(this);
				player.specialState = true;
				player.pauseInput = false;
				return;
			}
			this.bubble.actor.startMessage().setMessageColor(Color.PINK).append(MessageStrings.talk[girl.messageIndex]).endMessage();
			if(girl.bubblePos.x != -1) this.bubble.x = (int)girl.bubblePos.x;
			else this.bubble.x = (int)((this.bubble.actor.getWidth() + Gdx.graphics.getWidth()) - (this.bubble.actor.getWidth() * 2) - 32);
			System.out.println(this.bubble.x);
			if(girl.bubblePos.y != -1) this.bubble.y = (int)girl.bubblePos.y;
			else this.bubble.y = (int) Gdx.graphics.getHeight() - 32;
			girl.messageIndex++;
			
			if(girl.respond) {
				girl.isResponding = true;
				girl.respond = false;
			}
		} else if(girl.isResponding && !this.bubble.actor.isDrawing()) {
			girl.isResponding = false;
			player.respond = true;
			System.out.println("Player should respond");
			return;
		}
		
		if(!girl.firstIncounter && girl.bounds.overlaps(player.bounds)) {
			girl.firstIncounter = true;
			girl.messageIndex = 6;
			girl.bubblePos.x = (int)(((this.bubble.actor.getWidth() + Gdx.graphics.getWidth()) - (this.bubble.actor.getWidth() * 2)) - 32);
			girl.bubblePos.y = (int) Gdx.graphics.getHeight() / 2;
			girl.respond = true;
			player.pauseInput = true;
		}
	}

}
