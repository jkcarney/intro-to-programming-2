/*
  Filename   : CStack.java
  Author     : Joshua Carney
  Course     : CSCI 162-01
  Assignment : Assignment 9
  Description: A stack class that uses an ArrayList.
*/

import java.util.ArrayList;

/**
 * A stack ADT that utilizes an ArrayList.
 * 
 * @author Joshua Carney
 * @version April 2020
 */
public class CStack<E> {

	/*
	 * Why use an ArrayList:
	 * 
	 * With an ArrayList, its relatively easy to add to the back. A call to
	 * add(size()) would run at constant time. A call to remove(size() - 1) would
	 * also run at constant time because existing elements would not need to slide.
	 * The only time the functions would not run in constant time was if a
	 * push call went beyond the capacity of the ArrayList.
	 */
	private ArrayList<E> data;

	private static final int DEFAULT_CAPACITY = 10;

	/**
	 * Initializes an empty stack with a capacity of DEFAULT_CAPACITY = 10.
	 * 
	 * @param none
	 * @postcondition the Stack is empty with it's default capacity.
	 * @exception OutOfMemoryException - indicates insufficient memory for new
	 *                                 ArrayList<E>(default_capacity).
	 */
	public CStack() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Initializes an empty stack with the specified capacity.
	 * 
	 * @param none
	 * @postcondition the Stack is empty with the specified capacity.
	 * @exception OutOfMemoryException - indicates insufficient memory for new
	 *                                 ArrayList<E>(default_capacity).
	 */
	public CStack(int default_capacity) {
		data = new ArrayList<E>(default_capacity);
	}

	/**
	 * Returns how many elements are in the stack.
	 * 
	 * @param none
	 * @return an integer representing the number of elements in the sequence.
	 */
	public int size() {
		return data.size();
	}

	/**
	 * Returns a boolean if the stack is empty
	 * 
	 * @param none
	 * @return a boolean: true if the stack is empty, false if the stack still has
	 *         elements in it.
	 */
	public boolean isEmpty() {
		return data.isEmpty();
	}

	/**
	 * Pushes an element to the top of the stack
	 * 
	 * @param element - The element to be added to the top of the stack.
	 * @postcondition - The element has been added to the top of the stack and any
	 *                elements already in the stack are now below it.
	 */
	public void push(E element) {
		data.add(element);
	}
	/**
	 * Pops an element to the top of the stack
	 * 
	 * @param none
	 * @return The element just removed from the stack. 
	 * @postcondition - The element at the top of the stack has been removed
	 */
	public E pop() {
		return data.remove(size() - 1);
	}
	/**
	 * Look at the first element on top of the stack and return it (not remove)
	 * 
	 * @param none
	 * @return The element on top of the stack
	 */
	public E peek() {
		return data.get(data.size() - 1);
	}

	/*
	 * public static void main(String[] args) { 
	 * 		CStack<Integer> intStack = new CStack<Integer>(20);
	 * 		intStack.push(10); 
	 * 		intStack.push(30); 
	 *		intStack.push(100);
	 * 
	 * 		System.out.println(intStack.pop()); 
	 * 		System.out.println(intStack.pop());
	 * 		System.out.println(intStack.pop()); 
	 * }
	 */
}
