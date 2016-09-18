package fr.mimus.jbasicgl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Gestion de buffer
 * @author Mimus
 * @version 1.1
 */
public class DataBuffer
{
	/*public static final int HALF_BYTE 	=	0xf;
	public static final int ONE_BYTE 	=	0xff;
	public static final int TWO_BYTE 	=	0xffff;
	public static final int THREE_BYTE 	= 	0xffffff;
	public static final int FOUR_BYTE 	= 	0x0fffffff;*/
	
	//private int writeID;
	private int readID;
	//private byte[] data;
	//private int dataSize;
	private ArrayList<Byte> data;
	
	public DataBuffer()
	{
		//this(TWO_BYTE);
		data = new ArrayList<Byte>();
		readID = 0;
	}
	
	/*public DataBuffer(int size) {
		dataSize = size;
		data = new byte[dataSize];
		writeID = 0;
		readID = 0;
	}*/
	
	
	public void seek(int pos)
	{
		readID = pos;
	}
	
	/*public void flip() {
		byte[] nData = new byte[writeID];
		for(int i = 0; i < writeID; i++) {
			nData[i] = data[i];
		}
		data = nData;
	}*/
	
	public void clear()
	{
		if(data != null)
		{
			/*for(int i = 0; i < data.length; i++) {
				data[i] = 0;
			}*/
			data.clear();
		}
		/*data = new byte[dataSize];
		writeID = 0;*/
		readID = 0;
	}
	
	/*public static DataBuffer setData(byte[] data) {
		DataBuffer db = new DataBuffer();
		for(int i = 0; i<data.length; i++) {
			db.put(data[i]);
		}
		return db;
	}*/
	
	public byte[] array()
	{
		byte[] array = new byte[data.size()];
		for(int i = 0; i < data.size(); i++)
			array[i] = data.get(i);
		return (array);
	}
	
	public int size()
	{
		return data.size();
	}
	
	public void put(byte value)
	{
		/*if(writeID >= data.length) {
			System.err.println("Write Overflow... " + writeID + "\n\tMax capacity: "+data.length);
			return;
		}
		data[writeID] = value;
		writeID++;*/
		data.add(value);
	}
	
	public void put(byte... values) {
		for(int i = 0; i < values.length; i++) {
			put(values[i]);
		}
	}
	
	public byte getByte() {
		/*if(readID >= data.length) {
			System.err.println("Read Overflow... " + readID + "\n\tMax capacity: "+data.length);
			return 0;
		}
		return data[readID++];*/
		if(readID >= data.size()) {
			System.err.println("Read Overflow... " + readID + "\n\tMax capacity: " + data.size());
			return 0;
		}
		return (data.get(readID));
	}
	
	public void put(short w) {
		put((byte) (w >> 8));
		put((byte) w);
	}
	
	public short getShort() {
		return ByteBuffer.wrap(new byte[] {getByte(), getByte()}).getShort();
	}
	
	public void put(int w) {
		put((byte) (w >> 24));
		put((byte) (w >> 16));
		put((byte) (w >> 8));
		put((byte) w);
	}
	
	public int getInt() {
		return ByteBuffer.wrap(new byte[] {getByte(), getByte(), getByte(), getByte()}).getInt();
	}

	public void put(long w) {
		put((byte) (w >> 56));
		put((byte) (w >> 48));
		put((byte) (w >> 40));
		put((byte) (w >> 32));
		put((byte) (w >> 24));
		put((byte) (w >> 16));
		put((byte) (w >> 8));
		put((byte) w);
	}
	

	public long getLong() {
		return ByteBuffer.wrap(new byte[] {getByte(), getByte(), getByte(), getByte(), getByte(), getByte(), getByte(), getByte()}).getLong();
	}
	
	public void put(float w) {
		put(Float.floatToIntBits(w));
	}
	
	public float getFloat() {
		return ByteBuffer.wrap(new byte[] {getByte(), getByte(), getByte(), getByte()}).getFloat();
	}
	
	public void put(double w) {
		put(Double.doubleToLongBits(w));
	}
	
	public double getDouble() {
		return ByteBuffer.wrap(new byte[] {getByte(), getByte(), getByte(), getByte(), getByte(), getByte(), getByte(), getByte()}).getDouble();
	}
	
	public void put(String w) {
		if (w.length() == 0)
		{
			put((int)0);
			return;
		}
		byte b[] = w.getBytes();
		put(b.length);
		put(b);
	}
	
	public String getString() {
		int len = getInt();
		if (len == 0)
			return (new String());
		byte[] b = new byte[len];
		for(int i=0; i<b.length; i++) {
			b[i] = getByte();
		}
		return new String(b);
	}
	
	public void write(String path) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(this.array());
		fos.close();
	}
	
	public static DataBuffer read(String path) throws IOException
	{
		File f = new File(path);
		if (f.exists())
		{
			byte array[] = new byte[(int) f.length()];
			FileInputStream fis = new FileInputStream(f);
			fis.read(array);
			fis.close();
			DataBuffer db = new DataBuffer();
			for(int i = 0; i < array.length; i++)
				db.data.add(array[i]);
			return (db);
		}
		return (null);
		
		/*FileInputStream fis = new FileInputStream(path);
		fis.read(data);
		fis.close();*/
	}
}