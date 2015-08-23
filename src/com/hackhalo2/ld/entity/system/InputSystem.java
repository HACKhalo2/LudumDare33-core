package com.hackhalo2.ld.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.hackhalo2.ld.Dare33;
import com.hackhalo2.ld.entity.component.Components;
import com.hackhalo2.ld.entity.component.Player;
import com.hackhalo2.ld.entity.component.Position;
import com.hackhalo2.ld.entity.component.Render;
import com.hackhalo2.ld.entity.component.SimpleAI;
import com.hackhalo2.ld.entity.component.TextBubble;
import com.hackhalo2.ld.util.MessageStrings;

public class InputSystem extends EntitySystem {
	private Entity thePlayer;
	private Entity theMom = null;
	private TextBubble bubble;
	private int index = 0;
	private boolean switchColor = false;

	public InputSystem(TextBubble bubble) {
		super(1);
		this.bubble = bubble;
	}

	@Override
	public void addedToEngine(Engine engine) {
		this.thePlayer = engine.getEntitiesFor(Family.all(Player.class).get()).first();
	}

	@Override
	public void removedFromEngine(Engine engine) {
		this.thePlayer = null;
	}

	@Override
	public void update(float delta) {
		Player player = Components.PLAYER.get(this.thePlayer);
		Position pos = Components.POSITION.get(this.thePlayer);
		Render ren = Components.RENDER.get(this.thePlayer);

		if(this.getEngine().getSystem(SimpleAISystem.class).momSpawned && !Dare33.gameOver) {
			pos = Components.POSITION.get(this.theMom);
			SimpleAI ai = Components.AI.get(this.theMom);

			if(ai.frames < 20) {
				ai.frames++;
				System.out.println(ai.frames);
				if(ai.isFacingLeft) pos.x -= ai.walkingSpeed;
				else pos.x += ai.walkingSpeed;

				ai.bounds.setPosition(pos.x, pos.y);
			}
			
			if(this.index < MessageStrings.mom.length && !bubble.actor.isDrawing()) {
				bubble.x = 0;
				bubble.y = 64;
				bubble.actor.startMessage().setMessageColor(Color.MAGENTA).append(MessageStrings.mom[this.index]).endMessage();
				this.index++;

				if(this.index == MessageStrings.mom.length) {
					Dare33.gameOver(false);
				}
			}
		}

		if(!this.getEngine().getSystem(RenderSystem.class).viewBounds.contains(player.bounds) && player.specialState) {
			Dare33.gameOver(true);
			player.specialState = false;
			player.pauseInput = true;
		}

		if(player.pauseInput) {
			if(!ren.isIdle) ren.isIdle = true;
			return;
		}

		if(player.specialState && this.index < MessageStrings.story.length && !bubble.actor.isDrawing()) {
			Color color = (this.switchColor ? Color.CYAN : Color.PINK);
			this.switchColor = !this.switchColor;
			bubble.x = 0;
			bubble.y = 64;
			bubble.actor.startMessage().setMessageColor(color).append(MessageStrings.story[this.index]).endMessage();
			this.index++;

			if(this.index == MessageStrings.story.length) {
				this.index = 0;
				player.pauseInput = true;
				boolean spawnLeft = false;
				if(pos.x >= 475) spawnLeft = true;
				this.theMom = this.getEngine().getSystem(SimpleAISystem.class).spawnMom(spawnLeft);
				ren.direction = (byte)(spawnLeft ? 2 : 3);
			}
		}

		boolean inAction = false;

		if(Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S)) { //Up
			if(pos.y >= 37) {
				pos.y = 37;
			} else {
				inAction = true;
				pos.y += 1;
			}

		}

		if(Gdx.input.isKeyPressed(Keys.S) && !Gdx.input.isKeyPressed(Keys.W)) { //Down
			if(pos.y <= 8) {
				pos.y = 8;
			} else {
				inAction = true;
				pos.y -= 1;
			}
		}

		if(Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)) { //Left
			inAction = true;

			if(player.specialState) ren.direction = 3;
			else ren.direction = 1;

			pos.x -= 1;

		}

		if(Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.A)) { //Right
			inAction = true;

			if(player.specialState) ren.direction = 2;
			else ren.direction = 0;

			pos.x += 1;
		}

		player.bounds.setPosition(pos.x, pos.y);

		if(inAction) {
			ren.isIdle = false;
		} else {
			ren.isIdle = true;
		}
	}

}
