package com.hackhalo2.ld.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.hackhalo2.ld.actors.TextBubbleActor;

public class TextBubble implements Component {
	public int x;
	public int y;
	public TextBubbleActor actor;

	public TextBubble(Stage stage) {
		this.actor = new TextBubbleActor(this);
		stage.addActor(this.actor);
	}

}
