package com.hackhalo2.ld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class AbstractScreen implements Screen {
	protected Stage uiStage;
	protected Table uiTable;
	protected OrthographicCamera camera;
	public final String name;

	protected AbstractScreen(String name) {
		this.name = name;
		this.uiStage = new Stage();
		this.uiTable = new Table();
		this.uiTable.setFillParent(true);
		this.uiStage.addActor(this.uiTable);
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		this.uiStage.act(delta);
		this.uiStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		this.camera.viewportWidth = 30f;
		this.camera.viewportHeight = 30f * height/width;
		this.camera.update();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		this.uiStage.dispose();

	}

}
