package fr.mimus.jbasicgl.maths;

import fr.mimus.jbasicgl.graphics.Color4f;

public class Triangle
{
	Vector3f 	A;
	Vector3f 	B;
	Vector3f 	C;
	Color4f		color;
	Vector3f	normal;
	Vector2f	uvA;
	Vector2f	uvB;
	Vector2f	uvC;
	
	public Triangle(Vector3f a, Vector3f b, Vector3f c, Color4f color)
	{
		A = a;
		B = b;
		C = c;
		this.color = color;
		normal = Vector3f.cross(A, B, C);
		Vector3f pA = A.copy().normalize();
		Vector3f pB = B.copy().normalize();
		Vector3f pC = C.copy().normalize();
		uvA = new Vector2f((float)(Math.atan2(pA.x, pA.z) / (Math.PI * 2.0)) + 0.5f,
				(float)(Math.asin(pA.y) / Math.PI) + 0.5f);
		uvB = new Vector2f((float)(Math.atan2(pB.x, pB.z) / (Math.PI * 2.0)) + 0.5f,
				(float)(Math.asin(pB.y) / Math.PI) + 0.5f);
		uvC = new Vector2f((float)(Math.atan2(pC.x, pC.z) / (Math.PI * 2.0)) + 0.5f,
				(float)(Math.asin(pC.y) / Math.PI) + 0.5f);
	}

	public Vector3f getA() {
		return A;
	}

	public void setA(Vector3f a) {
		A = a;
	}

	public Vector3f getB() {
		return B;
	}

	public void setB(Vector3f b) {
		B = b;
	}

	public Vector3f getC() {
		return C;
	}

	public void setC(Vector3f c) {
		C = c;
	}

	public Color4f getColor() {
		return color;
	}

	public void setColor(Color4f color) {
		this.color = color;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}

	public Vector2f getUvA() {
		return uvA;
	}

	public void setUvA(Vector2f uvA) {
		this.uvA = uvA;
	}

	public Vector2f getUvB() {
		return uvB;
	}

	public void setUvB(Vector2f uvB) {
		this.uvB = uvB;
	}

	public Vector2f getUvC() {
		return uvC;
	}

	public void setUvC(Vector2f uvC) {
		this.uvC = uvC;
	}

}
