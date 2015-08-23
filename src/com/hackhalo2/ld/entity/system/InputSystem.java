package com.hackhalo2.ld.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.hackhalo2.ld.entity.component.Components;
import com.hackhalo2.ld.entity.component.Player;
import com.hackhalo2.ld.entity.component.Position;
import com.hackhalo2.ld.entity.component.Render;

public class InputSystem extends EntitySystem {
	private Entity thePlayer;

	public InputSystem() {
		super(1);
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
		if(player.pauseInput) {
			if(!ren.isIdle) ren.isIdle = true;
			return;
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
