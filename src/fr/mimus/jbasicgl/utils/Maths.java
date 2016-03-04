package fr.mimus.jbasicgl.utils;

public class Maths
{
	public static float rand(float min, float max)
	{
		float v = max - min;
		return (min + (float)(Math.random() * v));
	}
}
