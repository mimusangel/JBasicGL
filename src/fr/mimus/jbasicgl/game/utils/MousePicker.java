package fr.mimus.jbasicgl.game.utils;

import fr.mimus.jbasicgl.maths.Mat4;
import fr.mimus.jbasicgl.maths.Vec2;
import fr.mimus.jbasicgl.maths.Vec3;
import fr.mimus.jbasicgl.maths.Vec4;

public class MousePicker
{
	public static Vec3 direction(Vec2 mpos, float width, float height, Mat4 projection, Mat4 view)
	{
		Vec2 normalizedCoords = mpos.copy().mul(2.0f).div(width, height).sub(1f);
		Vec4 clipCoords = new Vec4(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
		
		Mat4 iProjMat = projection.invert();
		Vec4 eyeCoords = Mat4.transform(iProjMat, clipCoords);
		eyeCoords = new Vec4(eyeCoords.x, eyeCoords.y, -1f, 0f);
		Mat4 iViewMat = view.invert();
		Vec4 rayWorld = Mat4.transform(iViewMat, eyeCoords);
		Vec3 raydir = new Vec3(rayWorld.x, rayWorld.y, rayWorld.z);
		raydir.normalize();
		return (raydir);
	}
}
