package com.siegedog.eggs.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextHelper {

	public static void draw(SpriteBatch spriteBatch, String text, BitmapFont font,
			Color color, int x, int y, int width, HAlignment halign) {

		draw(spriteBatch, text, font, color, x, y, width, halign, 1.0f);
	}

	public static void draw(SpriteBatch spriteBatch, String text,
			BitmapFont font, Color color, int x, int y, int width,
			HAlignment halign, float alpha) {
		
		aux.set(0, 0, 0, color.a * alpha);
		font.setColor(aux);
		font.drawWrapped(spriteBatch, text, x + shadow, y - shadow, width, halign);

		// Useful when drawing with pre-defined colors
		font.setColor(color.r, color.g, color.b, color.a * alpha);
		font.drawWrapped(spriteBatch, text, x, y, width, halign);
	}

	public static void draw(SpriteBatch spriteBatch, String text,
			BitmapFont font, int x, int y) {

		font.setColor(Color.BLACK);
		font.draw(spriteBatch, text, x + shadow, y - shadow);

		font.setColor(Color.WHITE);
		font.draw(spriteBatch, text, x, y);
	}

	public static void setShadow(int i) {
		shadow = i;
	}

	private static Color aux = new Color();
	private static int shadow = 2;
}
