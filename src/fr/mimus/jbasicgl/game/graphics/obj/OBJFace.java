package fr.mimus.jbasicgl.game.graphics.obj;

public class OBJFace
{
	public int[] vertexIndices;
	public int[] normalIndices;
    public int[] texturesIndices;
    public String materialName;
    public String groupName;
	public OBJFace(int[] vertexArray, int[] normalArray, int[] textureArray)
	{
		vertexIndices = vertexArray;
		normalIndices = normalArray;
		texturesIndices = textureArray;
		materialName = "";
		groupName = "";
	}

}
