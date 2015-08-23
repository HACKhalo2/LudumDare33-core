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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.hackhalo2.ld.actors.GameOverActor;
import com.hackhalo2.ld.entity.component.Focus;
import com.hackhalo2.ld.entity.component.GirlComponent;
import com.hackhalo2.ld.entity.component.Player;
import com.hackhalo2.ld.entity.component.Position;
import com.hackhalo2.ld.entity.component.Render;
import com.hackhalo2.ld.entity.component.TextBubble;
import com.hackhalo2.ld.entity.system.GirlAISystem;
import com.hackhalo2.ld.entity.system.InputSystem;
import com.hackhalo2.ld.entity.system.RenderSystem;
import com.hackhalo2.ld.entity.system.SimpleAISystem;
import com.hackhalo2.ld.util.MessageStrings;

public class Dare33 extends ApplicationAdapter {
	private OrthographicCamera camera;
	private Engine entityEngine;
	private RenderSystem renderSystem;
	private OrthogonalTiledMapRenderer tiledRenderer;
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private Stage uiStage;
	private TextBubble bubble;
	private GameOverActor actor;
	public static boolean gameOver = false;
	static boolean alt = false;
	private Music background;
	private int index = 0;
	
	@Override
	public void create () {
		this.uiStage = new Stage();
		
		this.actor = new GameOverActor();
		this.uiStage.addActor(this.actor);
		
		this.background = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
		this.background.setLooping(true);
		
		this.mapLoader = new TmxMapLoader();
		this.map = this.mapLoader.load("test.tmx");
		this.tiledRenderer = new OrthogonalTiledMapRenderer(this.map, 0.75f);
		
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.zoom = 4f;
		
		this.entityEngine = new Engine();
		Entity entity = new Entity();
		
		bubble = new TextBubble(this.uiStage);
		
		Texture tex = new Texture("Guy.png");
		TextureRegion region = new TextureRegion(tex, 16, 32);
		entity.add(new Position(16, 32));
		entity.add(new Render(region, 1/10f));
		entity.add(new Focus(this.camera));
		entity.add(new Player());
		this.entityEngine.addEntity(entity);
		
		entity = new Entity();
		tex = new Texture("Crying_Girl.png");
		region = new TextureRegion(tex, 16, 32);
		entity.add(new Position(475, 32));
		Render ren = new Render(region, 1/20f);
		ren.direction = 3;
		ren.isIdle = false;
		entity.add(ren);
		entity.add(new GirlComponent());
		this.entityEngine.addEntity(entity);
		
		this.renderSystem = new RenderSystem();
		this.entityEngine.addSystem(this.renderSystem);
		this.entityEngine.addSystem(new InputSystem(new TextBubble(this.uiStage)));
		this.entityEngine.addSystem(new SimpleAISystem(new TextBubble(this.uiStage)));
		this.entityEngine.addSystem(new GirlAISystem(new TextBubble(this.uiStage)));
		
		this.background.play();
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
		
		if(gameOver) {
			System.out.println("Here");
			this.actor.scrollup = true;
			this.actor.taken = alt;
			this.entityEngine.getSystem(SimpleAISystem.class).canSpawn = false;
			gameOver = false;
		}
		
		if(this.index < MessageStrings.credits.length && !bubble.actor.isDrawing()) {
			bubble.x = 0;
			bubble.y = 0;
			bubble.actor.startMessage().append(MessageStrings.credits[this.index]).endMessage();
			this.index++;
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.tiledRenderer.setView(this.camera);
		this.tiledRenderer.render(new int[]{0, 1, 2});
		
		this.entityEngine.update(delta);
		
		this.tiledRenderer.render(new int[]{3, 4});
		
		this.uiStage.act(delta);
		this.uiStage.draw();
	}
	
	@Override
	public void dispose() {
		this.renderSystem.dispose();
		this.background.stop();
		this.background.dispose();
		this.uiStage.draw();
	}
	
	public static void gameOver(boolean flag) {
		gameOver = true;
		alt = flag;
	}
}
