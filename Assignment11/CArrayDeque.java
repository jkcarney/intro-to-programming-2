/*
  Filename   : CArrayDeque.java
  Author     : Joshua Carney
  Course     : CSCI 162-01
  Assignment : Assignment 11
  Description: A deque class
*/

// Author: Gary M. Zoppetti
// This class began as an implementation of a Queue ADT using a
//   circular array data structure. 
// It now partially implements a Deque ADT. 
// Modified and completed by Joshua Carney
public class CArrayDeque<E>
{
	// A deque is a double-ended queue, supporting O(1) additions and
    //   removals at both ends. 
	private Object[] data;
	private int size;
	// Index of the first element in the deque
	private int front;
	// Index one past the last element in the deque
	// (i.e., the index of where the next element would be inserted)
	// Elements range from data[front] to one before data[back]
	private int back;

	private static final int DEFAULT_CAPACITY = 10;

	/**
	 * Creates a new deque with a default capacity of 10
	 * @param - none
	 */
	public CArrayDeque ()
	{
		this (DEFAULT_CAPACITY);
	}

	/**
	 * Creates a new deque of the specified capacity
	 * 
	 * @param capacity - The initial capacity of the deque. Defaults to 10.
	 */
	public CArrayDeque (int capacity)
	{
		if (capacity < 0)
			throw new IllegalArgumentException ("capacity is negative: " + capacity);
		this.data = new Object[capacity];
		this.size = 0;
		this.front = this.back = 0;
	}

	/**
	 * Helper method used in add methods to help increment the index and allows the arrays to be modular. 
	 * 
	 * @param index - index to be incremented
	 * @param modulus - capacity of the data array
	 * @return - new index
	 */
	private static int incr (int index, int modulus)
	{
		if (++index >= modulus)
			index = 0;
		return index;
	}

	/**
	 * Helper method used in remove methods to help decrement the index and allows the arrays to be modular. 
	 * 
	 * @param index - index to be decremented
	 * @param modulus - capacity of the data array
	 * @return - new index
	 */
    private static int decr (int index, int modulus)
	{
    	if (--index <= 0)
			index = modulus - 1;
		return index;
	}


    /**
     * Returns how many elements are currently in the deque.
     * 
     * @return - an integer representing how many elements are in the deque
     */
	public int size ()
	{
		return size;
	}

	/**
	 * Returns a boolean representing if the deque is empty or not
	 * 
	 * @return - true if the deque is empty
	 * 			 false otherwise
	 */
	public boolean isEmpty ()
	{
		return size == 0;
	}

	/**
	 * Returns a boolean representing if the deque is full or not
	 * 
	 * @return - true if the deque is full
	 * 			 false otherwise
	 */
	public boolean isFull ()
	{
		return size == data.length;
	}

	/**
	 * Adds a specified element to the back of the deque. 
	 * If the add will cause the deque to overflow, capacity will be increased automatically
	 * 
	 * @param elem - Element to be added to the back of the deque
	 */
	public void addLast (E elem)
	{
        if(isFull())
        	ensureCapacity(2 * size + 1);
        data[back] = elem;
        back = incr(back, data.length);
        ++size;
	}

	/**
	 * Adds a specified element to the front of the deque.
	 * If the add will cause the deque to overflow, capacity will be increased automatically
	 * 
	 * @param elem - Element to be added to the front of the deque.
	 */
	public void addFirst (E elem)
	{
		if (isFull ())
			ensureCapacity (2 * size + 1);
		
		front = decr(front, data.length);
		data[front] = elem;
		++size;
        
	}

	/**
	 * Increases the capacity of the deque to a specified capacity.
	 * Calls to decrease the capacity are ignored
	 * 
	 * @param capacity - The new capacity of the deque.
	 */
	public void ensureCapacity (int capacity)
	{
		if (capacity <= data.length)
			return;

		Object[] newData = new Object[capacity];
		if (front < back)
			System.arraycopy (data, front, newData, 0, size);
		else
		{
            System.arraycopy(data, front, newData, 0, data.length - front);
            System.arraycopy(data, 0, newData, data.length - front, back);
		}
		data = newData;
		front = 0;
		back = size;
	}

	/**
	 * Removes the first element of the deque and returns it
	 * 
	 * @return The element removed from the deque
	 */
	@SuppressWarnings("unchecked")
	public E removeFirst ()
	{
		if (isEmpty ())
			throw new RuntimeException ("Deque is empty");
		E elem = (E) data[front];
		data[front] = null;
		front = incr (front, data.length);
		--size;
		return elem;
	}

	/**
	 * Removes the last element from the deque and returns it.
	 * 
	 * @return The element removed from the end of the deque
	 */
	@SuppressWarnings("unchecked")
	public E removeLast ()
	{
		if (isEmpty ())
			throw new RuntimeException ("Deque is empty");
		
		back = decr(back, data.length);
		E elem = (E) data[back];
		data[back] = null;
        --size;
        return elem;
	}

	/**
	 * Grabs the first element of the deque but does not remove it
	 * 
	 * @return The first element of the deque
	 */
	@SuppressWarnings("unchecked")
	public E getFirst ()
	{
		if (isEmpty ())
			throw new RuntimeException ("Deque is empty");

		return (E) data[front];
	}

	/**
	 * Grabs the last element of the deque but does not remove it
	 * 
	 * @return the last element of the deque
	 */
	@SuppressWarnings("unchecked")
	public E getLast ()
	{
		if (isEmpty ())
			throw new RuntimeException ("Deque is empty");

		if(back == 0) {
			return (E) data[data.length - 1];
		}
        return (E) data[back - 1];
	}

	/**
	 *  Return a string of the form [ e0 e1 e2 ... e(n-1) ],
	 *  where e0 is the first element and e(n-1) is the last
	 *  
	 *  @return a string of the deque. 
	 */
	public String toString ()
	{
		StringBuilder r = new StringBuilder("[ ");
		// CASE: back of the deque is "in front of" the front index
        if(back < front) {
        	for(int i = 0; i < back; ++i) {
        		r.append(data[i]);
        		r.append(" ");
        	}
        	for(int i = front; i < data.length; ++i) {
        		r.append(data[i]);
        		r.append(" ");
        	}
        }
        // CASE: back of the deque is "behind" the front index
        else {
        	for(int i = front; i < back; ++i) {
        		r.append(data[i]);
        		r.append(" ");
        	}
        }
        r.append("]");
        return r.toString();
	}

	public static void main (String[] args)
	{
		// Allocate a deque of capacity 0 to see if it grows as needed.
		CArrayDeque<Integer> deque = new CArrayDeque<> (0);
		for (int i = 0; i < 1_000; ++i)
		{
			deque.addFirst (i);
			deque.addLast (i);
		}
		// Print the deque
		// Fix this by writing the "toString" method above
		System.out.println ("deque: " + deque);
		while (!deque.isEmpty ())
		{
			int first = deque.getFirst ();
			int last = deque.getLast ();
			if (first != last)
			{
				System.out.println ("front element not equal to back element");
				System.exit (1);
			}
			deque.removeFirst ();
			deque.removeLast ();
		}
		System.out.println ("Success!");
	}
}