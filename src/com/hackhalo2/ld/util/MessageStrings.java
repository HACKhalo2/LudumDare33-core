package com.hackhalo2.ld.util;

import java.util.Random;

public class MessageStrings {

	private static String[] background; //random background chatter
	private static String[] talk;       //The messages for the little girl at the beginning
	private static String[] nice1;      //The messages when you are nice and accept
	private static String[] nice2;      //The messages when you are nice but deny
	private static String[] mean;       //The messages when you are mean
	private static String[] story;      //Help her (Odd = kid, Even = Guy)
	private static String[] mom;        //The Mom's conversation with the guy
	private static String[] gossip;     //Random Gossip
	private static Random rng;
	
	private MessageStrings() { }
	
	static {
		background = new String[] { "Did you pick up the kids from school?", "What did you say?", "Oh, alright.",
				"Man, I might have an issue with compulsive spending.", "When did a McSloppy cost five bucks?!",
				"I miss my shrubberies&", "did you hear about becky and robert?", "there are good monsters, and bad monsters&",
				"Hey, Big Zam!", "I'm sorry mario, but the princess is in another castle"
		};
		talk = new String[] { "*sniffle*", "Mommy? Daddy?", "Mommy, where did you go?", "Mommy! Daddy!", "Where am I at?", "I'm so scared&",
				"Sir?", "Sir, do you know where my Mommy and Daddy are at?", "Sir? Can you help me find them?", "Please sir, I'm really scared&",
				"Why aren't you helping me sir?", "Oh& alright& *sniffle*", "*sob*", "I miss my mommy and daddy&"
		};
		nice1 = new String[] { "Hrm?", "I'm sorry, I don't", "Alright, I guess I can do that." };
		nice2 = new String[] { "Hrm?", "I'm sorry, I don't", "I'm sorry& I can't&", "I'm really, really sorry, but I can't&",
				"&I wish I could&", "*sigh* I'm sorry&"};
		mean = new String[] { "Hrm?", "No.", "*sigh* No.", "Go. Away.", "Because I won't.", "hrumph&" };
		story = new String[] { "Thank you for helping me, sir.", "No problem. So where did you lose your mom at?", "um& that way!",
				"Alright, hold my hand and we'll walk together and find her, okay?", "*nods*", "So what do you like to do?",
				"I like to play with my dolls.", "oh?", "yea! I have lots!", "have any with you now?",
				"my mommy has my favorite in her purse right now", "So we really need to find her quickly then, huh?", "uh huh!",
				"Have any other hobbies?", "I like to color and paint&", "oh yea?", "yea, my daddy says I could be an artist when I grow up!",
				"*chuckle* I bet!"};
	}

}
