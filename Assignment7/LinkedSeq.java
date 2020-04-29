/*
  Filename   : LinkedSeq.java
  Author     : Joshua Carney
  Course     : CSCI 162-01
  Assignment : Assignment 7
  Description: A sequence data collection class implemented using a linked list.
*/

// File: LinkedSeq.java based on the package edu.colorado.collections

// This is an assignment for after completion of Chapters 4 and 5
// of "Data Structures and Other Objects Using Java" by Michael Main.

/******************************************************************************
 * This class is a homework assignment; A LinkedSeq is a collection of objects
 * that are kept in order. The sequence can have a special "current element,"
 * which is specified and accessed through four methods that are not available
 * in the bag class (start, getCurrent, advance and isCurrent).
 *
 * @note Beyond Int.MAX_VALUE elements, the size method does not work.
 *
 * @see Based on the sequence with linked list assignment by Michael Main in
 *      chapter 4 but using generic nodes from chapter 5
 *
 * @version March 2007
 * 
 * @author Implementation by Student name
 ******************************************************************************/
public class LinkedSeq<E> implements Cloneable {
	// Invariant of the LinkedSeq class:
	// 1. the number of items in the sequence is maintained in size
	// 2. head points to the first node, if any, or it is null
	// 3. tail points to the last node, if any, or it is null
	// 4. if there is a current item, cursor points to it and
	// precursor points to the node before it, if any
	// 5. if there is no current item, cursor and precursor are both null

	private Node<E> head, tail, cursor, precursor;
	private int size;

	/**
	 * Initialize an empty sequence.
	 * 
	 * @param - none
	 * @postcondition: This sequence is empty.
	 **/
	public LinkedSeq() {
		head = null;
		tail = null;
		cursor = null;
		precursor = null;
		size = 0;
	}

	/**
	 * Add a new element to this sequence, after the current element.
	 * 
	 * @param element the new element that is being added
	 * @postcondition: A new copy of the element has been added to this sequence. If
	 *                 there was a current element, then the new element is placed
	 *                 after the current element. If there was no current element,
	 *                 then the new element is placed at the end of the sequence. In
	 *                 all cases, the new element becomes the new current element of
	 *                 this sequence.
	 * @exception OutOfMemoryError Indicates insufficient memory for a new node.
	 **/
	public void addAfter(E element) {
		Node<E> addend = new Node<E>(element, null);	
		if(!isCurrent()) { // If no current
			if(head == null) { // If the head is null, point head and tail to addend to avoid NullPointer
				head = addend;
				tail = addend;
			} else { // If the head is not null, there are elements already in the list, so add to tail.
				tail.setLink(addend);
				tail = addend;
			}
		} else { // There IS a current, therefore it should be added after the current.
			addend.setLink(cursor.getLink());
			cursor.setLink(addend);
		}
		
		++size;
		precursor = cursor;
		cursor = addend;
		
		if(tail == null || tail.getLink() != null)
			tail = updateTail();
	}

	/**
	 * Add a new element to this sequence, before the current element.
	 * 
	 * @param element the new element that is being added
	 * @postcondition: A new copy of the element has been added to this sequence. If
	 *                 there was a current element, then the new element is placed
	 *                 before the current element. If there was no current element,
	 *                 then the new element is placed at the start of the sequence.
	 *                 In all cases, the new element becomes the new current element
	 *                 of this sequence.
	 * @exception OutOfMemoryError Indicates insufficient memory for a new node.
	 **/
	public void addBefore(E element) {
		Node<E> addend = new Node<E>(element, null);
		if(!isCurrent() || cursor == head) { // If there isn't a current OR the cursor is at the head, add to beginning of list
			if(size == 0) { // If there are no elements in the list, set head and tail to addend
				head = addend;
				tail = addend;
			} else { // There are elements in the list, so add it to the front
				addend.setLink(head);
				head = addend;
			}
		} else { // There is a cursor which does not point to head, so add it before.
			addend.setLink(precursor.getLink());
			precursor.setLink(addend);
		}
		
		++size;
		cursor = addend;
		
		if(precursor == null || precursor.getLink() != cursor)
			precursor = updatePrecursor();
	}
	/**
	 *  Private method that updates the precursor for the addBefore and addAfter methods
	 *  
	 * @return An address to the preceding node of whatever the cursor is (unless there are no elements
	 * or the cursor is at the head, in which case it will return null
	 */
	private Node<E> updatePrecursor() {
		if(head == null || head == cursor)
			return null;
		
		Node<E> n;
		for(n = head; n.getLink() != null && n.getLink() != cursor; n = n.getLink());
		return n;
	}
	/**
	 * 
	 * Private method that updates the tail for addBefore and addAfter methods
	 * 
	 * @return An address to the tail node of the list from the head field.
	 */
	private Node<E> updateTail() {
		if(head == null)
			return null;
		if(size == 1)
			return head;
		
		Node<E> n;
		for(n = head; n.getLink() != null; n = n.getLink());
		return n;
	}

	/**
	 * Place the contents of another sequence at the end of this sequence.
	 * 
	 * @param addend a sequence whose contents will be placed at the end of this
	 *               sequence
	 * @precondition: The parameter, addend, is not null.
	 * @postcondition: The elements from addend have been placed at the end of this
	 *                 sequence. The current element of this sequence remains where
	 *                 it was, and the addend is also unchanged.
	 * @exception NullPointerException Indicates that addend is null.
	 * @exception OutOfMemoryError     Indicates insufficient memory to increase the
	 *                                 size of this sequence.
	 **/
	public void addAll(LinkedSeq<E> addend) {
		LinkedSeq<E> addendClone = addend.clone();
		
		if(size == 0)
			head = addendClone.head;
		else
			tail.setLink(addendClone.head);
		
		tail = addendClone.tail;
		size += addend.size;
	}

	/**
	 * Move forward, so that the current element is now the next element in this
	 * sequence.
	 * 
	 * @param - none
	 * @precondition: isCurrent() returns true.
	 * @postcondition: If the current element was already the end element of this
	 *                 sequence (with nothing after it), then there is no longer any
	 *                 current element. Otherwise, the new element is the element
	 *                 immediately after the original current element.
	 * @exception IllegalStateException Indicates that there is no current element,
	 *                                  so advance may not be called.
	 **/
	public void advance() {
		if (!isCurrent()) {
			throw new IllegalStateException("No current element; advance may not be called");
		}
		precursor = cursor;
		cursor = cursor.getLink();
	}

	/**
	 * Generate a copy of this sequence.
	 * 
	 * @param - none
	 * @return The return value is a copy of this sequence. Subsequent changes to
	 *         the copy will not affect the original, nor vice versa. Note that the
	 *         return value must be type cast to a LinkedSeq before it can be used.
	 * @exception OutOfMemoryError Indicates insufficient memory for creating the
	 *                             clone.
	 * @note Refer to discussion on p. 228 of text; there's substantial extra work
	 *       In the generic implementation, you will get an unavoidable warning on
	 *       answer = (LinkedSeq<E>) super.clone( ); That's okay. The cure is worse
	 *       than the disease.
	 **/
	@SuppressWarnings("unchecked")
	public LinkedSeq<E> clone() // DO NOT CHANGE THIS METHOD
	{
		LinkedSeq<E> answer;

		try {
			answer = (LinkedSeq<E>) super.clone(); // causes warning that we have suppressed
		} catch (CloneNotSupportedException e) { // This exception should not occur. But if it does, it would probably
													// indicate a programming error that made super.clone unavailable.
													// The most common error would be forgetting the "Implements
													// Cloneable"
													// clause at the start of this class.
			throw new RuntimeException("This class does not implement Cloneable");
		}

		if (head != null) { // need to copy this sequence into answer

			// copy the first node
			answer.head = new Node<E>(head.getData(), null);
			if (cursor == head) {
				answer.cursor = answer.head;
			}
			if (precursor == head) { // current is next node
				answer.precursor = answer.head;
			}

			// copy the rest of the sequence
			Node<E> thisPtr = head.getLink();
			Node<E> answerPtr = answer.head;

			while (thisPtr != null) {
				answerPtr.setLink(new Node<E>(thisPtr.getData(), null));
				answerPtr = answerPtr.getLink();
				if (cursor == thisPtr) {
					answer.cursor = answerPtr;
				}
				if (precursor == thisPtr) { // current is next node
					answer.precursor = answerPtr;
				}
				thisPtr = thisPtr.getLink();
			}

			// set answer's tail to the last node created
			answer.tail = answerPtr;
		}

		return answer;
	}

	/**
	 * Create a new sequence that contains all the elements from one sequence
	 * followed by the other.
	 * 
	 * @param s1 the first of two sequences
	 * @param s2 the second of two sequences
	 * @precondition: Neither s1 nor s2 is null.
	 * @return a new sequence that has the elements of s1 followed by the elements
	 *         of s2 (with no current element)
	 * @exception NullPointerException. Indicates that one of the arguments is null.
	 * @exception OutOfMemoryError      Indicates insufficient memory for the new
	 *                                  sequence.
	 **/
	public static <E> LinkedSeq<E> concatenation(LinkedSeq<E> s1, LinkedSeq<E> s2) {
		LinkedSeq<E> clonedSeq = s1.clone();
		clonedSeq.addAll(s2);

		clonedSeq.cursor = null;
		clonedSeq.precursor = null;

		return clonedSeq;
	}

	/**
	 * Accessor method to get the current element of this sequence.
	 * 
	 * @param - none
	 * @precondition: isCurrent() returns true.
	 * @return the current element of this sequence
	 * @exception IllegalStateException Indicates that there is no current element,
	 *                                  so getCurrent may not be called.
	 **/
	public E getCurrent() {
		if (!isCurrent())
			throw new IllegalStateException("There is no current element, therefore getCurrent may not be called.");

		return cursor.getData();

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
		return cursor != null;
	}

	/**
	 * Remove the current element from this sequence.
	 * 
	 * @param - none
	 * @precondition: isCurrent() returns true.
	 * @postcondition: The current element has been removed from this sequence, and
	 *                 the following element (if there is one) is now the new
	 *                 current element. If there was no following element, then
	 *                 there is now no current element.
	 * @exception IllegalStateException Indicates that there is no current element,
	 *                                  so removeCurrent may not be called.
	 **/
	public void removeCurrent() {
		if (!isCurrent())
			throw new IllegalStateException("There is no current element, therefore removeCurrent may not be called.");
		
		if(cursor == head) {
			cursor = head.getLink();
			head = head.getLink();
		} else {
			Node<E> next = cursor.getLink();
			precursor.setLink(cursor.getLink());
			cursor = next;
		}
		--size;
	}

	/**
	 * Determine the number of elements in this sequence.
	 * 
	 * @param - none
	 * @return the number of elements in this sequence
	 **/
	public int size() {
		return size;
	}

	/**
	 * Set the current element at the front of this sequence.
	 * 
	 * @param - none
	 * @postcondition: The front element of this sequence is now the current element
	 *                 (but if this sequence has no elements at all, then there is
	 *                 no current element).
	 **/
	public void start() {
		cursor = head;
	}

	/**
	 * Provide a string representation of the sequence with current item in
	 * parentheses
	 * 
	 * @param - none
	 * @postcondition string representation returned but sequence is unchanged
	 * @return string displaying sequence
	 **/
	public String toString() // DO NOT CHANGE THIS METHOD
	{
		String answer = "";
		Node<E> current;

		for (current = head; current != null; current = current.getLink()) {
			if (current == cursor) {
				answer += "(" + current.getData() + ") ";
			} else {
				answer += current.getData() + " ";
			}
		}
		return answer;
	}

}
