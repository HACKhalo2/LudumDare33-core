package com.hackhalo2.ld.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Focus implements Component {
	public final OrthographicCamera camera;

	public Focus(OrthographicCamera camera) {
		this.camera = camera;
	}

}
