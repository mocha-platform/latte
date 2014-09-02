/**
 * 
 */
package com.mocha.ehcache.bigmemory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CrudTransactional {

	public static void main(String[] args) {
		// Create a cache manager using the factory method
		// AND specify the new configuration file
		final CacheManager cacheManager = CacheManager.newInstance(Crud.class
				.getResource("/ehcache-crud.xml"));
		// Get the "crud" cache from the cache manager...
		final Cache dataStore = cacheManager.getCache("crud");
		final String myKey = "My Key";
		final String myData = "My Data";
		try {
			cacheManager.getTransactionController().begin();
			// Set up the first data element...
			Element readElement = dataStore.get(myKey);
			System.out.println("Read data: " + readElement);
			final Element createElement = new Element(myKey, myData);
			// CREATE data using the put(Element) method...
			dataStore.put(createElement);
			System.out.println("Created data: " + createElement);
			readElement = dataStore.get(myKey);
			System.out.println("Read data: " + readElement);
			showException();
			cacheManager.getTransactionController().commit();
		} catch (Exception e) {
			cacheManager.getTransactionController().rollback();
		}

		// READ data using the get(Object) method...
		try {
			cacheManager.getTransactionController().begin();
			Element readElement = dataStore.get(myKey);
			System.out.println("Read data: " + readElement);
			// Check to make sure the data is the same...
			if (!myData.equals(readElement.getObjectValue())) {
				throw new RuntimeException("My data doesn't match!");
			}

			// UPDATE data by mapping a new value to the same key...
			final String myNewData = "My New Data";
			final Element updateElement = new Element(myKey, myNewData);
			dataStore.put(updateElement);
			System.out.println("Updated data: " + updateElement);
			// Test to see that the data is updated...
			readElement = dataStore.get(myKey);
			if (!myNewData.equals(readElement.getObjectValue())) {
				throw new RuntimeException("My data doesn't match!");
			}
			// DELETE data using the remove(Object) method...
			final boolean wasRemoved = dataStore.remove(myKey);
			System.out.println("Removed data: " + wasRemoved);
			if (!wasRemoved) {
				throw new RuntimeException("My data wasn't removed!");
			}
			cacheManager.getTransactionController().commit();
		} catch (Exception e) {
			cacheManager.getTransactionController().rollback();
		}
		// Be polite and release the CacheManager resources...
		cacheManager.shutdown();
	}

	private static void showException() {
		String a = null;
		a = a.substring(1, 10);
	}
}
