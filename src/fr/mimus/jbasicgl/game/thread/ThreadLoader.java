package fr.mimus.jbasicgl.game.thread;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadLoader extends Thread
{
	Queue<IReadable> read;
	int total;
	IReadable r;
	
	public ThreadLoader()
	{
		total = 0;
		read = new ConcurrentLinkedQueue<IReadable>();
	}
	
	public void add(IReadable r)
	{
		read.offer(r);
	}
	
	public void run()
	{
		total = read.size();
		while ((r = read.poll()) != null)
			r.read();
	}
	
	public float getStatus()
	{
		if (total == 0)
			return (-1);
		float count = total - read.size();
		if (r != null)
			count -= 1;
		if (count <= 0)
			return (0);
		return (int)Math.floor(count * 100f / (float)total);
	}
	
	public float getSubStatus()
	{
		if (r == null)
			return (-1);
		return r.getLoadStatus();
	}
}
