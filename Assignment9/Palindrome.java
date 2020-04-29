/*
  Filename   : Palindrome.java
  Author     : Joshua Carney
  Course     : CSCI 162-01
  Assignment : Assignment 9
  Description: A palindrome checker that utilizes the CStack and CQueue and ACTUALLY ignores case.
*/

import java.util.Scanner; // From Appendix B

/******************************************************************************
 * The <CODE>Palindrome</CODE> Java application illustrates the use of a stack
 * and a queue in the same program to determine whether a line is a palindrome.
 * The program ignores everything except alphabetic letters, and it ignores the
 * difference between upper- and lowercase letters.
 *
 * <p>
 * <b>Java Source Code for this class:</b>
 * <A HREF="../applications/Palindrome.java">
 * http://www.cs.colorado.edu/~main/applications/Palindrome.java </A>
 *
 * @author Michael Main <A HREF="mailto:main@colorado.edu"> (main@colorado.edu)
 *         </A>
 *
 * @version Feb 10, 2016
 ******************************************************************************/
public class Palindrome
{
	/**
	 * The main method prompts the user for a strings. Each string is read and
	 * checked to see whether it is a palindrome. The program ends when the user
	 * enters an empty line (just the return key).
	 * 
	 * @param args not used in this implementation
	 **/
	public static void main (String[] args)
	{
		Scanner stdin = new Scanner (System.in); 
		String line; 

		do
		{
			System.out.print ("Your expression (or return to end): ");
			line = stdin.nextLine ();
			if (isPalindrome (line))
				System.out.println ("That is a palindrome.");
			else
				System.out.println ("That is not a palindrome.");
		} while (line.length () != 0);
		stdin.close ();
	}

	/**
	 * Determine whether a string is a palindrome. Note: All non-letters are ignored,
	 * and the case of the letters is also ignored.
	 * 
	 * @param input the string to check
	 * @return true if and only if the input string is a palindrome.
	 **/
	public static boolean isPalindrome (String input)
	{
		CQueue<Character> q = new CQueue<Character> ();
		CStack<Character> s = new CStack<Character> ();
		
		for(int i = 0; i < input.length(); ++i) {
			char c = input.charAt(i);
			c = Character.toUpperCase(c);
			if(Character.isLetter(c)) {
				q.push(c);
				s.push(c);
			}
		}
		
		while(!q.isEmpty()) {
			if(q.pop() != s.pop())
				return false;
		}
		
		return true;
	}

}