package com.mocha.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheUsage {

	public static void main(String[] args) {
		CacheManager manager = CacheManager.newInstance("src/main/resources/ehcache.xml");  
//		manager.addCache("testCache");  
		Cache test = manager.getCache("test");  
		Element e = test.get("key1");
		if(e == null) {
			test.put(new Element("key1", "value1"));
		} else {		
			System.out.println(e.getObjectValue());
		}
		manager.shutdown(); 
	}

}
