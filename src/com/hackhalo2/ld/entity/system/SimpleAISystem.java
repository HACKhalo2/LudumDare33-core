package com.hackhalo2.ld.entity.system;

import java.util.Random;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.hackhalo2.ld.entity.component.Components;
import com.hackhalo2.ld.entity.component.Position;
import com.hackhalo2.ld.entity.component.Render;
import com.hackhalo2.ld.entity.component.SimpleAI;
import com.hackhalo2.ld.entity.component.TextBubble;

public class SimpleAISystem extends EntitySystem implements EntityListener {
	private Family family;
	private Array<Entity> entities;
	private Random rng;
	private Rectangle viewBounds = new Rectangle();
	private TextBubble bubble;
	public boolean canSpawn = true;

	public SimpleAISystem(TextBubble bubble) {
		super(1);
		this.bubble = bubble;
		this.rng = new Random();
		this.family = Family.all(SimpleAI.class).get();
		this.entities = new Array<>();
		this.entities.shrink();
		
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		engine.addEntityListener(this.family, this);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		engine.removeEntityListener(this);
		this.entities.clear();
	}
	
	@Override
	public void entityAdded(Entity entity) {
		this.entities.add(entity);
		
	}

	@Override
	public void entityRemoved(Entity entity) {
		this.entities.removeValue(entity, true);
		this.entities.shrink();
		
	}
	
	@Override
	public void update(float delta) {
		this.viewBounds.set(this.getEngine().getSystem(RenderSystem.class).viewBounds);
		
		Position pos;
		SimpleAI ai;
		
		for(Entity entity : this.entities) {
			pos = Components.POSITION.get(entity);
			ai = Components.AI.get(entity);
			
			if(ai.isFacingLeft) pos.x -= ai.walkingSpeed;
			else pos.x += ai.walkingSpeed;
			
			ai.bounds.setPosition(pos.x, pos.y);
			
			if(!ai.bounds.overlaps(this.viewBounds)) this.getEngine().removeEntity(entity);
		}
		
		if(this.entities.size < 5) this.spawn();
	}
	
	private void spawn() {
		if(!this.canSpawn) return;
		
		Entity entity = new Entity();
		SimpleAI ai = new SimpleAI();
		
		Texture tex = new Texture("Random_"+MathUtils.random(0, 7)+".png");
		TextureRegion region = new TextureRegion(tex, 16, 32);
		Render ren = new Render(region, 1/10f);
		ren.direction = (byte)this.rng.nextInt(4);
		ren.frame = MathUtils.random(0, 7);
		ai.isFacingLeft = (ren.direction == 1 || ren.direction == 3);
		ren.isIdle = false;
		entity.add(ren);
		
		Position pos = new Position(0,0);
		pos.y = ((int)this.rng.nextInt((37 - 8) + 1) + 8);
		pos.x = (int) (ai.isFacingLeft ? (this.viewBounds.x + this.viewBounds.width) : this.viewBounds.x);
		
		entity.add(pos);
		ai.walkingSpeed = this.rng.nextInt((2 - 1) + 1) + 1;
		entity.add(ai);
		
		this.getEngine().addEntity(entity);
	}
	
	public void spawnMom() {
		Entity entity = new Entity();
	}

}
