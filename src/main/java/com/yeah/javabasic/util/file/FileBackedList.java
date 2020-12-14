package com.yeah.javabasic.util.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 常规的List（比如ArrayList）都是在内存中开辟存储空间，将数据存放在内存中。当数据量很大时，可能导致OOM，
 * 这里实现了基于文件的 List，字符串格式的数据会被保存到文件中，通过对外提供 get、add方法，屏蔽内部细节，
 * 可以像使用普通 List一样使用该类。
 * <p>
 * 实现要点：
 * <ul>
 * <li>LinkedHashMap 的 removeEldestEntry 方法，自动删除最老的数据</li>
 * <li>每次add时，字符串长度也被写入文件</li>
 * <li>nio的channel、buffer</li>
 * </ul>
 * File-backed list-like class. Allows addition of arbitrary
 * numbers of array entries (serialized to JSON) in a binary
 * packed file. Reading of entries is done with an NIO
 * channel that seeks to the entry in the file.
 * <p>
 * File entry format:
 * <ul>
 * <li>4 bytes: length of entry</li>
 * <li><i>length</i> bytes: JSON string containing the entry data</li>
 * </ul>
 * <p>
 * Pointers to the offset of each entry are kept in a {@code List<Long>}.
 * The values loaded from the the file are cached up to a maximum of
 * {@code cacheSize}. Items are evicted from the cache with an LRU algorithm.
 */
public class FileBackedList implements AutoCloseable {

  private final List<Long> pointers = new ArrayList<>();
  private final RandomAccessFile raf;
  private final FileChannel channel;
  private final Map<Integer, String> cache;

  private long filesize;

  @SuppressWarnings("serial")
  public FileBackedList(File file, final int cacheSize) throws IOException {
    this.raf = new RandomAccessFile(file, "rw");
    this.channel = raf.getChannel();
    this.filesize = raf.length();
    this.cache = new LinkedHashMap<Integer, String>(cacheSize, 0.75f, true) {
      public boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
        return size() > cacheSize;
      }
    };
  }

  public void add(String str) {
    try {
      if (str!=null && str.length()>0) {
        writeToFile(str);
      }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getAt(int index) {
    if(cache.containsKey(index)) {
      return cache.get(index);
    }

    try {
      String val = readFromFile(pointers.get(index));
      cache.put(index, val);
      return val;
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void writeToFile(String str) throws IOException {
    synchronized (channel) {
      ByteBuffer bytes = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
      // 字符串的长度
      ByteBuffer length = ByteBuffer.allocate(4).putInt(bytes.array().length);

      channel.position(filesize);
      pointers.add(channel.position());
      length.flip();
      channel.write(length);
      channel.write(bytes);

      filesize += 4 + bytes.array().length;
    }
  }

  private String readFromFile(long pointer) throws IOException {
    synchronized (channel) {
      FileChannel fc = channel.position(pointer);

      //get length of entry
      ByteBuffer buffer = ByteBuffer.wrap(new byte[4]);
      fc.read(buffer);
      buffer.flip();
      int length = buffer.getInt();

      //read entry
      buffer = ByteBuffer.wrap(new byte[length]);
      fc.read(buffer);
      buffer.flip();

      return new String(buffer.array(), StandardCharsets.UTF_8);
    }
  }

  @Override
  public void close() {
    try {
      raf.close();
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static void main(String[] args) throws IOException {
	  FileBackedList fileList = new FileBackedList(new File("C:\\Users\\techsure\\Desktop\\任务清单.txt"), 2);
	  for (int i = 0; i < 10; i++) {
		  fileList.add("Test FileBackedList>>>>>>>>>>>" + i);
		  
		  System.out.println(fileList.getAt(i));
	  }
	  
	  fileList.close();
  }
}