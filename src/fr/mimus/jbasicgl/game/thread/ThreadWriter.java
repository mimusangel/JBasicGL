package fr.mimus.jbasicgl.game.thread;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadWriter extends Thread
{
	Queue<IWritable> write;
	int total;
	IWritable w;
	
	public ThreadWriter()
	{
		total = 0;
		write = new ConcurrentLinkedQueue<IWritable>();
	}
	
	public void add(IWritable w)
	{
		write.offer(w);
	}
	
	public void run()
	{
		total = write.size();
		while ((w = write.poll()) != null)
			w.write();
	}
	
	public float getStatus()
	{
		if (total == 0)
			return (-1);
		float count = total - write.size();
		if (w != null)
			count -= 1;
		if (count <= 0)
			return (0);
		return (int)Math.floor(count * 100f / (float)total);
	}
	
	public float getSubStatus()
	{
		if (w == null)
			return (-1);
		return w.getSaveStatus();
	}
}
