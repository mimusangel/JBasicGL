package fr.mimus.jbasicgl.utils;
import fr.mimus.jbasicgl.graphics.Shaders;
import fr.mimus.jbasicgl.input.Keyboard;
import fr.mimus.jbasicgl.input.Mouse;
import fr.mimus.jbasicgl.maths.Matrix4f;
import fr.mimus.jbasicgl.maths.Vector3f;

public class Camera
{
	public Vector3f	pos = new Vector3f();
	public Vector3f	rot = new Vector3f();
	public float	moveSpeed;
	public float	rotSpeed;
	
	public Camera()
	{
		this(0.075f, 0.75f);
	}
	
	public Camera(float moveSpeed, float rotSpeed)
	{
		this.moveSpeed = moveSpeed;
		this.rotSpeed = rotSpeed;
	}
	
	public void render(Shaders shader, String uniformName)
	{
		shader.setUniformMat4f(uniformName, Matrix4f.multiply(
			Matrix4f.rotate(rot.x, rot.y, rot.z),
			Matrix4f.translate(-pos.x, -pos.y, -pos.z))
		);
	}
	
	public void update(Keyboard keyboard, Mouse mouse)
	{
		if (mouse.isDown(Mouse.MOUSE_BUTTON_LEFT))
			mouse.setGrabbed(true);
		if (keyboard.isDown(Keyboard.KEY_ESCAPE))
			mouse.setGrabbed(false);
		
		if (mouse.isGrabbed())
		{
			rot.x += mouse.getDY() * rotSpeed;
			rot.y += mouse.getDX() * rotSpeed;
			if (rot.x > 90)
				rot.x = 90;
			if (rot.x < -90)
				rot.x = -90;
		}
		else
			return ;
		
		if (keyboard.isDown(Keyboard.KEY_W))
		{
			Vector3f forward = forward(rot);
			pos.add(forward.mul(-moveSpeed));
		}
		if (keyboard.isDown(Keyboard.KEY_S))
		{
			Vector3f forward = forward(rot);
			pos.add(forward.mul(moveSpeed));
		}
		if (keyboard.isDown(Keyboard.KEY_A))
		{
			Vector3f straf = straf(rot);
			pos.add(straf.mul(moveSpeed));
		}
		if (keyboard.isDown(Keyboard.KEY_D))
		{
			Vector3f straf = straf(rot);
			pos.add(straf.mul(-moveSpeed));
		}
		if (keyboard.isDown(Keyboard.KEY_SPACE))
			pos.y += moveSpeed;
		if (keyboard.isDown(Keyboard.KEY_LEFT_SHIFT))
			pos.y -= moveSpeed;
	}
	
	public static Vector3f forward(Vector3f rot)
    {
		Vector3f r = new Vector3f();
    	float cosY = (float) Math.cos(Math.toRadians(rot.y - 90));
		float sinY = (float) Math.sin(Math.toRadians(rot.y - 90));
		float cosP = (float) Math.cos(Math.toRadians(-rot.x));
		float sinP = (float) Math.sin(Math.toRadians(-rot.x));
		
		r.x = -(cosY * cosP);
		r.y = -sinP;
		r.z = sinY * cosP;
		r.normalize();
		return (r);
    }
    
    public static Vector3f straf(Vector3f rot)
    {
		Vector3f r = new Vector3f();
    	float cosY = (float) Math.cos(Math.toRadians(rot.y));
		float sinY = (float) Math.sin(Math.toRadians(rot.y));
		float cosP = (float) Math.cos(Math.toRadians(-rot.x));
		
		r.x = -(cosY * cosP);
		r.z = sinY * cosP;
		r.normalize();
		return (r);
    }
}
