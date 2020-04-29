/*
 * Filename:    Statistician.java
 * Author:      Joshua Carney
 * Course:      CSCI 162 Section 1
 * Date:        02/17/2020
 * Assignment:  Assignment 4
 * Description: Create a statistician class that keeps certain stats about numbers.
 */

/******************************************************************************
 * A Statistician keeps track of statistics about a sequence of double numbers.
 * Outline of Java Source Code for this class was obtained from:
 *   http://www.cs.colorado.edu/~main/edu/colorado/homework/Statistician.java
 *   
 * Beth Katz modified it to use different method names and extra methods
 * January 2007 and January 2008
 * The student(s) listed below implemented it.
 * 
 * @author Joshua Carney
 * 
 ******************************************************************************/

public class Statistician implements Cloneable {  
	/****************************
	 * class invariant:
	 * - resetting the statistician resets all values here
	 * - these values are computed since the most recent reset
	 * - sumOfValues contains the sum of all values entered (or 0)
	 * - sumOfValues may have a value signifying arithmetic errors
	 *        Double.POSITIVE_INFINITY or Double.NEGATIVE_INFINITY
	 * - count contains the number of values (or 0) but may contain
	 *        Integer.MAX_VALUE if too many values are entered
	 * - smallestValue contains smallest value entered (or 0)
	 * - largestValue contains largest value entered (or 0)
	 */
	private double sumOfValues;
	private int count;
	private double smallestValue;
	private double largestValue;

	/**
	 * Initialize a new Statistician.  
	 * @param none
	 * @postcondition
	 *   This Statistician is newly initialized and has not yet been 
	 *   given any numbers.
	 **/   
	public Statistician() {
		this.reset();
	}
	
	/**
	 * Reset this Statistician. 
	 * @param none
	 * @postcondition
	 *   This Statistician is reinitialized as if it has never been 
	 *   given any numbers.
	 **/
	public void reset() {
		sumOfValues = 0.0;
		count = 0;
		smallestValue = Double.NaN;
		largestValue = Double.NaN;
	}
	
	/**
	 * Returns a separate copy of this Statistician that will appear
	 * to be indistinguishable from the original but separate
	 * @postcondition
	 *   The returned Statistician is a separate copy of this Statistician
	 */
	public Statistician clone() {	
		try {
			Statistician trial = (Statistician) super.clone();
			return trial;
		}
		catch(CloneNotSupportedException e) {
			throw new RuntimeException("Cloneable not implemented");
		}
	}

	/**
	 * Give a new number to this Statistician. 
	 * @param number
	 *   the new number that is being given to this Statistician
	 * @postcondition
	 *   The specified number has been given to this Statistician 
	 *   and it will be included in any subsequent statistics.
	 **/
	public void insert(double number) {
		sumOfValues += number;
		++count;
		if(number < smallestValue || Double.isNaN(smallestValue)) {
			smallestValue = number;
		}
		if(number > largestValue || Double.isNaN(largestValue)){
			largestValue = number;
		}
	}

	/**
	 * Compare this Statistician to another object for equality.
	 * @param obj
	 *   an object with which this Statistician will be compared
	 * @return
	 *   A return value of true indicates that obj refers to a Statistican 
	 *   object with the same length, sum, mean, largest and smallest as 
	 *   this Statistician. Otherwise the return value is false.
	 * Note:
	 *   If obj is null or does not refer to a Statistician object, 
	 *   then the answer is false.
	 **/   
	public boolean equals(Object obj) {
		if (!(obj instanceof Statistician)) {
			return false;
		}
		Statistician s = (Statistician) obj;
		
		return this.length() == s.length() && this.sum() == s.sum() && this.mean() == s.mean()
				&& this.largest() == s.largest() && this.smallest() == s.smallest();
	} 


	/**
	 * Determine how many numbers have been given to this Statistician.
	 * @param none
	 * @return
	 *   count of how many numbers have been given to this Statistician
	 *   since it was initialized or reinitialized.
	 * Note:
	 *   Giving a Statistician more than Integer.MAX_VALUE numbers, 
	 *   will cause failure with an arithmetic overflow.
	 **/ 
	public int length() {
		return count;
	}

	/**
	 * Determine the sum of all the numbers that have been given to this 
	 * Statistician.
	 * @param none
	 * @return
	 *   the sum of all the number that have been given to this 
	 *   Statistician since it was initialized or reinitialized.
	 * Note:
	 *   If the sum exceeds the bounds of double numbers, then the answer
	 *   from this method may be Double.POSITIVE_INFINITY or
	 *   Double.NEGATIVE_INFINITY.
	 **/ 
	public double sum() {
		return sumOfValues;
	}


	/**
	 * Determine the arithmetic average of all the numbers that have been 
	 * given to this Statistician.
	 * @param none
	 * @return
	 *   the arithmetic mean of all the number that have been given to this 
	 *   Statistician since it was initialized or reinitialized.
	 * Note:
	 *   If this Statistician has been given more than Integer.MAX_VALUE 
	 *   numbers, then this method fails because of arithmetic overflow.
	 *   If length() is zero, then the answer is Double.NaN.
	 *   If sum() exceeds the bounds of double numbers, then the answer 
	 *   may be Double.POSITIVE_INFINITY or Double.NEGATIVE_INFINITY.
	 **/ 
	public double mean() {
		return sumOfValues / count;
	}


	/**
	 * Determine smallest number that has been given to this Statistician.
	 * @param none
	 * @return
	 *   the smallest number that has been given to this Statistician
	 *   since it was initialized or reinitialized.
	 * Note:
	 *   If length() is zero, then the answer is Double.NaN.
	 **/ 
	public double smallest() {
		return smallestValue;
	}


	/**
	 * Determine largest number that has been given to this Statistician.
	 * @param none
	 * @return
	 *   the largest number that has been given to this Statistician
	 *   since it was initialized or reinitialized.
	 * Note:
	 *   If length() is zero, then the answer is Double.NaN.
	 **/ 
	public double largest() {
		return largestValue;
	}


	/**
	 * Add the numbers of another Statistician (addend) to this Statistician.
	 * @param addend
	 *   a Statistician whose numbers will be added to this Statistician
	 * @precondition
	 *   The parameter, addend, is not null. 
	 * @postcondition
	 *   The numbers from addend have been added to this Statistician. 
	 *   After the operation, this Statistician acts as if it were given 
	 *   all of its numbers and also given all of the numbers from the 
	 *   addend.
	 * @exception NullPointerException
	 *   Indicates that addend is null.
	 **/
	public void add(Statistician addend) {
		this.sumOfValues += addend.sumOfValues;
		this.count += addend.count;
		
		if(addend.smallestValue < this.smallestValue || Double.isNaN(this.smallestValue)) this.smallestValue = addend.smallestValue;
		if(addend.largestValue > this.largestValue || Double.isNaN(this.largestValue)) this.largestValue = addend.largestValue;
	}   

	/**
	 * Create a new Statistician that behaves as if it was given all 
	 * the numbers from this and the other Statistician 
	 * @param other
	 *   an existing Statistician
	 * @precondition
	 *   Neither this nor the other Statistician is null.
	 * @return
	 *   a new Statistician that acts as if it was given all the 
	 *   numbers from this Statistician and the other Statistician 
	 * @exception NullPointerException.
	 *   Indicates that the argument is null.
	 **/   
	public Statistician union(Statistician other) {
		Statistician unionStat = new Statistician();
		
		unionStat.count = this.count + other.count;
		unionStat.sumOfValues = this.sumOfValues + other.sumOfValues;
		
		if(this.smallestValue < other.smallestValue) { 
			unionStat.smallestValue = this.smallestValue;
		} else {
			unionStat.smallestValue = other.smallestValue;
		}
		
		if(this.largestValue > other.largestValue) { 
			unionStat.largestValue = this.largestValue;
		} else {
			unionStat.largestValue = other.largestValue;
		}
		
		return unionStat;
	}

}