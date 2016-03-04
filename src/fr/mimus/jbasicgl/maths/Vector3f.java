package fr.mimus.jbasicgl.maths;

/**
 * @author Mimus
 * @version 1.0b
 */
public class Vector3f
{
	public float x;
	public float y;
	public float z;
	
	/**
	 * Créé un vecteur à zero
	 */
	public Vector3f()
	{
		this(0, 0, 0);
	}
	
	/**
	 * Créé un vecteur en defini les deux axes à la même valeur.
	 * @param v Valeur X, Y et Z
	 */
	public Vector3f(float v)
	{
		this(v, v, v);
	}
	
	/**
	 * Copie les valeur d'un vecteur dans un nouveau vecteur.
	 * @param v Vecteur a copier
	 */
	public Vector3f(Vector3f v)
	{
		this(v.x, v.y, v.z);
	}
	
	/**
	 * On crée un vecteur a partir d'un vecteur 2f et d'une valeur
	 * @param v Vecteur2f
	 * @param z valeur axe Z
	 */
	public Vector3f(Vector2f v, float z)
	{
		this(v.x, v.y, z);
	}
	
	/**
	 * Créer un vecteur en définisent chacun des axes.
	 * @param x Axe x
	 * @param y Axe y
	 * @param z Axe z
	 */
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f add(float v)
	{
		return (this.add(v, v, v));
	}
	
	public Vector3f add(Vector3f v)
	{
		return (this.add(v.x, v.y, v.z));
	}
	
	public Vector3f add(float x, float y, float z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
		return (this);
	}
	
	public Vector3f sub(float v)
	{
		return (this.sub(v, v, v));
	}
	
	public Vector3f sub(Vector3f v)
	{
		return (this.sub(v.x, v.y, v.z));
	}
	
	public Vector3f sub(float x, float y, float z)
	{
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return (this);
	}
	
	public Vector3f div(float v)
	{
		return (this.div(v, v, v));
	}
	
	public Vector3f div(Vector3f v)
	{
		return (this.div(v.x, v.y, v.z));
	}
	
	public Vector3f div(float x, float y, float z)
	{
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return (this);
	}
	
	public Vector3f mul(float v)
	{
		return (this.mul(v, v, v));
	}
	
	public Vector3f mul(Vector3f v)
	{
		return (this.mul(v.x, v.y, v.z));
	}
	
	public Vector3f mul(float x, float y, float z)
	{
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return (this);
	}
	
	public Vector3f set(float v)
	{
		return (this.set(v, v, v));
	}
	
	public Vector3f set(Vector3f v)
	{
		return (this.set(v.x, v.y, v.z));
	}
	
	public Vector3f set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return (this);
	}
	
	public double lengthSqrd()
	{
		return (x * x + y * y + z * z);
	}
	
	public double length()
	{
		return (Math.sqrt(lengthSqrd()));
	}
	
	public Vector3f normalize()
	{
		double lenght = length();
		x = (float) (x / lenght);
		y = (float) (y / lenght);
		z = (float) (z / lenght);
		return (this); 
	}
	
	public Vector3f negate() {
		x = -x;
		y = -y;
		z = -z;
		return (this); 
	}
	
	public float dot(Vector3f v1) {
		return (x * v1.x + y * v1.y + z * v1.z);
	}
	
	public static Vector3f cross(Vector3f v1, Vector3f v2)
	{
		Vector3f v = new Vector3f();
		v.x = v1.y * v2.z - v1.z * v2.y;
		v.y = v1.z * v2.x - v1.x * v2.z;
		v.z = v1.x * v2.y - v1.y * v2.x;
		v.normalize();
		return (v); 
	}
	
	public Vector2f xy()
	{
		return (new Vector2f(x, y));
	}
	
	public Vector2f xz()
	{
		return (new Vector2f(x, z));
	}
	
	public Vector2f yz()
	{
		return (new Vector2f(y, z));
	}
	
	public Vector3f copy()
	{
		return (new Vector3f(this));
	}
	
	public float[] toArray()
	{
		return (new float[]{x, y, z});
	}

	public String toString()
	{
		return "[" + x + ", " + y + ", " + z + "]";
	}
}
