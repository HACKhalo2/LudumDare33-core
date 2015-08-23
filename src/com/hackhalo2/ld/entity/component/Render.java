package com.hackhalo2.ld.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Render implements Component {
	public final TextureRegion region;
	public final float frameTime;
	public int frame = 0; //between 0 and 7 (for frame 1-8)
	public float elapsedTime = 0f;
	public boolean isIdle = true;
	public byte direction = 0; //0 for right, 1 for left, 2 for special right, 3 for special left
	public boolean forceRender = false;

	public Render(TextureRegion region, float frameTime) {
		this.region = region;
		this.frameTime = frameTime;
	}

}
