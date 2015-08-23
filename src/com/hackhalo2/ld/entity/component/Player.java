package com.hackhalo2.ld.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class Player implements Component {
	public boolean specialState = false;
	public boolean hudActive = false;
	public boolean respond = false;
	public boolean wasMean = false;
	public boolean wasNice = false;
	public int messageIndex = 0;
	public final Rectangle bounds;
	public boolean gameOver = false;

	public Player() {
		this.bounds = new Rectangle(0,0,16,32);
	}

}
