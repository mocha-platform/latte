/**
 * 
 */
package com.mocha.ehcache.bigmemory;

import java.util.List;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Direction;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import net.sf.ehcache.search.aggregator.Aggregators;

public class Group {
	public static void main(final String[] args) {

		// Create a cache manager using the factory method...
		final CacheManager cacheManager = CacheManager.newInstance(Group.class
				.getResource("/ehcache-group.xml"));
		// Retrieve the "sort" data set...
		final Cache dataSet = cacheManager.getCache("group");
		// Create some objects...
		final Person janeAusten = new Person(1, "Jane", "Austen", "Female",
				5 * 12 + 7, 130);
		final Person charlesDickens = new Person(2, "Charles", "Dickens",
				"Male", 5 * 12 + 9, 160);
		final Person janeDoe = new Person(3, "Jane", "Doe", "Female",
				5 * 12 + 5, 145);
		final Person alexSmith = new Person(4, "Alex", "Smith", "Other",
				5 * 12 + 5, 160);
		// Put the objects into the data set...
		dataSet.put(new Element(janeAusten.getId(), janeAusten));
		dataSet.put(new Element(charlesDickens.getId(), charlesDickens));
		dataSet.put(new Element(janeDoe.getId(), janeDoe));
		dataSet.put(new Element(alexSmith.getId(), alexSmith));
		// Fetch the search attributes that we'll use in our query and when
		// fetching
		// our results...
		Attribute<Integer> height = dataSet.getSearchAttribute("height");
		Attribute<String> gender = dataSet.getSearchAttribute("gender");
		Attribute<Float> bmi = dataSet.getSearchAttribute("bmi");
		// Create a query object. (This query is designed to select the entire
		// data
		// set.)
		final Query query = dataSet.createQuery()
				.addCriteria(height.gt(5 * 12));
		// Group by the gender attribute so we can perform aggregations on each
		// gender...
		query.addGroupBy(gender);
		query.includeAttribute(gender);
		// Include an aggregation of the average height and bmi of each gender
		// in
		// the results...
		query.includeAggregator(Aggregators.average(height));
		query.includeAggregator(Aggregators.average(bmi));
		// Include the count of the members of each gender
		query.includeAggregator(Aggregators.count());
		// Make the results come back sorted by gender in alphabetical order
		query.addOrderBy(gender, Direction.ASCENDING);
		// Execute the query...
		final Results results = query.execute();
		// Print the results...
		for (Result result : results.all()) {
			String theGender = result.getAttribute(gender);
			List<Object> aggregatorResults = result.getAggregatorResults();
			Float avgHeight = (Float) aggregatorResults.get(0);
			Float avgBMI = (Float) aggregatorResults.get(1);
			Integer count = (Integer) aggregatorResults.get(2);
			System.out.println("Gender: " + theGender + "; count: " + count
					+ "; average height: " + avgHeight + "; average BMI: "
					+ avgBMI);
		}
	}

	public static class Person {
		private final String firstName;
		private final String lastName;
		private final long id;
		private final int height;
		private final int weight;
		private String gender;

		public Person(final long id, final String firstName,
				final String lastName, final String gender, final int height,
				final int weight) {
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.gender = gender;
			this.height = height;
			this.weight = weight;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public String getGender() {
			return gender;
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