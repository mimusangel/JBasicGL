package fr.mimus.jbasicgl.game.animation;

import java.util.ArrayList;

/**
 * @author Mimus
 * @version 1.1
 */
public abstract class Animation
{
	public class Frame
	{
		protected int id;
		protected double nextTime;
		protected int nextFrame;
		
		public Frame(int id, double nextTime, int nextFrame)
		{
			this.id = id;
			this.nextTime = nextTime;
			this.nextFrame = nextFrame;
		}
		
		public Frame(int frame, double nextTime)
		{
			this(frame, nextTime, frame + 1);
		}
		
		public int getID()
		{
			return id;
		}
		
		public double getNextTime()
		{
			return nextTime;
		}
		
		public int getNextFrame()
		{
			return nextFrame;
		}
	}
	
	protected int frame;
	protected ArrayList<Frame> frames;
	public Animation()
	{
		this.frame = 0;
		frames = new ArrayList<Frame>();
	}
	
	public abstract void init();
	public abstract void update(int tick, double elapse);
	
	public Frame getFrame()
	{
		return (frames.get(this.frame));
	}
	
	public int getCurrentFrame()
	{
		return (this.frame);
	}
	
	public Animation setCurrentFrame(int frame)
	{
		this.frame = (frame % frames.size());
		return (this);
	}

}
