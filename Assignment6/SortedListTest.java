/*
  Filename   : SortedListTest.java
  Author     : Joshua Carney
  Course     : CSCI 162-01
  Assignment : Assignment 6
  Description: A JUnit 4 unit test for SortedList.java 
*/

import static org.junit.Assert.*;

import org.junit.Test;

public class SortedListTest {

	// Tests methods on an empty list to ensure proper behavior
	@Test
	public void testEmptyList() {
		SortedList s = new SortedList();
		
		assertEquals("List isn't empty", "[ ]", s.toString());
		assertEquals("Size doesn't equals 0", 0, s.size());
		assertEquals("Index should return -1", -1, s.find(10.4));
		assertTrue("removeAt doesn't return false for empty list", !s.removeAt(2));
	}

	// Test find method
	
	@Test
	public void testFindMethod() {
		SortedList s = new SortedList();
		
		s.insert(42.2);
		assertEquals("List size does not equal 1", 1, s.size());
		assertEquals("Index should return 0", 0, s.find(42.2));
		
		s.insert(101.44);
		s.insert(999.99);
		assertEquals("List size does not equal 3", 3, s.size());
		assertEquals("Index of 42.2 should stil be 0", 0, s.find(42.2));
		assertEquals("Index of 999.99 should be 2", 2, s.find(999.99));
		assertEquals("Index of 101.44 should be 1", 1, s.find(101.44));
		
		s.insert(-17.22);
		assertEquals("Index of -17.22 should be 0", 0, s.find(-17.22));
		
		s.removeAt(s.find(999.99));
		assertEquals("List removed 999.99 incorrectly after s.find(999.99)", "[ -17.22 42.2 101.44 ]", s.toString());
	}
	
	// General SortedList tests
	
	@Test
	public void sortedListTest() {
		SortedList s1 = new SortedList();
		
		assertEquals("List isn't empty", "[ ]", s1.toString());
		assertEquals("Size doesn't equals 0", 0, s1.size());
		
		s1.insert(0.0);
		s1.insert(-1.3);
		s1.insert(-30.22);
		s1.insert(-5.6);
		
		assertEquals("Negative numbers sorted incorrectly", "[ -30.22 -5.6 -1.3 0.0 ]", s1.toString());
		assertEquals("Index of -30.22 should be 0", 0, s1.find(-30.22));
	}
	
	// Test repeat numbers to see if SortedList can handle multiples of the same number
	// Also tests adding values to the head and tail of a list with values in it already
	
	@Test
	public void repeatListTest() {
		SortedList repeats = new SortedList();
		
		repeats.insert(10.1);
		repeats.insert(10.1);
		repeats.insert(10.1);
		repeats.insert(10.1);
		repeats.insert(10.1);
		
		System.out.println("Repeats List: " + repeats.toString());
		
		assertEquals("repeats list should contain five 10.1 double values", "[ 10.1 10.1 10.1 10.1 10.1 ]", repeats.toString());
		assertEquals("Size isn't 5", 5, repeats.size());
		
		repeats.insert(1.5);
		
		assertEquals("repeats list doesn't match expected value string after adding 1.5", "[ 1.5 10.1 10.1 10.1 10.1 10.1 ]", repeats.toString());
		System.out.println("Repeats List after adding 1.5: " + repeats.toString());
		
		repeats.insert(222.45);
		
		assertEquals("repeats list doesn't match expected value string after adding 222.45", "[ 1.5 10.1 10.1 10.1 10.1 10.1 222.45 ]", repeats.toString());
		System.out.println("Repeats List after adding 222.45: " + repeats.toString());
				
		assertEquals("find should simply return the first index of the number at index=1", 1, repeats.find(10.1));
		assertTrue("removeAt should return false for removeAt(100)", !repeats.removeAt(100));
		assertEquals("Size isn't 7", 7, repeats.size());
		
		repeats.removeAt(0);
		repeats.removeAt(repeats.find(222.45));
		
		assertEquals("Repeats list should contain five 10.1 double values after removing the 0th index", "[ 10.1 10.1 10.1 10.1 10.1 ]", repeats.toString());
		assertEquals("find should return 0", 0, repeats.find(10.1));
		assertEquals("find should return -1", -1, repeats.find(4.666));
		
		repeats.removeAt(0);
		repeats.removeAt(0);
		repeats.removeAt(0);
		repeats.removeAt(0);
		repeats.removeAt(0);
		
		assertEquals("List isn't empty", "[ ]", repeats.toString());
		assertEquals("Size doesn't equals 0", 0, repeats.size());
	}
}
