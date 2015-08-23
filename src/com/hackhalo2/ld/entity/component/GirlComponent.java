package com.hackhalo2.ld.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GirlComponent implements Component {
	public int messageIndex = 0;
	public final Rectangle bounds;
	public boolean firstIncounter = false;
	public boolean respond = false;
	public boolean isResponding = false;
	public Vector2 bubblePos;

	public GirlComponent() {
		this.bounds = new Rectangle(0,0,16,32);
		this.bubblePos = new Vector2(-1, -1);
	}

}
