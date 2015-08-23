package com.hackhalo2.ld.util;

import com.badlogic.gdx.math.Vector2;

public class CharToRegion {

	private CharToRegion() { }
	
	public static Vector2 get(char character) {
		Vector2 temp = new Vector2();
		
		switch(character) {
		case 'a':
			temp.set(0, 0);
			break;
		case 'b':
			temp.set(0, 1);
			break;
		case 'c':
			temp.set(0, 2);
			break;
		case 'd':
			temp.set(0, 3);
			break;
		case 'e':
			temp.set(0, 4);
			break;
		case 'f':
			temp.set(0, 5);
			break;
		case 'g':
			temp.set(0, 6);
			break;
		case 'h':
			temp.set(0, 7);
			break;
		case 'i':
			temp.set(1, 0);
			break;
		case 'j':
			temp.set(1, 1);
			break;
		case 'k':
			temp.set(1, 2);
			break;
		case 'l':
			temp.set(1, 3);
			break;
		case 'm':
			temp.set(1, 4);
			break;
		case 'n':
			temp.set(1, 5);
			break;
		case 'o':
			temp.set(1, 6);
			break;
		case 'p':
			temp.set(1, 7);
			break;
		case 'q':
			temp.set(2, 0);
			break;
		case 'r':
			temp.set(2, 1);
			break;
		case 's':
			temp.set(2, 2);
			break;
		case 't':
			temp.set(2, 3);
			break;
		case 'u':
			temp.set(2, 4);
			break;
		case 'v':
			temp.set(2, 5);
			break;
		case 'w':
			temp.set(2, 6);
			break;
		case 'x':
			temp.set(2, 7);
			break;
		case 'y':
			temp.set(3, 0);
			break;
		case 'z':
			temp.set(3, 1);
			break;
		case '.':
			temp.set(3, 2);
			break;
		case '!':
			temp.set(3, 3);
			break;
		case '?':
			temp.set(3, 4);
			break;
		case ',':
			temp.set(3, 5);
			break;
		case '*':
			temp.set(3, 6);
			break;
		case '&':
			temp.set(3, 7);
			break;
		case '\'':
			temp.set(4, 0);
			break;
		case ' ':
		default:
			temp.set(5, 0);
			break;
		}
		
		return temp;
	}

}
