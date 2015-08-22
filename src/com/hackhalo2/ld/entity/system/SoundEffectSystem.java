package com.hackhalo2.ld.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.hackhalo2.ld.entity.component.Components;
import com.hackhalo2.ld.entity.component.Player;
import com.hackhalo2.ld.entity.component.Position;
import com.hackhalo2.ld.entity.component.Render;

public class SoundEffectSystem extends EntitySystem implements Disposable {
	private ImmutableArray<Entity> entities;
	private Entity thePlayer;
	private Sound footstep;

	public SoundEffectSystem() {
		super(2);
		this.footstep = Gdx.audio.newSound(Gdx.files.internal("footstep2.mp3"));
	}

	@Override
	public void addedToEngine(Engine engine) {
		this.entities = engine.getEntitiesFor(Family.all(Render.class, Position.class).get());
		this.thePlayer = engine.getEntitiesFor(Family.all(Player.class).get()).first();
	}

	@Override
	public void removedFromEngine(Engine engine) {
		this.entities = null;
		this.footstep.stop();
	}

	@Override
	public void update(float delta) {
		Position playerPos = Components.POSITION.get(this.thePlayer);
		Position pos;
		Render ren;
		float volume = -1f;

		for(Entity entity : this.entities) {
			ren = Components.RENDER.get(entity);
			if((!ren.isIdle && ren.frame == 0) || ren.frame == 4) {
				if(entity.getId() == this.thePlayer.getId()) volume = 0.05f;
				else {
					pos = Components.POSITION.get(entity);
					volume = (500f * playerPos.getPosition().dst(pos.getPosition())) / 1000f;
				}
				//this.footstep.play(volume, 1.5f, 0.5f);
			}
		}

	}

	@Override
	public void dispose() {
		this.footstep.dispose();

	}

}
