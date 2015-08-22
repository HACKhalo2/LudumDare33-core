package com.hackhalo2.ld.entity.system;

import java.util.Comparator;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.hackhalo2.ld.entity.component.Components;
import com.hackhalo2.ld.entity.component.Focus;
import com.hackhalo2.ld.entity.component.Position;
import com.hackhalo2.ld.entity.component.Render;

public class RenderSystem extends EntitySystem implements Comparator<Entity>, EntityListener, Disposable {
	private Family family;
	private SpriteBatch batch;
	private Array<Entity> entities;
	private boolean shouldSort = true;
	private Rectangle viewBounds;

	public RenderSystem() {
		super(0);
		this.family = Family.all(Render.class, Position.class).get();
		this.entities = new Array<>(false, 16);
		this.batch = new SpriteBatch();
		this.viewBounds = new Rectangle();
	}
	
	@Override
	public void update(float delta) {
		if(this.shouldSort) this.sort();
		
		this.batch.begin();
		for(Entity entity : this.entities) {
			Render ren = Components.RENDER.get(entity);
			Position pos = Components.POSITION.get(entity);
			
			if(Components.FOCUS.has(entity)) {
				Focus focus = Components.FOCUS.get(entity);
				focus.camera.position.set(pos.x + 8, pos.y + 16, 0);
				focus.camera.update();
				this.setView(focus.camera);
			}
			
			if(!ren.isIdle) {
				ren.elapsedTime += delta;
				if(ren.elapsedTime > ren.frameTime) {
					while(ren.elapsedTime >= ren.frameTime) {
						if(ren.frame == 7) ren.frame = 0;
						else ren.frame++;
						
						ren.elapsedTime -= ren.frameTime;
					}
				}
				
				ren.region.setRegion(ren.frame * 16, ren.direction * 32, 16, 32);
			} else {
				if(ren.elapsedTime > 0f) ren.elapsedTime = 0f;
				if(ren.frame > 0) ren.frame = 0;
				
				ren.region.setRegion(0, ren.direction * 32, 16, 32);
			}
			this.batch.draw(ren.region, pos.x, pos.y);
		}
		this.batch.end();
	}
	
	private void setView(OrthographicCamera camera) {
		this.batch.setProjectionMatrix(camera.combined);
		float width = (camera.viewportWidth * camera.zoom) + 128;
		float height = (camera.viewportHeight * camera.zoom) + 128;
		this.viewBounds.set(camera.position.x - width / 2, camera.position.y - height / 2, width, height);
	}
	
	public boolean shouldSort() {
		return this.shouldSort;
	}
	
	public RenderSystem sort(boolean flag) {
		this.shouldSort = flag;
		
		return this;
	}
	
	public void forceSort() {
		this.shouldSort = true;
	}
	
	private void sort() {
		this.shouldSort = false;
		this.entities.sort(this);
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		ImmutableArray<Entity> temp = engine.getEntitiesFor(this.family);
		
		for(Entity entity : temp) {
			this.entities.add(entity);
		}
		
		this.entities.shrink();
		this.sort();
		
		engine.addEntityListener(this.family, this);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		this.entities.clear();
		this.shouldSort = false;
	}
	
	@Override
	public void entityAdded(Entity entity) {
		this.entities.add(entity);
		this.sort();
	}

	@Override
	public void entityRemoved(Entity entity) {
		if(this.entities.contains(entity, true)) {
			this.entities.removeValue(entity, true);
			this.sort();
		}
	}

	@Override
	public int compare(Entity left, Entity right) {
		Position posL = Components.POSITION.get(left);
		Position posR = Components.POSITION.get(right);
		
		return posL.y = posR.y;
	}

	@Override
	public void dispose() {
		this.batch.dispose();
	}
	
	

}
