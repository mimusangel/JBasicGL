package fr.mimus.jbasicgl.game.graphics;

import org.lwjgl.opengl.GL11;

import fr.mimus.jbasicgl.game.animation.Animation;
import fr.mimus.jbasicgl.graphics.Color4f;
import fr.mimus.jbasicgl.graphics.Mesh;
import fr.mimus.jbasicgl.graphics.Shaders;
import fr.mimus.jbasicgl.graphics.Texture;
import fr.mimus.jbasicgl.maths.Mat4;
import fr.mimus.jbasicgl.maths.Vec2;

public class Sprite
{
	private static Mesh mesh;
	protected Texture texture;
	protected int width;
	protected int height;
	protected Animation anim;
	protected Vec2 textureScale;
	protected Vec2 textureOffset;
	protected boolean enableAnimation;
	protected boolean flipX;
	protected boolean flipY;
	protected Vec2 scale;
	
	
	public Sprite(Texture texture, int width, int height, Animation anim)
	{
		this.texture = texture;
		this.width = width;
		this.height = height;
		this.anim = anim;
		this.anim.init();
		this.textureScale = new Vec2((float)width / (float)texture.getWidth(), (float)height / (float)texture.getHeight());
		this.textureOffset = new Vec2();
		if (mesh == null)
		{
			mesh = new Mesh(4);
			mesh.addVertices(0, 0).addTexCoord2f(0, 0).addColor(Color4f.WHITE);
			mesh.addVertices(1, 0).addTexCoord2f(1, 0).addColor(Color4f.WHITE);
			mesh.addVertices(1, 1).addTexCoord2f(1, 1).addColor(Color4f.WHITE);
			mesh.addVertices(0, 1).addTexCoord2f(0, 1).addColor(Color4f.WHITE);
			mesh.buffering();
		}
		enableAnimation = true;
		flipX = false;
		flipY = false;
		scale = new Vec2(1, 1);
	}
	
	public void render(Shaders shader, Vec2 pos)
	{
		if (anim == null || mesh == null)
			return;
		texture.bind();

		int wNb = texture.getWidth() / width;
		int hNb = texture.getHeight() / height;
		int frameX = anim.getFrame().getID() % wNb;
		int frameY = anim.getFrame().getID() / wNb;
		if (frameY < hNb)
		{
			this.textureOffset.set((float)frameX, (float)frameY).mul(textureScale);
			shader.setUniformMat4f(Shaders.MODEL_NAME, Mat4.multiply(Mat4.translate(pos.x + (flipX ? width : 0), pos.y + (flipY ? height : 0)), Mat4.scale((flipX ? -width : width) * scale.x, (flipY ? -height : height) * scale.y, 1)));
			shader.setUniform2f("TextureScale", textureScale);
			shader.setUniform2f("TextureOffset", textureOffset);
			mesh.render(GL11.GL_QUADS);
		}
		
		Texture.unbind();
	}
	
	public void update(int tick, double elapse)
	{
		if (anim == null || mesh == null)
			return;
		if (enableAnimation)
			anim.update(tick, elapse);
	}
	
	public Sprite setFrame(int frame)
	{
		if (anim != null)
			anim.setCurrentFrame(frame);
		return (this);
	}
	
	public Sprite setTexture(Texture texture)
	{
		this.texture = texture;
		return (this);
	}

	public Animation getAnimation()
	{
		return anim;
	}

	public Sprite setAnimation(Animation anim)
	{
		this.anim = anim;
		return (this);
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public Animation getAnim()
	{
		return anim;
	}

	public Sprite setAnim(Animation anim)
	{
		this.anim = anim;
		return (this);
	}

	public boolean isEnableAnimation()
	{
		return enableAnimation;
	}

	public Sprite setEnableAnimation(boolean enable)
	{
		this.enableAnimation = enable;
		return (this);
	}

	public boolean isFlipX()
	{
		return flipX;
	}

	public Sprite setFlipX(boolean flipX)
	{
		this.flipX = flipX;
		return (this);
	}

	public boolean isFlipY()
	{
		return flipY;
	}

	public Sprite setFlipY(boolean flipY)
	{
		this.flipY = flipY;
		return (this);
	}

	public Vec2 getScale()
	{
		return scale;
	}
	
	public Sprite setScale(float x, float y)
	{
		scale.set(x, y);
		return (this);
	}
	
	public Sprite setScale(float xy)
	{
		return (setScale(xy, xy));
	}
}
