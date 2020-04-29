/*
  Filename   : SortedList.java
  Author     : Joshua Carney
  Course     : CSCI 162-01
  Assignment : Assignment 6
  Description: A sorted list data collection class
*/

/**************************************************
 * A SortedList is a linked list of double values
 * 
 * @version March 2020
 * 
 * @author Joshua Carney
 * 
 **************************************************/

public class SortedList {

	// Class Invariant:
	// 1. head : represents a pointer to the first node of the DoubleNode class.
	// If the list is empty then head will point to null. If the list is not
	// empty, then the elements will be stored and sorted in increasing order
	//
	// 2. size : an integer keeping track of how many values are in the list.
	// A size of zero means the list is empty and the head points to null.
	// Otherwise, the size tells how many DoubleNodes are in the SortedList.
	// Any attempt to reference indexes >= size will result in undesired functionality.

	private DoubleNode head;
	private int size;

	/**
	 * Initializes an empty SortedList with size at zero head pointing to null.
	 *
	 * @param - none
	 * 
	 * @postcondition This sorted list is empty and points to null
	 * 
	 **/
	public SortedList() {
		this.head = null;
		this.size = 0;
	}

	/**
	 * Inserts the given value into the list in sorted order.
	 * 
	 * @param value the double value to be added to the list
	 * 
	 * @postcondition The double value passed is now added to the list in sorted, 
	 * 				  increasing order
	 *                 
	 **/
	public void insert(double value) {
		DoubleNode preceding = getPrecedingNode(value);

		if (preceding == null || head.getData() >= value) {
			head = new DoubleNode(value, head);
		} else {
			preceding.setLink(new DoubleNode(value, preceding.getLink()));
		}
		++size;
	}

	/**
	 * Grabs the address of the node prior to the passed double value
	 * 
	 * @param value: the value within the node
	 * 
	 * @return An address to a DoubleNode that precedes the value
	 * 
	 **/
	private DoubleNode getPrecedingNode(double value) {
		if (head == null)
			return null;

		DoubleNode n;
		for (n = head; n.getLink() != null; n = n.getLink())
			if (value <= n.getLink().getData())
				return n;
		return n;
	}

	/**
	 *
	 * Returns the position of "value" in the list from 0 to size - 1. If the value
	 * cannot be found, return -1.
	 *
	 * @param value the double value to search for
	 * 
	 * @return - an integer representing the index of the given value
	 *
	 **/
	public int find(double value) {
		int index = 0;
		for (DoubleNode n = head; n != null; n = n.getLink(), ++index)
			if (n.getData() == value)
				return index;
		return -1;
	}

	/**
	 *
	 * Removes the value at the passed index
	 * 
	 * @param index an int representing which 0 based index to remove
	 * 
	 * @return true if the value was successfully removed from the list. false if the
	 *         index was either less than 0 or greater than or equal to size
	 * 
	 * @precondition The index is valid
	 * 
	 * @postcondition The element at the given index has been removed and the
	 *                predescssor now points to the removed's link reference
	 *                
	 **/
	public boolean removeAt(int index) {
		if (index < 0 || index >= size)
			return false;

		DoubleNode remove = head;
		for (int i = 0; i != index; i++, remove = remove.getLink())
			;

		// Special case if the node to be removed is still head after for() loop
		// Otherwise the following code after this block will throw
		// a NullPointer if remove == head.
		if (remove == head) {
			head = remove.getLink();
			--size;
			return true;
		}

		DoubleNode preceding = getPrecedingNode(remove.getData());
		preceding.setLink(remove.getLink());
		--size;
		return true;
	}

	/**
	 *
	 * Accessor that returns how many nodes are in the list
	 * 
	 * @param - none
	 * @return - an integer of how many nodes are in the list
	 * 
	 **/
	public int size() {
		return size;
	}

	/**
	 * 
	 * Converts the SortedList into a string with brackets, for example, "[ 10.2
	 * 543.2 1001.113 ]" An empty SortedList is represented as "[ ]"
	 * 
	 * @param - none
	 * @return - a string representing the values in the SortedList
	 * 
	 */
	public String toString() {
		String answer = "[ ";
		DoubleNode current;

		for (current = head; current != null; current = current.getLink()) {
			answer += current.getData() + " ";
		}
		answer += "]";
		return answer;
	}
}
