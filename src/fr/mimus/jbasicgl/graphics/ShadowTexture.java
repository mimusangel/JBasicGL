package fr.mimus.jbasicgl.graphics;

import fr.mimus.jbasicgl.utils.IDisposable;
import fr.mimus.jbasicgl.utils.MemoryClass;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;


public class ShadowTexture implements IDisposable
{
	private int frameIndex;
	private int textureIndex;
	private int width, height;
	public ShadowTexture(int w, int h)
	{
		this.width = w;
		this.height = h;
		
		frameIndex = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, frameIndex);
		
		textureIndex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureIndex);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT16, this.width, this.height, 0,GL_DEPTH_COMPONENT, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

		glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, textureIndex, 0);
		glDrawBuffers(GL_NONE);
		
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
		{
	        System.out.println("Framebuffer not complete!");
        System.exit(-1);
	    }
		else
			System.out.println("Framebuffer is complete!");
		glBindRenderbuffer(GL_RENDERBUFFER, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		MemoryClass.addClass(this);
	}
	
	public void target()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, frameIndex);
	}
	
	public static void noTarget()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	/**
	 * Active l'utilisation de la textures.
	 */
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, textureIndex);
	}

	/**
	 * Desactive l'utilisation des textures.
	 */
	public static void unbind()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void dispose()
	{
		//glDeleteRenderbuffers(depthRenderBuffer);
		glDeleteTextures(textureIndex);
		glDeleteFramebuffers(frameIndex);
	}

}

