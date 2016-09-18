package fr.mimus.jbasicgl.game.graphics.obj;

import java.io.File;

import fr.mimus.jbasicgl.graphics.Texture;
import fr.mimus.jbasicgl.maths.Vec3;

public class Material
{
	public Texture texture;
	public Vec3 diffColor; // kd
	public Vec3 specColor; // ks
	public Vec3 ambientColor; // ka
	public String name;
	public String path;
	public Material(String name, String path)
	{
		this.name = name;
		this.path = path;
		diffColor = new Vec3(1.0f);
		specColor = new Vec3(1.0f);
		ambientColor = new Vec3(1.0f);
		String n = getTextureExt(path + name);
		if (n.length() > 0)
		{
			texture = Texture.FileTexture(n);
			System.out.println("Texture Loaded: " + n);
		}
	}
	
	private String getTextureExt(String n)
	{
		File f = new File(n + ".png");
		if(f.exists())
			return (n + ".png");
		f = new File(n + ".jpg");
		if(f.exists())
			return (n + ".jpg");
		return "";
	}

	public void loadTexture(String name)
	{
		File f = new File(path + "/" + name);
		if (f.exists())
		{
			if (name.toLowerCase().endsWith(".tga"))
				texture = Texture.FileTGATexture(path + "/" + name);
			else
				texture = Texture.FileTexture(path + "/" + name);
		}
	}

}
