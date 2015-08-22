package com.hackhalo2.ld.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class Position implements Component {
	public int x = 0;
	public int y = 0;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2 getPosition() {
		return new Vector2(x, y);
	}

}
