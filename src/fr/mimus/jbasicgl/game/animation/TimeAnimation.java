package fr.mimus.jbasicgl.game.animation;

/**
 * @author Mimus
 * @version 1.1
 */
public abstract class TimeAnimation extends Animation
{
	protected double time = 0;
	public void update(int tick, double elapse)
	{
		time += elapse;
		Frame f = this.getFrame();
		if (f != null && time >= f.getNextTime())
		{
			time -= f.getNextTime();
			setCurrentFrame(f.getNextFrame());
		}
	}
	
	public void defaultAnimation4x4(double timeToFrame)
	{
		frames.add(new Frame(0, timeToFrame));
		frames.add(new Frame(1, timeToFrame));
		frames.add(new Frame(2, timeToFrame));
		frames.add(new Frame(3, timeToFrame, 0));
		frames.add(new Frame(4, timeToFrame));
		frames.add(new Frame(5, timeToFrame));
		frames.add(new Frame(6, timeToFrame));
		frames.add(new Frame(7, timeToFrame, 4));
		frames.add(new Frame(8, timeToFrame));
		frames.add(new Frame(9, timeToFrame));
		frames.add(new Frame(10, timeToFrame));
		frames.add(new Frame(11, timeToFrame, 8));
		frames.add(new Frame(12, timeToFrame));
		frames.add(new Frame(13, timeToFrame));
		frames.add(new Frame(14, timeToFrame));
		frames.add(new Frame(15, timeToFrame, 12));
	}
}
