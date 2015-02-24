package org.javacoo.crawler.core.data;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

/** 
 * <strong>扩展<tt>hash</tt>算法</strong> 
 * <p> 
 * 基本思想：在内存里面开辟一个区域，所有位置为零，然后 用不同的hash算法对要存入的数据计算hash值，每个hash 
 * 值对内存位数求模，得到的数在内存位置上值1，置位之前需要现 判断是否已经置位，对于插入的值，所有的位置都是1时， 才认为 是重复 
 * </p> 
 */  
public class SimpleBloomFilter<T> {
	private static int defaultSize = 2 << 24;// 2的24次方。  
	  
    private static int basic = defaultSize - 1;  
  
    private static BitSet bits;// 内存置位  
    
    private AtomicInteger size = new AtomicInteger(0);
  
    static int check(int h) {  
        return basic & h;  
    }  
  
    static int hash(int h) {  
        h ^= (h >>> 20) ^ (h >>> 12);  
        return h ^ (h >>> 7) ^ (h >>> 4);  
    }  
  
    public SimpleBloomFilter() {  
        bits = new BitSet(defaultSize);  
    }  
  
    public boolean contains(T t) {  
        if (t == null) {  
            return true;  
        }  
        int pos1 = check(hash(t.hashCode() + 2));  
        int pos2 = check(hash(t.hashCode() + 4));  
        int pos3 = check(hash(t.hashCode() + 6));  
        if (bits.get(pos1) && bits.get(pos2) && bits.get(pos3)) {  
            return true;  
        }  
        return false;  
    }  
  
    public void add(T t) {  
        if (t == null) {  
            return;  
        }  
        int pos1 = check(hash(t.hashCode() + 2));  
        int pos2 = check(hash(t.hashCode() + 4));  
        int pos3 = check(hash(t.hashCode() + 6));  
        bits.set(pos1);  
        bits.set(pos2);  
        bits.set(pos3);  
        size.incrementAndGet();
    }  
    
    
  
    public int getSize() {
		return size.get();
	}

	
}
