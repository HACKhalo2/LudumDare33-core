package com.hackhalo2.ld.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class SimpleAI implements Component {
	
	public final Rectangle bounds;
	public boolean isFacingLeft = false;
	public int walkingSpeed = 1;
	public int frames = 0;
	public boolean special = false;

	public SimpleAI() {
		this.bounds = new Rectangle();
	}

}
