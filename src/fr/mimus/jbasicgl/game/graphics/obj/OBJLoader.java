package fr.mimus.jbasicgl.game.graphics.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.mimus.jbasicgl.graphics.Color4f;
import fr.mimus.jbasicgl.graphics.Mesh;
import fr.mimus.jbasicgl.maths.Vec2;
import fr.mimus.jbasicgl.maths.Vec3;

public class OBJLoader
{
	private String path;
	private ArrayList<Vec3> vertices;
	private ArrayList<Vec3> normals;
	private ArrayList<Vec2> texturesUV;
	private ArrayList<OBJFace> faces;
	private OBJMaterial material;
	private ArrayList<String> groups;
	
	private OBJLoader(String path)
	{
		this.path = path;
		vertices = new ArrayList<Vec3>();
		normals = new ArrayList<Vec3>();
		texturesUV = new ArrayList<Vec2>();
		faces = new ArrayList<OBJFace>();
		material = null;
		groups = new ArrayList<String>();
	}
	
	private Vec3 parse3f(String line)
	{
		String parse[] = line.split(" ");
		float x = Float.valueOf(parse[1]);
		float y = Float.valueOf(parse[2]);
		float z = Float.valueOf(parse[3]);
		return new Vec3(x, y, z);
	}
	
	private Vec2 parse2f(String line)
	{
		String parse[] = line.split(" ");
		float x = Float.valueOf(parse[1]);
		float y = 1-Float.valueOf(parse[2]);
		return new Vec2(x, y);
	}
	
	private OBJFace[] parseFace(String line)
	{
		String parse[] = line.split(" ");
		String g1[] = parse[1].split("/");
		String g2[] = parse[2].split("/");
		String g3[] = parse[3].split("/");
		int[] vertexIndicesArray = {Integer.valueOf(g1[0]),
                Integer.valueOf(g2[0]), 
                Integer.valueOf(g3[0])};
		int[] normalIndicesArray = {-1, -1, -1};
		int[] normalTextureArray = {-1, -1, -1};
		if(normals.size()>0)
		{
			normalIndicesArray = new int[]{Integer.valueOf(g1[2]),
		            		Integer.valueOf(g2[2]), 
		        			Integer.valueOf(g3[2])};
		}
		if(texturesUV.size()>0)
		{
			normalTextureArray = new int[]{Integer.valueOf(g1[1]),
			            	Integer.valueOf(g2[1]), 
			        		Integer.valueOf(g3[1])};
		}
		
		OBJFace[] r;
        if(parse.length>4) {
        	r = new OBJFace[2];
    		String g4[] = parse[4].split("/");
    		
    		int[] vertexArray = {Integer.valueOf(g3[0]),
                Integer.valueOf(g4[0]), 
                Integer.valueOf(g1[0])};
    		
			int[] normalArray = {-1, -1, -1};
			int[] textureArray = {-1, -1, -1};
			if(normals.size()>0)
			{
				normalArray = new int[]{Integer.valueOf(g3[2]),
			                		Integer.valueOf(g4[2]), 
			            			Integer.valueOf(g1[2])};
			}
			if(texturesUV.size()>0)
			{
				textureArray = new int[]{Integer.valueOf(g3[1]),
			                		Integer.valueOf(g4[1]), 
			            			Integer.valueOf(g1[1])};
			}
			r[1] = new OBJFace(vertexArray, normalArray, textureArray);
        } else {
        	r = new OBJFace[1];
        }
        r[0] = new OBJFace(vertexIndicesArray, normalIndicesArray, normalTextureArray);
        return (r);
	}
	
	public OBJMesh load()
	{
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader(path));
			String line = "";
			String materialName = "";
			String groupName = "";
			System.out.println("OBJ Loading...");
			while((line=reader.readLine()) != null)
			{
				if (line.startsWith("#"))
					continue;
				if (line.startsWith("mtllib ")) // Load Material
				{
					String fileMTL = line.substring(7);
					File f = new File(path);
					System.out.println("MTL Loading...");
					material = OBJMaterial.load(fileMTL, f.getParent() + File.separatorChar);
					System.out.println("Material: " + material.size());
					System.out.println("MTL Loaded !");
				}
				else if (line.startsWith("usemtl ")) // Use Material
				{
					String parse[] = line.split(" ");
					materialName = parse[1];
				}
				else if (line.startsWith("g ")) // Group
				{
					String parse[] = line.split(" ");
					if (!groups.contains(parse[1]))
						groups.add(parse[1]);
					groupName = parse[1];
				}
				else if (line.startsWith("vn ")) // Normal
				{
		        	Vec3 v = parse3f(line);
		        	normals.add(v);
		        }
				else if (line.startsWith("vt ")) // Texture UV
				{
		        	Vec2 v = parse2f(line);
		        	texturesUV.add(v);
		        }
				else if (line.startsWith("v ")) // Vertice
				{
					Vec3 v = parse3f(line);
		        	vertices.add(v);
		        }
				else if (line.startsWith("f ")) // Face
				{
					OBJFace[] f = parseFace(line);
		        	for(int i=0; i<f.length; i++)
		        	{
		        		f[i].materialName = materialName;
		        		f[i].groupName = groupName;
		        		faces.add(f[i]);
		        	}
		        }
			}
			reader.close();
			System.out.println("Vertices: " + vertices.size());
			System.out.println("Textures UV: " + texturesUV.size());
			System.out.println("Normals: " + normals.size());
			System.out.println("Groups: " + groups.size());
			System.out.println("OBJ Loaded !");
			System.out.println("Mesh Creating...");
			Mesh mesh = new Mesh(faces.size() * 3);
			ArrayList<Material> listMaterial = material.toList();
			for (OBJFace face : faces)
			{
				mesh.addVertices(vertices.get(face.vertexIndices[0] - 1)).addColor(Color4f.WHITE);
				mesh.addVertices(vertices.get(face.vertexIndices[2] - 1)).addColor(Color4f.WHITE);
				mesh.addVertices(vertices.get(face.vertexIndices[1] - 1)).addColor(Color4f.WHITE);
				if (texturesUV.size() > 0)
				{
					mesh.addTexCoord2f(texturesUV.get(face.texturesIndices[0] - 1));
					mesh.addTexCoord2f(texturesUV.get(face.texturesIndices[2] - 1));
					mesh.addTexCoord2f(texturesUV.get(face.texturesIndices[1] - 1));
					if (face.materialName.length() > 0)
					{
						for (int i = 0; i < listMaterial.size(); i++)
						{
							Material m = listMaterial.get(i);
							if (m.name.equals(face.materialName))
							{
								mesh.addTexture(i);
								mesh.addTexture(i);
								mesh.addTexture(i);
								break;
							}
						}
					}
				}
				if (normals.size() > 0)
				{
					mesh.addNormal(normals.get(face.normalIndices[0] - 1));
					mesh.addNormal(normals.get(face.normalIndices[2] - 1));
					mesh.addNormal(normals.get(face.normalIndices[1] - 1));
				}
			}
			mesh.buffering();
			System.out.println("Mesh Created!");
			return (new OBJMesh(listMaterial, mesh));
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File doesn't exist! " + this.path);
		}
		catch (IOException e)
		{
			System.err.println("Read Error! " + this.path);
			System.err.println(e.getMessage());
		}
		return (null);
	}
	
	public static OBJMesh load(String path)
	{
		OBJLoader obj = new OBJLoader(path);
		return (obj.load());
	}
}
