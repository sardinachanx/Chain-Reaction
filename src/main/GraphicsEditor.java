package main;

import org.newdawn.slick.Graphics;

public class GraphicsEditor{

	public static int getCenterX(String s, int x, Graphics g){
		return x - g.getFont().getWidth(s) / 2;
	}

	public static int getCenterY(String s, int y, Graphics g){
		return y - g.getFont().getHeight(s) / 2;
	}

	public static float getCenterX(String s, float x, Graphics g){
		return x - g.getFont().getWidth(s) / 2;
	}

	public static float getCenterY(String s, float y, Graphics g){
		return y - g.getFont().getHeight(s) / 2;
	}

}
