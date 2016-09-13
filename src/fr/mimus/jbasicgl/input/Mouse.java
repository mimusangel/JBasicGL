package fr.mimus.jbasicgl.input;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

import fr.mimus.jbasicgl.maths.Vec2;

public class Mouse
{
	public final static int MOUSE_BUTTON_1		= 0;
	public final static int MOUSE_BUTTON_2		= 1;
	public final static int MOUSE_BUTTON_3		= 2;
	public final static int MOUSE_BUTTON_4		= 3;
	public final static int MOUSE_BUTTON_5		= 4;
	public final static int MOUSE_BUTTON_6		= 5;
	public final static int MOUSE_BUTTON_7		= 6;
	public final static int MOUSE_BUTTON_8		= 7;
	public final static int MOUSE_BUTTON_LAST	= MOUSE_BUTTON_8;
	public final static int MOUSE_BUTTON_LEFT	= MOUSE_BUTTON_1;
	public final static int MOUSE_BUTTON_RIGHT	= MOUSE_BUTTON_2;
	public final static int MOUSE_BUTTON_MIDDLE	= MOUSE_BUTTON_3;
	
	private final MouseCursorPos		mousePosCallBack;
    private final MouseCursorButton		mouseButtonCallBack;
    private final MouseWheel			mouseWheelCallBack;
    private long						window;
    
    public Mouse(long window)
    {
    	this.window = window;
    	mousePosCallBack = new MouseCursorPos();
        mouseButtonCallBack = new MouseCursorButton();
        mouseWheelCallBack = new MouseWheel();
        
        glfwSetCursorPosCallback(window, mousePosCallBack);
        glfwSetMouseButtonCallback(window, mouseButtonCallBack);
        glfwSetScrollCallback(window, mouseWheelCallBack);
    }
    
    /**
     * Savoir si un bouton de la souris est appuyer.
     * @param button bouton de la souris
     * @return true si le bouton est appuyer, sinon false
     */
    public boolean isDown(int button)
	{
    	return (mouseButtonCallBack.isDown(button));
	}
    
    /**
     * @return Connetre le sens de rotation de l'axe X du scroll
     */
    public double getWheelX()
	{
		return (mouseWheelCallBack.getWheelX());
	}
    
    /**
     * @return Connetre le sens de rotation de l'axe Y du scroll
     */
	public double getWheelY()
	{
		return (mouseWheelCallBack.getWheelY());
	}
    
	/**
	 * Donne les Coordonnée X d'une souris
	 * @return Coordonnée X
	 */
	public double getX()
	{
		return (mousePosCallBack.getX());
	}
	
	public double getX(double scale)
	{
		return (mousePosCallBack.getX() / scale);
	}

	/**
	 * Donne les Coordonnée Y d'une souris
	 * @return Coordonnée Y
	 */
	public double getY()
	{
		return (mousePosCallBack.getY());
	}
	
	public double getY(double scale)
	{
		return (mousePosCallBack.getY() / scale);
	}
	
	public Vec2 getPos()
	{
		return (new Vec2((float)getX(), (float)getY()));
	}
	
	public Vec2 getPos(float scaleX, float scaleY)
	{
		return (new Vec2((float)getX() / scaleX, (float)getY() / scaleY));
	}
	
	public Vec2 getPos(Vec2 scale)
	{
		return getPos(scale.x, scale.y);
	}
	
	public double getDX()
	{
		return (mousePosCallBack.getDX());
	}
	
	public double getDY()
	{
		return (mousePosCallBack.getDY());
	}
	
    public void destroy()
    {
    	mousePosCallBack.release();
    	mouseButtonCallBack.release();
    	mouseWheelCallBack.release();
    }

	public boolean isGrabbed() {
		return (mousePosCallBack.isGrabbed());
	}

	public void setGrabbed(boolean grabbed) {
		mousePosCallBack.setGrabbed(this.window, grabbed);
	}	
}
