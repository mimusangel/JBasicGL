package fr.mimus.jbasicgl.game.graphics.obj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import fr.mimus.jbasicgl.maths.Vec3;

public class OBJMaterial
{
	private HashMap<String, Material> materials;
	public OBJMaterial()
	{
		materials = new HashMap<String, Material>();
	}
	
	public Material getMaterial(String name)
	{
		return (materials.get(name));
	}
	
	
	private static Vec3 parse3f(String line)
	{
		String parse[] = line.split(" ");
		float x = Float.valueOf(parse[1]);
		float y = Float.valueOf(parse[2]);
		float z = Float.valueOf(parse[3]);
		return new Vec3(x, y, z);
	}
	
	public static OBJMaterial load(String name, String path)
	{
		try {

			BufferedReader reader = new BufferedReader(new FileReader(path + name));
			String line = "";
			OBJMaterial mtl = new OBJMaterial();
			Material mat = null;
			while((line=reader.readLine()) != null)
			{
				if (line.startsWith("#"))
					continue;
				if (line.startsWith("newmtl "))
				{
					if (mat != null)
						mtl.materials.put(mat.name, mat);
					String parse[] = line.split(" ");
					mat = new Material(parse[1], path);
				}
				else if (mat != null)
				{
					if (line.startsWith("map_Kd "))
					{
						String parse[] = line.split(" ");
						mat.loadTexture(parse[1]);
					}
					else if (line.startsWith("Ks "))
					{
						mat.specColor = parse3f(line);
					}
					else if (line.startsWith("Ka "))
					{
						mat.ambientColor = parse3f(line);
					}
					else if (line.startsWith("Kd "))
					{
						mat.diffColor = parse3f(line);
					}
					
				}
			}
			reader.close();
			if (mat != null)
				mtl.materials.put(mat.name, mat);
			return (mtl);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File doesn't exist! " + path + name);
		}
		catch (IOException e)
		{
			System.err.println("Read Error! " + path + name);
			System.err.println(e.getMessage());
		}
		return (null);
	}
	
	public ArrayList<Material> toList()
	{
		ArrayList<Material> list = new ArrayList<Material>();
		for (Material m : materials.values())
		{
			System.out.println("Material: " + m.name);
			System.out.println("- texture: " + (m.texture != null));
			System.out.println("- diffColor: " + m.diffColor.toString());
			System.out.println("- specColor: " + m.specColor.toString());
			System.out.println("- ambientColor: " + m.ambientColor.toString());
			list.add(m);
		}
		return (list);
	}

	public int size()
	{
		return (materials.size());
	}
	
}
