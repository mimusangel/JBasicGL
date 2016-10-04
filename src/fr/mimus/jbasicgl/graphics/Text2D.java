package fr.mimus.jbasicgl.graphics;

import org.lwjgl.opengl.GL11;

import fr.mimus.jbasicgl.maths.Mat4;
import fr.mimus.jbasicgl.maths.Vec2;

public class Text2D
{
	public static Texture font;
	private static String fontArray = "0123456789"
									+ "abcdefghij"
									+ "klmnopqrst"
									+ "uvwxyz    "
									+ "ABCDEFGHIJ"
									+ "KLMNOPQRST"
									+ "UVWXYZ    "
									+ ".:/\\?!,;[]"
									+ "=+-_><(){}"
									+ "'\"@*      ";
	private static float[] scaleArray = new float[] {
									0.73f, 0.47f, 0.73f, 0.73f, 0.80f, 0.73f, 0.73f, 0.73f, 0.73f, 0.73f,
									0.73f, 0.73f, 0.66f, 0.73f, 0.73f, 0.53f, 0.73f, 0.66f, 0.26f, 0.40f,
									0.66f, 0.26f, 1.00f, 0.66f, 0.73f, 0.73f, 0.73f, 0.46f, 0.73f, 0.46f,
									0.66f, 0.73f, 1.00f, 0.73f, 0.73f, 0.73f, 1.00f, 1.00f, 1.00f, 1.00f,
									1.00f, 0.80f, 1.00f, 0.86f, 0.80f, 0.73f, 1.00f, 0.86f, 0.26f, 0.60f,
									0.86f, 0.66f, 0.87f, 0.86f, 1.00f, 0.80f, 1.00f, 0.86f, 0.86f, 0.80f,
									0.86f, 1.00f, 1.00f, 1.00f, 0.87f, 0.86f, 1.00f, 1.00f, 1.00f, 1.00f,
									0.26f, 0.26f, 0.53f, 0.53f, 0.73f, 0.26f, 0.26f, 0.26f, 0.40f, 0.40f,
									0.73f, 0.80f, 0.46f, 0.86f, 0.73f, 0.73f, 0.46f, 0.46f, 0.53f, 0.53f,
									0.26f, 0.46f, 1.00f, 0.46f, 1.00f, 1.00f, 1.00f, 1.00f, 1.00f, 1.00f };
	private static Mesh fontMesh;
	private static float fontScale = 1.0f;
	public static void load(Texture bitmap)
	{
		font = bitmap;
		float w = font.getWidth() / 10f; 
		float h = font.getHeight() / 10f;
		float tw = w / (float)font.getWidth(); 
		float th = h / (float)font.getHeight();
		fontMesh = new Mesh(4);
		fontMesh.addVertices(0, 0).addTexCoord2f(0f, 0f).addColor(Color4f.WHITE);
		fontMesh.addVertices(w, 0).addTexCoord2f(tw, 0f).addColor(Color4f.WHITE);
		fontMesh.addVertices(w, h).addTexCoord2f(tw, th).addColor(Color4f.WHITE);
		fontMesh.addVertices(0, h).addTexCoord2f(0f, th).addColor(Color4f.WHITE);
		fontMesh.buffering();
	}
	
	private static float getOffset(char c)
	{
		float w = 10f;
		if (font != null)
			w = font.getWidth() / 10f;
		return (w * scaleArray[fontArray.indexOf(c)]);
	}
	
	public static void drawString(Shaders shader, String txt, Vec2 pos, Color4f color)
	{
		if (font == null)
			load(Texture.FileTexture(Text2D.class.getResourceAsStream("font.png"), true));
		font.bind();
		float w = font.getWidth() / 10f; 
		float h = font.getHeight() / 10f;
		float tw = w / (float)font.getWidth(); 
		float th = h / (float)font.getHeight();
		shader.setUniformMat4f(Shaders.MODEL_NAME, Mat4.scale(fontScale));
		shader.setUniform2f("TextureScale", new Vec2(1, 1));
		shader.setUniform4f("GlobalColor", color.toVector4());
		Vec2 cursor = new Vec2();
		for (int i = 0; i <txt.length(); i++)
		{
			char c = txt.charAt(i);
			if (c == ' ')
			{
				cursor.x += (w / 2) * fontScale;
				continue;
			}
			if (c == '\n')
			{
				cursor.x = 0;
				cursor.y += h;
				continue;
			}
			int id = fontArray.indexOf(c);
			if (id < 0)
				continue;
			Vec2 offset = new Vec2((id % 10) * tw, (id / 10) * th);
			shader.setUniformMat4f(Shaders.VIEW_NAME, Mat4.translate(pos.copy().add(cursor)));
			shader.setUniform2f("TextureOffset", offset);
			if (fontMesh != null)
				fontMesh.render(GL11.GL_QUADS);
			cursor.x += getOffset(c) * fontScale;
		}
		shader.setUniform2f("TextureOffset", new Vec2());
	}
	
	public static Vec2 metricString(String txt)
	{
		if (font == null)
			load(Texture.FileTexture(Text2D.class.getResourceAsStream("font.png"), true));
		float w = font.getWidth() / 10f; 
		float h = font.getHeight() / 10f;
		Vec2 cursor = new Vec2();
		Vec2 metric = new Vec2(0, 0);
		for (int i = 0; i <txt.length(); i++)
		{
			char c = txt.charAt(i);
			if (c == ' ')
			{
				cursor.x += w;
				metric.x = Math.max(metric.x, cursor.x);
				continue;
			}
			if (c == '\n')
			{
				cursor.x = 0;
				cursor.y += h;
				metric.y = Math.max(metric.y, cursor.y);
				continue;
			}
			int id = fontArray.indexOf(c);
			if (id < 0)
				continue;
			cursor.x += getOffset(c);
			metric.x = Math.max(metric.x, cursor.x);
		}
		if (metric.x > 0)
			metric.y += h;
		return (metric);
	}

	public static float getFontScale() {
		return fontScale;
	}

	public static void setFontScale(float fontScale) {
		Text2D.fontScale = fontScale;
	}
}
