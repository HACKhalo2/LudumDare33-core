package com.hackhalo2.ld;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.hackhalo2.ld.entity.component.Focus;
import com.hackhalo2.ld.entity.component.Player;
import com.hackhalo2.ld.entity.component.Position;
import com.hackhalo2.ld.entity.component.Render;
import com.hackhalo2.ld.entity.system.InputSystem;
import com.hackhalo2.ld.entity.system.RenderSystem;
import com.hackhalo2.ld.entity.system.SoundEffectSystem;

public class Dare33 extends ApplicationAdapter {
	private OrthographicCamera camera;
	private Engine entityEngine;
	private RenderSystem renderSystem;
	private OrthogonalTiledMapRenderer tiledRenderer;
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private Music music;
	
	@Override
	public void create () {
		this.music = Gdx.audio.newMusic(Gdx.files.internal("STE-008.mp3"));
		this.music.setLooping(true);
		this.music.setVolume(0.5f);
		
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.zoom = 4f;
		
		this.entityEngine = new Engine();
		Entity entity = new Entity();
		
		Texture tex = new Texture("Guy.png");
		TextureRegion region = new TextureRegion(tex, 16, 32);
		entity.add(new Position(0, 32));
		entity.add(new Render(region, 1/10f));
		entity.add(new Focus(this.camera));
		entity.add(new Player());
		this.entityEngine.addEntity(entity);
		
		entity = new Entity();
		tex = new Texture("Crying_Girl.png");
		region = new TextureRegion(tex, 16, 32);
		entity.add(new Position(32, 32));
		entity.add(new Render(region, 1/20f));
		this.entityEngine.addEntity(entity);
		
		this.renderSystem = new RenderSystem();
		this.entityEngine.addSystem(this.renderSystem);
		this.entityEngine.addSystem(new InputSystem());
		this.entityEngine.addSystem(new SoundEffectSystem());
		
		this.mapLoader = new TmxMapLoader();
		this.map = this.mapLoader.load("test.tmx");
		this.tiledRenderer = new OrthogonalTiledMapRenderer(this.map, 0.75f);
		this.music.play();
	}
	
	@Override
	public void resize(int width, int height) {
		this.camera.viewportWidth = 30f;
		this.camera.viewportHeight = 30f * height/width;
		this.camera.update();
	}

	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.tiledRenderer.setView(this.camera);
		this.tiledRenderer.render(new int[]{0, 1});
		
		this.entityEngine.update(delta);
		
		this.tiledRenderer.render(new int[]{2, 3});
	}
	
	@Override
	public void dispose() {
		this.renderSystem.dispose();
		this.music.stop();
		this.music.dispose();
	}
}
