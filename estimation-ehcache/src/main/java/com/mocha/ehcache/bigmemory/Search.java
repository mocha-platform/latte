package com.mocha.ehcache.bigmemory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

public class Search {
	public static void main(final String[] args) {
		// Create a cache manager using the factory method...
		final CacheManager cacheManager = CacheManager.newInstance(Crud.class
				.getResource("/ehcache-search.xml"));
		// Retrieve the "search" data set...
		final Cache dataSet = cacheManager.getCache("search");
		// Create some searchable objects...
		final Person janeAusten = new Person(1, "Jane", "Austen", 5 * 12 + 7,
				130);
		final Person charlesDickens = new Person(2, "Charles", "Dickens",
				5 * 12 + 9, 160);
		final Person janeDoe = new Person(3, "Jane", "Doe", 5 * 12 + 5, 145);

		// Put the searchable objects into the data set...
		dataSet.put(new Element(janeAusten.getId(), janeAusten));
		dataSet.put(new Element(charlesDickens.getId(), charlesDickens));
		dataSet.put(new Element(janeDoe.getId(), janeDoe));
		// Fetch the Body Mass Index (BMI) attribute...
		Attribute<Object> bmi = dataSet.getSearchAttribute("bodyMassIndex");
		// Create a query for all people with a BMI greater than 23...
		final Query query = dataSet.createQuery().addCriteria(bmi.gt(23F))
				.includeValues();
		// Execute the query...
		final Results results = query.execute();
		// Print the results...
		for (Result result : results.all()) {
			System.out.println(result.getValue());
		}
	}

	public static class Person {
		private final String firstName;
		private final String lastName;
		private final long id;
		private final int height;
		private final int weight;

		public Person(long id, final String firstName, final String lastName,
				final int height, final int weight) {
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.height = height;
			this.weight = weight;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public int getHeight() {
			return height;
		}

		public int getWeight() {
			return weight;
		}

		public float getBodyMassIndex() {
			return ((float) weight / ((float) height * (float) height)) * 703;
		}

		public long getId() {
			return id;
		}

		public String toString() {
			return "[id=" + id + ", firstName=" + firstName + ", lastName="
					+ lastName + ", height=" + height + " in" + ", weight="
					+ weight + " lbs" + ", bmi=" + getBodyMassIndex() + "]";
		}
	}
}