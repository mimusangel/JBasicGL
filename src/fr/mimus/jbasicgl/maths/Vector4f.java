package fr.mimus.jbasicgl.maths;

/**
 * @author Mimus
 * @version 1.0b
 */
public class Vector4f
{
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Vector4f()
	{
		this(0, 0, 0, 0);
	}
	
	public Vector4f(float v)
	{
		this(v, v, v, v);
	}
	
	public Vector4f(Vector4f v)
	{
		this(v.x, v.y, v.z, v.w);
	}
	
	public Vector4f(Vector2f v, float z, float w)
	{
		this(v.x, v.y, z, w);
	}
	
	public Vector4f(Vector3f v, float w)
	{
		this(v.x, v.y, v.z, w);
	}
	
	public Vector4f(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector3f xyz()
	{
		return (new Vector3f(x, y, z));
	}
	
	public Vector4f copy()
	{
		return (new Vector4f(this));
	}
	
	public float[] toArray()
	{
		return (new float[]{x, y, z, w});
	}
}
