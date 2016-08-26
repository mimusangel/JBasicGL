package fr.mimus.jbasicgl.maths;

/**
 * @author Mimus
 * @version 1.0b
 */
public class Vector2f
{
	public float x;
	public float y;
	
	/**
	 * Créé un vecteur à zero
	 */
	public Vector2f()
	{
		this(0, 0);
	}
	
	/**
	 * Créé un vecteur en defini les deux axes à la même valeur.
	 * @param v Valeur X et Y
	 */
	public Vector2f(float v)
	{
		this(v, v);
	}
	
	/**
	 * Copie les valeur d'un vecteur dans un nouveau vecteur.
	 * @param v Vecteur a copier
	 */
	public Vector2f(Vector2f v)
	{
		this(v.x, v.y);
	}
	
	/**
	 * Créer un vecteur en définisent chacun des axes.
	 * @param x Axe X
	 * @param y Axe Y
	 */
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2f add(float v)
	{
		return (this.add(v, v));
	}
	
	public Vector2f add(Vector2f v)
	{
		return (this.add(v.x, v.y));
	}
	
	public Vector2f add(float x, float y)
	{
		this.x += x;
		this.y += y;
		return (this);
	}
	
	public Vector2f sub(float v)
	{
		return (this.sub(v, v));
	}
	
	public Vector2f sub(Vector2f v)
	{
		return (this.sub(v.x, v.y));
	}
	
	public Vector2f sub(float x, float y)
	{
		this.x -= x;
		this.y -= y;
		return (this);
	}
	
	public Vector2f div(float v)
	{
		return (this.div(v, v));
	}
	
	public Vector2f div(Vector2f v)
	{
		return (this.div(v.x, v.y));
	}
	
	public Vector2f div(float x, float y)
	{
		this.x /= x;
		this.y /= y;
		return (this);
	}
	
	public Vector2f mul(float v)
	{
		return (this.mul(v, v));
	}
	
	public Vector2f mul(Vector2f v)
	{
		return (this.mul(v.x, v.y));
	}
	
	public Vector2f mul(float x, float y)
	{
		this.x *= x;
		this.y *= y;
		return (this);
	}
	
	public Vector2f set(float v)
	{
		return (this.set(v, v));
	}
	
	public Vector2f set(Vector3f v)
	{
		return (this.set(v.x, v.y));
	}
	
	public Vector2f set(float x, float y)
	{
		this.x = x;
		this.y = y;
		return (this);
	}
	
	public double lengthSqrd()
	{
		return (x * x + y * y);
	}
	
	public double length()
	{
		return (Math.sqrt(lengthSqrd()));
	}
	
	public Vector2f normalise()
	{
		double lenght = length();
		x = (float) (x / lenght);
		y = (float) (y / lenght);
		return this;
	}
	
	public Vector2f negate() {
		x = -x;
		y = -y;
		return this; 
	}
	
	public float dot(Vector2f v)
	{
		return (x * v.x + y * v.y);
	}
	
	public Vector2f cross()
	{
		Vector2f v = new Vector2f(y, -x);
		return (v.normalise());
	}
	
	public float crossProduct(Vector2f v)
	{
		return (x * v.y - y * v.x);
	}
	
	public Vector2f copy()
	{
		return (new Vector2f(this));
	}
	
	public float[] toArray()
	{
		return (new float[]{x, y});
	}
}
