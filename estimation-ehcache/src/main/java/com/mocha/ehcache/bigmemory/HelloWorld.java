package com.mocha.ehcache.bigmemory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class HelloWorld {

	public static void main(String[] args) {
		// Create a cache manager
		final CacheManager cacheManager = new CacheManager();
		// create the data store called "hello-world"
		final Cache dataStore = cacheManager.getCache("hello-world");
		// create a key to map the data to
		final String key = "greeting";
		// Create a data element
		final Element putGreeting = new Element(key, "Hello, World!");
		// Put the element into the data store
		dataStore.put(putGreeting);
		// Retrieve the data element
		System.out.println(dataStore.get(key).getObjectValue());
	}

}
