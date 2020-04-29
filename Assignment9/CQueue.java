/*
  Filename   : CQueue.java
  Author     : Joshua Carney
  Course     : CSCI 162-01
  Assignment : Assignment 9
  Description: A queue class that uses a LinkedList
*/

import java.util.LinkedList;

/**
 * A queue ADT that utilizes a LinkedList
 * 
 * @author Joshua Carney
 * @version April 2020
 */
public class CQueue<E> {

	/*
	 * Why use a LinkedList:
	 * 
	 * Its easy to maintain pushing to the back of a LinkedList and then pop from
	 * the front of the LinkedList. Removing from the front of an LinkedList is also
	 * extremely easy. Adding to the back of an LinkedList is easy as well because
	 * LinkedList has an addLast method.
	 */
	private LinkedList<E> data;

	/**
	 * Initializes an empty queue
	 * 
	 * @param none
	 * @postcondition a new queue has been created
	 */
	public CQueue() {
		data = new LinkedList<E>();
	}

	/**
	 * Returns how many elements are in the queue
	 * 
	 * @return an int representing how many elements are currently in the queue.
	 */
	public int size() {
		return data.size();
	}

	/**
	 * Returns if the queue is empty or not
	 * 
	 * @return a boolean - true if the list has no elements in it, false if the list
	 *         has elements in it.
	 */
	public boolean isEmpty() {
		return data.isEmpty();
	}

	/**
	 * Pushes an element into the queue
	 * 
	 * @param element - the value to be added to the queue
	 * @postcondition - The element passed has been added to the end of the queue
	 */
	public void push(E element) {
		data.addLast(element);
	}

	/**
	 * Pops an element from the front of the queue
	 * 
	 * @return the element just removed from the queue
	 * @postcondition - the element has been removed from the front of the queue
	 */
	public E pop() {
		return data.pollFirst();
	}

	/**
	 * Peeks the element at the front of the queue without removing it
	 * 
	 * @return the element at the front of the queue
	 */
	public E peek() {
		return data.peekFirst();
	}
	/*
	public static void main(String[] args) {
		CQueue<String> stringQ = new CQueue<String>();
		
		stringQ.push(" Yee ");
		stringQ.push(" eee ");
		stringQ.push(" haw ");
		
		System.out.print(stringQ.pop());
		System.out.print(stringQ.pop());
		System.out.print(stringQ.pop());
	}
	*/
}