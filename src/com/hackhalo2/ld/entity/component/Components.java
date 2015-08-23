package com.hackhalo2.ld.entity.component;

import com.badlogic.ashley.core.ComponentMapper;

public class Components {
	public static ComponentMapper<Render> RENDER = ComponentMapper.getFor(Render.class);
	public static ComponentMapper<Position> POSITION = ComponentMapper.getFor(Position.class);
	public static ComponentMapper<Focus> FOCUS = ComponentMapper.getFor(Focus.class);
	public static ComponentMapper<Player> PLAYER = ComponentMapper.getFor(Player.class);
	public static ComponentMapper<SimpleAI> AI = ComponentMapper.getFor(SimpleAI.class);
	public static ComponentMapper<TextBubble> TEXT_BUBBLE = ComponentMapper.getFor(TextBubble.class);
	
	private Components() { }

}
