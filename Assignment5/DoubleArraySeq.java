/*
 * Filename:    DoubleArraySeq.java
 * Author:      Joshua Carney
 * Course:      CSCI 162 Section 1
 * Date:        02/27/2020
 * Assignment:  Assignment 5
 * Description: Create a data collection class, DoubleArraySeq
 */
import java.util.Arrays;

//File: DoubleArraySeq.java from the package edu.colorado.collections

//This is an assignment for students to complete after reading Chapter 3 of
//"Data Structures and Other Objects Using Java" by Michael Main.

/******************************************************************************
 * This class is a homework assignment; A DoubleArraySeq is a collection of
 * double numbers. The sequence can have a special "current element," which is
 * specified and accessed through four methods that are not available in the
 * sequence class (start, getCurrent, advance and isCurrent).
 *
 * @note (1) The capacity of a sequence can change after it's created, but the
 *       maximum capacity is limited by the amount of free memory on the
 *       machine. The constructor, addAfter, addBefore, clone, and concatenation
 *       will result in an OutOfMemoryError when free memory is exhausted.
 *       <p>
 *       (2) A sequence's capacity cannot exceed the maximum integer
 *       2,147,483,647 (Integer.MAX_VALUE). Any attempt to create a larger
 *       capacity results in a failure due to an arithmetic overflow.
 *
 * @note This file contains mostly blank implementations ("stubs") because this
 *       is a Programming Project for students.
 *
 * @see Unmodified older Java Source Code for this class <A HREF=
 *      "http://www.cs.colorado.edu/~main/edu/colorado/collections/DoubleArraySeq.java">
 *      (www.cs.colorado.edu/~main/edu/colorado/collections/DoubleArraySeq.java)
 *      </A>
 *
 * @version February 2008
 * 
 * @author Joshua Carney
 * 
 ******************************************************************************/
public class DoubleArraySeq implements Cloneable {
	// Invariant of the DoubleArraySeq class:
	// 1. The number of elements in the seqeunces is in the instance variable
	// manyItems.
	// 2. For an empty sequence (with no elements), we do not care what is
	// stored in any of data; for a non-empty sequence, the elements of the
	// sequence are stored in data[0] through data[manyItems-1], and we
	// don't care what's in the rest of data.
	// 3. If there is a current element, then it lies in data[currentIndex];
	// if there is no current element, then currentIndex equals manyItems.
	private double[] data;
	private int manyItems;
	private int currentIndex;

	private static final int DEFAULT_CAPACITY = 10;

	/**
	 * Initialize an empty sequence with an initial capacity of 10. Note that the
	 * addAfter and addBefore methods work efficiently (without needing more memory)
	 * until this capacity is reached.
	 * 
	 * @param - none
	 * @postcondition This sequence is empty and has an initial capacity of 10.
	 * @exception OutOfMemoryError Indicates insufficient memory for: new
	 *                             double[10].
	 **/
	public DoubleArraySeq() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Initialize an empty sequence with a specified initial capacity. Note that the
	 * addAfter and addBefore methods work efficiently (without needing more memory)
	 * until this capacity is reached.
	 * 
	 * @param initialCapacity the initial capacity of this sequence
	 * @precondition initialCapacity is non-negative.
	 * @postcondition This sequence is empty and has the given initial capacity.
	 * @exception IllegalArgumentException Indicates that initialCapacity is
	 *                                     negative.
	 * @exception OutOfMemoryError         Indicates insufficient memory for: new
	 *                                     double[initialCapacity].
	 **/
	public DoubleArraySeq(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("initialCapacity must be non-negative, not " + initialCapacity);
		}

		data = new double[initialCapacity];
		manyItems = 0;
		currentIndex = 0;
	}

	/**
	 * Add a new element to this sequence, after the current element. If the new
	 * element would take this sequence beyond its current capacity, then the
	 * capacity is increased before adding the new element.
	 * 
	 * @param element the new element that is being added
	 * @postcondition A new copy of the element has been added to this sequence. If
	 *                there was a current element, then the new element is placed
	 *                after the current element. If there was no current element,
	 *                then the new element is placed at the end of the sequence. In
	 *                all cases, the new element becomes the new current element of
	 *                this sequence.
	 * @exception OutOfMemoryError Indicates insufficient memory for increasing the
	 *                             sequence's capacity.
	 * @note An attempt to increase the capacity beyond Integer.MAX_VALUE will cause
	 *       the sequence to fail with an arithmetic overflow.
	 **/
	public void addAfter(double element) {
		if (size() - 1 == data.length)
			ensureCapacity(2 * size() + 1);

		// add to end of sequence if no current
		if (!isCurrent()) {
			data[manyItems++] = element;
			currentIndex = manyItems - 1;
		}
		// add after currentElement if current exists
		else {
			System.arraycopy(data, currentIndex + 1, data, currentIndex + 2, manyItems++ - currentIndex - 1);
			data[currentIndex + 1] = element;
			++currentIndex;
		}
	}

	/**
	 * Add a new element to this sequence, before the current element. If the new
	 * element would take this sequence beyond its current capacity, then the
	 * capacity is increased before adding the new element.
	 * 
	 * @param element the new element that is being added
	 * @postcondition A new copy of the element has been added to this sequence. If
	 *                there was a current element, then the new element is placed
	 *                before the current element. If there was no current element,
	 *                then the new element is placed at the start of the sequence.
	 *                In all cases, the new element becomes the new current element
	 *                of this sequence.
	 * @exception OutOfMemoryError Indicates insufficient memory for increasing the
	 *                             sequence's capacity.
	 * @note An attempt to increase the capacity beyond Integer.MAX_VALUE will cause
	 *       the sequence to fail with an arithmetic overflow.
	 **/
	public void addBefore(double element) {
		if (size() == data.length)
			ensureCapacity(2 * size() + 1);

		// add to beginning of sequence if no current
		if (!isCurrent()) {
			System.arraycopy(data, 0, data, 1, manyItems);
			data[0] = element;
			currentIndex = 0;
		}
		// add before CurrentElement if current exists
		else {
			System.arraycopy(data, currentIndex, data, currentIndex + 1, (manyItems - currentIndex));
			data[currentIndex] = element;
		}

		++manyItems;
	}

	/**
	 * Place the contents of another sequence at the end of this sequence.
	 * 
	 * @param addend a sequence whose contents will be placed at the end of this
	 *               sequence
	 * @precondition The parameter, addend, is not null.
	 * @postcondition The elements from addend have been placed at the end of this
	 *                sequence. The current element of this sequence remains where
	 *                it was, and the addend is also unchanged.
	 * @exception NullPointerException Indicates that addend is null.
	 * @exception OutOfMemoryError     Indicates insufficient memory to increase the
	 *                                 size of this sequence.
	 * @note An attempt to increase the capacity beyond Integer.MAX_VALUE will cause
	 *       an arithmetic overflow that will cause the sequence to fail.
	 **/
	public void addAll(DoubleArraySeq addend) {
		if (!isCurrent())
			currentIndex = size() + addend.size();
		ensureCapacity(size() + addend.size());
		System.arraycopy(addend.data, 0, this.data, manyItems, addend.size());
		manyItems += addend.size();
	}

	/**
	 * Move forward, so that the current element is now the next element in this
	 * sequence.
	 * 
	 * @param - none
	 * @precondition isCurrent() returns true.
	 * @postcondition If the current element was already the end element of this
	 *                sequence (with nothing after it), then there is no longer any
	 *                current element. Otherwise, the new element is the element
	 *                immediately after the original current element.
	 * @exception IllegalStateException Indicates that there is no current element,
	 *                                  so advance may not be called.
	 **/
	public void advance() {
		if (!isCurrent())
			throw new IllegalStateException("There is no current element so advance may not be called");
		++currentIndex;
	}

	/**
	 * Generate a copy of this sequence.
	 * 
	 * @param - none
	 * @return The return value is a copy of this sequence. Subsequent changes to
	 *         the copy will not affect the original, nor vice versa.
	 * @exception OutOfMemoryError Indicates insufficient memory for creating the
	 *                             clone.
	 **/
	public DoubleArraySeq clone() {
		try {
			DoubleArraySeq clone = (DoubleArraySeq) super.clone();
			clone.data = this.data.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Cloneable isnt implemented");
		}
	}

	/**
	 * Create a new sequence that contains all the elements from one sequence
	 * followed by another.
	 * 
	 * @param s1 the first of two sequences
	 * @param s2 the second of two sequences
	 * @precondition Neither s1 nor s2 is null.
	 * @return a new sequence that has the elements of s1 followed by the elements
	 *         of s2 (with no current element)
	 * @exception NullPointerException. Indicates that one of the arguments is null.
	 * @exception OutOfMemoryError      Indicates insufficient memory for the new
	 *                                  sequence.
	 * @note An attempt to create a sequence with a capacity beyond
	 *       Integer.MAX_VALUE will cause an arithmetic overflow that will cause the
	 *       sequence to fail.
	 **/
	public static DoubleArraySeq concatenation(DoubleArraySeq s1, DoubleArraySeq s2) {
		DoubleArraySeq newSequence = new DoubleArraySeq(s1.size() + s2.size());
		newSequence.addAll(s1);
		newSequence.addAll(s2);
		newSequence.currentIndex = newSequence.size();
		return newSequence;
	}

	/**
	 * Change the current capacity of this sequence.
	 * 
	 * @param minimumCapacity the new capacity for this sequence
	 * @postcondition This sequence's capacity has been changed to at least
	 *                minimumCapacity. If the capacity was already at or greater
	 *                than minimumCapacity, then the capacity is left unchanged.
	 * @exception OutOfMemoryError Indicates insufficient memory for: new
	 *                             int[minimumCapacity].
	 **/
	public void ensureCapacity(int minimumCapacity) {
		if (minimumCapacity <= getCapacity())
			return;
		data = Arrays.copyOf(data, minimumCapacity);

	}

	/**
	 * Accessor method to get the current capacity of this sequence. The add method
	 * works efficiently (without needing more memory) until this capacity is
	 * reached.
	 * 
	 * @param - none
	 * @return the current capacity of this sequence
	 **/
	public int getCapacity() {
		return data.length;
	}

	/**
	 * Accessor method to get the current element of this sequence.
	 * 
	 * @param - none
	 * @precondition isCurrent() returns true.
	 * @return the current element of this sequence
	 * @exception IllegalStateException Indicates that there is no current element,
	 *                                  so getCurrent may not be called.
	 **/
	public double getCurrent() {
		if (!isCurrent())
			throw new IllegalStateException("There is no current element so getCurrent cannot be called");
		return data[currentIndex];
	}

	/**
	 * Accessor method to determine whether this sequence has a specified current
	 * element that can be retrieved with the getCurrent method.
	 * 
	 * @param - none
	 * @return true (there is a current element) or false (there is no current
	 *         element at the moment)
	 **/
	public boolean isCurrent() {
		return currentIndex < manyItems;
	}

	/**
	 * Remove the current element from this sequence.
	 * 
	 * @param - none
	 * @precondition isCurrent() returns true.
	 * @postcondition The current element has been removed from this sequence, and
	 *                the following element (if there is one) is now the new current
	 *                element. If there was no following element, then there is now
	 *                no current element.
	 * @exception IllegalStateException Indicates that there is no current element,
	 *                                  so removeCurrent may not be called.
	 **/
	public void removeCurrent() {
		if (!isCurrent())
			throw new IllegalStateException("There is no current element so removeCurrent cannot be called");

		System.arraycopy(data, currentIndex + 1, data, currentIndex, manyItems - currentIndex);
		--manyItems;
	}

	/**
	 * Determine the number of elements in this sequence.
	 * 
	 * @param - none
	 * @return the number of elements in this sequence
	 **/
	public int size() {
		return manyItems;
	}

	/**
	 * Set the current element at the front of this sequence.
	 * 
	 * @param - none
	 * @postcondition The front element of this sequence is now the current element
	 *                (but if this sequence has no elements at all, then there is no
	 *                current element).
	 **/
	public void start() {
		if (size() == 0)
			throw new IllegalStateException("There are no elements in the sequence");

		currentIndex = 0;
	}

	/**
	 * Reduce the current capacity of this sequence to its actual size (i.e., the
	 * number of elements it contains). The current item does not change.
	 * 
	 * @param - none
	 * @postcondition This sequence's capacity has been changed to its current size.
	 * @exception OutOfMemoryError Indicates insufficient memory for altering the
	 *                             capacity.
	 **/
	public void trimToSize() {
		data = Arrays.copyOf(data, manyItems);
	}

	/**
	 * Provide a string representation of the sequence with current item in
	 * parentheses
	 * 
	 * @param - none
	 * @postcondition string representation returned but sequence is unchanged
	 * @return string displaying sequence
	 **/
	public String toString() {
		String answer = "";

		for (int i = 0; i < manyItems; i++) {
			if (i == currentIndex) {
				answer += "(" + data[i] + ") ";
			} else {
				answer += data[i] + " ";
			}
		}
		return answer;
	}

}
