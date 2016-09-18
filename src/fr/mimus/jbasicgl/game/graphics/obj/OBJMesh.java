package fr.mimus.jbasicgl.game.graphics.obj;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import fr.mimus.jbasicgl.graphics.Mesh;
import fr.mimus.jbasicgl.graphics.Shaders;
import fr.mimus.jbasicgl.graphics.Texture;
import fr.mimus.jbasicgl.utils.MemoryClass;

public class OBJMesh
{
	public ArrayList<Material> materials;
	public Mesh mesh;
	
	public OBJMesh(ArrayList<Material> mat, Mesh mesh)
	{
		materials = mat;
		this.mesh = mesh;
	}
	
	public static OBJMesh load(String path)
	{
		return (OBJLoader.load(path));
	}
	
	public void render(Shaders shader)
	{
		for (int i = 1; i < materials.size(); i++)
			shader.setUniform1i("Textures[" + i + "]", i);
		
		for (int i = materials.size() - 1; i >= 0 ; i--)
		{
			glActiveTexture(GL_TEXTURE0 + i);
			if (materials.get(i).texture != null)
				materials.get(i).texture.bind();
			shader.setUniform3f("Materials[" + i + "].diff", materials.get(i).diffColor);
			shader.setUniform3f("Materials[" + i + "].spec", materials.get(i).specColor);
			shader.setUniform3f("Materials[" + i + "].ambient", materials.get(i).ambientColor);
		}
		mesh.render(GL11.GL_TRIANGLES);
		for (int i = materials.size() - 1; i >= 0 ; i--)
		{
			glActiveTexture(GL_TEXTURE0 + i);
			Texture.unbind();
			glDisable(GL_TEXTURE_2D);
		}
	}
	
	public static void info()
	{
		System.out.println("Shader name: Textures[i]");
	}
	
	public void dispose()
	{
		for (int i = 0; i < materials.size() ; i++)
		{
			if (materials.get(i).texture != null)
				MemoryClass.clear(materials.get(i).texture);
		}
		MemoryClass.clear(mesh);
	}

}
