package com.hackhalo2.ld.util;

import com.badlogic.gdx.math.MathUtils;

public class MessageStrings {

	public static String[] credits;
	public static String[] background; //random background chatter
	public static String[] talk;       //The messages for the little girl at the beginning
	public static String[] nice1;      //The messages when you are nice and accept
	public static String[] story;      //Help her (Odd = kid, Even = Guy)
	public static String[] mom;        //The Mom's conversation with the guy
	
	private MessageStrings() { }
	
	public static String getRandomBackground() {
		return background[MathUtils.random(0, (background.length-1))];
	}
	
	static {
		credits = new String[] { "Developed by Jacob Litewski", "For Ludum Dare Thirty Three"};
		background = new String[] { "Did you pick up the kids from school?", "What did you say?", "Oh, alright.",
				"Man, I might have an issue with compulsive spending.", "When did a McSloppy cost five bucks?!",
				"I miss my shrubberies&", "did you hear about becky and robert?", "there are good monsters, and bad monsters&",
				"Hey, Big Zam!", "I'm sorry mario, but the princess is in another castle"
		};
		talk = new String[] { "*sniffle*", "Mommy? Daddy?", "Mommy, where did you go?", "Mommy! Daddy!", "Where am I at?", "I'm so scared&",
				"Sir?", "Do you know where my Parents are?", "Can you help me find them?"
		};
		nice1 = new String[] { "Hrm?", "I'm sorry, I don't", "Alright, I guess I can do that." };
		story = new String[] { "Thank you for helping me, sir.", "No problem. So where did you lose your mom at?", "um& that way!",
				"Alright, hold my hand and we'll walk together and find her, okay?", "*nods*", "So what do you like to do?",
				"I like to play with my dolls.", "oh?", "yea! I have lots!", "have any with you now?",
				"my mommy has my favorite in her purse right now", "So we really need to find her quickly then, huh?", "uh huh!",
				"Have any other hobbies?", "I like to color and paint&", "oh yea?", "yea, my daddy says I could be an artist when I grow up!",
				"*chuckle* I bet!"};
		mom = new String[] { "SAMATHA! THere you are!", "Get away from that man!", "what were you doing with him?", "You! You were trying to take my little girl!"
				
		};
	}

}
