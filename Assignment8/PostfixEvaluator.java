
/*
  Filename   : PostfixEvaluator.java
  Author     : Joshua Carney
  Course     : CSCI 162-01
  Assignment : Assignment 8
  Description: A postfix evaluator class that relies on Java's Stack class.
*/
import java.util.Stack;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

/**
 * A postfix evaluator class with a single static method, evaluate, that
 * utilizes a Stack<Double>, a TokenScanner, and Token class to compute a String
 * representation of a postfix function. Valid operators are +, -, *, and /.
 * 
 * @note This method throws no exceptions. Evaluate returns caught exceptions
 *       with a string representation of that exception.
 * @author Joshua Carney
 * @version April 2020
 */
public class PostfixEvaluator {

	/**
	 * Evaluates a String of positive double numbers, inputed in a postfix style.
	 * Strings containing invalid characters will cause the method to prematurely
	 * end with an error message being returned. Valid strings include double
	 * numbers and the operators "+", "-", "*", and "/". Letters and "(" and ")" and
	 * are invalid characters.
	 * 
	 * @param input The user input, represented by a String, to be evaluated.
	 * @return A string representing the final evaluation of the input. The return
	 *         can also be a representation of an error that occurred during
	 *         computation, such as "Stack Underflow" for an EmptyStackException.
	 */
	public static String evaluate(String input) {
		TokenScanner inputScanner = new TokenScanner(input);

		// Will catch if there is no input or if input contains ( or )
		if (!inputScanner.hasNextToken())
			return "ERROR: No input";
		if (input.contains(")") || input.contains("("))
			return "ERROR: ( ) has no meaning here";

		Stack<Double> numberStack = new Stack<Double>();

		while (inputScanner.hasNextToken()) {
			Token t = inputScanner.nextToken();
			if (t.isNumber())
				numberStack.push(t.numberValue());
			else if (t.isOperator()) {
				// Will catch stack underflow, divide by zero, or no such element.
				try {
					compute(numberStack, t.operatorCharValue());
				} catch (EmptyStackException e) {
					return "ERROR: Stack Underflow";
				} catch (ArithmeticException e) {
					return "ERROR: Infinity or divide by zero error";
				} catch (NoSuchElementException e) {
					break;
				}
			} else
				break;
		}

		// Will catch if there is more than one number in the stack or if the end of the
		// inputScanner was reached prematurely
		if (numberStack.size() > 1)
			return "ERROR: Computed answer, but values remain on stack";
		if (!inputScanner.reachedEnd())
			return "ERROR: Computed answer, but not all input used.";

		return numberStack.pop().toString();
	}

	/**
	 * Computes the first two numbers on the stack, called by the operator. Then it
	 * pushes that number to the stack.
	 *
	 * @param numbers  An address to a Stack of Double numbers of all the numbers to
	 *                 be computed.
	 * @param operator A valid operator, +-/*
	 */
	private static void compute(Stack<Double> numbers, char operator) {
		Double n1 = numbers.pop();
		Double n2 = numbers.pop();
		switch (operator) {
		case '+':
			numbers.push(n1 + n2);
			break;
		case '*':
			numbers.push(n1 * n2);
			break;
		case '-':
			numbers.push(n2 - n1);
			break;
		case '/':
			numbers.push(n2 / n1);
			break;
		}
	}
}
