package fr.mimus.jbasicgl.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import fr.mimus.jbasicgl.utils.BufferAlloc;
import fr.mimus.jbasicgl.utils.IDisposable;
import fr.mimus.jbasicgl.utils.MemoryClass;
import fr.mimus.jbasicgl.utils.TGAReader;

import static org.lwjgl.opengl.GL11.*;

/**
 * Class de gestion des textures, permet de les charger et les utiliser simplement.
 * @author Mimus
 * @version 1.0b
 */
public class Texture implements IDisposable
{
	private int index;
	private int width, height;
	private int[] pixels = null;
	
	/**
	 * Créer une texture a partir d'un chemin, si celci est introuvable créer une image d'erreur.
	 * @param path Chemin vers une image.
	 * @param nearest Defini le lissage de la texture
	 * @return Retourne la texture créer
	 */
	
	public static Texture FileTexture(InputStream path, boolean nearest)
	{
		try
		{
			BufferedImage image = ImageIO.read(path);
			return (new Texture(image, nearest));
		} 
		catch (IOException e) 
		{
			System.err.println("Error TEXTURE: Can't load texture: "+path);
		}
		return (new Texture(errorTexture(), nearest));
	}
	public static Texture FileTexture(String path, boolean nearest)
	{
		try
		{
			return (FileTexture(new FileInputStream(path), nearest));
		} 
		catch (IOException e) 
		{
			System.err.println("Error TEXTURE: Can't load texture: "+path);
		}
		return (new Texture(errorTexture(), nearest));
	}
	
	public static Texture FileTexture(String path)
	{
		return FileTexture(path, true);
	}
	
	public static Texture FileTGATexture(String path, boolean nearest)
	{
		try
		{
			FileInputStream fis = new FileInputStream(path);
	        byte [] buffer = new byte[fis.available()];
	        fis.read(buffer);
	        fis.close();
	
	        int [] pixels = TGAReader.read(buffer, TGAReader.ARGB);
	        int width = TGAReader.getWidth(buffer);
	        int height = TGAReader.getHeight(buffer);
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        image.setRGB(0, 0, width, height, pixels, 0, width);
	        return (new Texture(image, nearest));
		} 
		catch (IOException e) 
		{
			System.err.println("Error TEXTURE: Can't load texture: "+path);
		}
		return (new Texture(errorTexture(), nearest));
    }
	
	public static Texture FileTGATexture(String path)
	{
		return (FileTGATexture(path, true));
	}

	/**
	 * Créer une texture d'erreur.
	 * @return BufferedImage de l'image d'erreur.
	 */
	private static BufferedImage errorTexture()
	{
		BufferedImage buff = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buff.createGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, 64, 64);
			g.setColor(new Color(255, 0, 255));
			g.fillRect(0, 0, 32, 32);
			g.fillRect(32, 32, 64, 64);
		g.dispose();
		return (buff);
	}
	
	public static Texture loadAutotile(String path)
	{
		return (loadAutotile(path, true));
	}
	
	public static Texture loadAutotile(String path, boolean nearest)
	{
		try
		{
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			return (new Texture(loadAutotile(image), nearest));
		} 
		catch (IOException e) 
		{
			System.err.println("Error TEXTURE: Can't load texture: "+path);
		}
		return (new Texture(errorTexture(), nearest));
	}
	
	private static BufferedImage loadAutotile(BufferedImage ii)
	{
		BufferedImage image = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
			for(int i = 0; i <= 0xff; i ++)
			{
				int x = i % 16;
				int y = i / 16;
				int aa = i & 0x89;
				if(aa == 0x80 || aa == 0)
				{
					g.drawImage(ii, 
							x * 32, y * 32, x * 32 + 16, y * 32 +16, 
							0 * 32, 1 * 32, 0 * 32 + 16, 1 * 32 + 16, null);
				}
				else if(aa == 0x09)
				{
					g.drawImage(ii, 
							x * 32, y * 32, x * 32 + 16, y * 32 +16, 
							1 * 32, 0 * 32, 1 * 32 + 16, 0 * 32 + 16, null);
				}
				else if(aa == 0x01 || aa == 0x81)
				{
					g.drawImage(ii, 
							x * 32, y * 32, x * 32 + 16, y * 32 +16, 
							0 * 32, 2 * 32, 0 * 32 + 16, 2 * 32 + 16, null);
				}
				else if(aa == 0x08 || aa == 0x88)
				{	
					g.drawImage(ii, 
							x * 32, y * 32, x * 32 + 16, y * 32 +16, 
							1 * 32, 1 * 32, 1 * 32 + 16, 1 * 32 + 16, null);
				}
				else
				{
					g.drawImage(ii, 
							x * 32, y * 32, x * 32 + 16, y * 32 +16, 
							1 * 32, 2 * 32, 1 * 32 + 16, 2 * 32 + 16, null);
				}
				
				int ba = i & 0x13;
				if(ba == 0x10 || ba == 0)
				{
					g.drawImage(ii, 
							x * 32 + 16, y * 32, x * 32 + 32, y * 32 + 16, 
							1 * 32 + 16, 1 * 32, 1 * 32 + 32, 1 * 32 + 16, null);
				}
				else if(ba == 0x03)
				{
					g.drawImage(ii, 
							x * 32 + 16, y * 32, x * 32 + 32, y * 32 + 16, 
							1 * 32 + 16, 0 * 32, 1 * 32 + 32, 0 * 32 + 16, null);
				}
				else if(ba == 0x01 || ba == 0x11)
				{
					g.drawImage(ii, 
							x * 32 + 16, y * 32, x * 32 + 32, y * 32 + 16, 
							1 * 32 + 16, 2 * 32, 1 * 32 + 32, 2 * 32 + 16, null);
				}
				else if(ba == 0x02 || ba == 0x12)
				{	
					g.drawImage(ii, 
							x * 32 + 16, y * 32, x * 32 + 32, y * 32 + 16, 
							0 * 32 + 16, 1 * 32, 0 * 32 + 32, 1 * 32 + 16, null);
				}
				else
				{
					g.drawImage(ii, 
							x * 32 + 16, y * 32, x * 32 + 32, y * 32 + 16, 
							0 * 32 + 16, 2 * 32, 0 * 32 + 32, 2 * 32 + 16, null);
				}

				int bb = i & 0x26;
				if(bb == 0x20 || bb == 0)
				{
					g.drawImage(ii, 
							x * 32 + 16, y * 32 + 16, x * 32 + 32, y * 32 + 32, 
							1 * 32 + 16, 2 * 32 + 16, 1 * 32 + 32, 2 * 32 + 32, null);
				}
				else if(bb == 0x06)
				{
					g.drawImage(ii, 
							x * 32 + 16, y * 32 + 16, x * 32 + 32, y * 32 + 32, 
							1 * 32 + 16, 0 * 32 + 16, 1 * 32 + 32, 0 * 32 + 32, null);
				}
				else if(bb == 0x04 || bb == 0x24)
				{
					g.drawImage(ii, 
							x * 32 + 16, y * 32 + 16, x * 32 + 32, y * 32 + 32, 
							1 * 32 + 16, 1 * 32 + 16, 1 * 32 + 32, 1 * 32 + 32, null);
				}
				else if(bb == 0x02 || bb == 0x22)
				{	
					g.drawImage(ii, 
							x * 32 + 16, y * 32 + 16, x * 32 + 32, y * 32 + 32, 
							0 * 32 + 16, 2 * 32 + 16, 0 * 32 + 32, 2 * 32 + 32, null);
				}
				else
				{
					g.drawImage(ii, 
							x * 32 + 16, y * 32 + 16, x * 32 + 32, y * 32 + 32, 
							0 * 32 + 16, 1 * 32 + 16, 0 * 32 + 32, 1 * 32 + 32, null);
				}
				
				int ab = i & 0x4c;
				if(ab == 0x40 || ab == 0)
				{
					g.drawImage(ii, 
							x * 32, y * 32 + 16, x * 32 + 16, y * 32 + 32, 
							0 * 32, 2 * 32 + 16, 0 * 32 + 16, 2 * 32 + 32, null);
				}
				else if(ab == 0x0c)
				{
					g.drawImage(ii, 
							x * 32, y * 32 + 16, x * 32 + 16, y * 32 + 32, 
							1 * 32, 0 * 32 + 16, 1 * 32 + 16, 0 * 32 + 32, null);
				}
				else if(ab == 0x04 || ab == 0x44)
				{
					g.drawImage(ii, 
							x * 32, y * 32 + 16, x * 32 + 16, y * 32 + 32, 
							0 * 32, 1 * 32 + 16, 0 * 32 + 16, 1 * 32 + 32, null);
				}
				else if(ab == 0x08 || ab == 0x48)
				{
					g.drawImage(ii, 
							x * 32, y * 32 + 16, x * 32 + 16, y * 32 + 32, 
							1 * 32, 2 * 32 + 16, 1 * 32 + 16, 2 * 32 + 32, null);
				}
				else
				{
					g.drawImage(ii, 
							x * 32, y * 32 + 16, x * 32 + 16, y * 32 + 32, 
							1 * 32, 1 * 32 + 16, 1 * 32 + 16, 1 * 32 + 32, null);
				}
			}
		g.dispose();
		return image;
	}
	
	/**
	 * Créer une texture a partir d'un BufferedImage
	 * @param image BufferedImage a convertir en texture opengl
	 * @param nearest Defini le lissage de la texture
	 */
	public Texture(BufferedImage image, boolean nearest)
	{
		width = image.getWidth();
		height = image.getHeight();
		pixels = new int[width*height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		
		int[] data = new int[width*height];
		for(int i=0; i<data.length; i++) 
		{
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		int id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);

		if (nearest)
		{
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		}
		else
		{
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		}

		IntBuffer buffer = BufferAlloc.createIntBuffer(data);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		this.index = id;
		glBindTexture(GL_TEXTURE_2D, 0);
		MemoryClass.addClass(this);
	}

	/**
	 * @return retourne l'index de la texture.
	 */
	public int getIndex()
	{
		return (index);
	}
	
	/**
	 * Active l'utilisation de la textures.
	 */
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, index);
	}

	/**
	 * Desactive l'utilisation des textures.
	 */
	public static void unbind()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	/**
	 * Detruit la textures.
	 */
	public void dispose()
	{
		glDeleteTextures(index);
	}

	/**
	 * @return Largeur de la texture.
	 */
	public int getWidth()
	{
		return (width);
	}
	
	/**
	 * @return Hauteur de la texture.
	 */
	public int getHeight()
	{
		return (height);
	}
}
