import java.text.DecimalFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 * Created January 2007; Updated January 2008
 */

/**
 * @author Beth Katz
 * Exercises the union operator for Statistician 
 */

public class UnionDriver {

	private static DecimalFormat fmt = new DecimalFormat("0.00");
	
	/**
	 * constructs a UnionDriver with no instance variables
	 */
	UnionDriver( ) {
	}

	/**
	 * prints a summary of a Statistician's info
	 * @param s
	 *    a statistician to be printed
	 * @param label
	 *    label for output
	 */
	public void print(Statistician s, String label) {
		System.out.println( );
		System.out.print("Stat <<" + label + ">> has ");
		System.out.println(s.length( ) + " entries totalling "
				+ fmt.format(s.sum( )) + " with mean of " 
				+ fmt.format(s.mean( )) );
		System.out.println("Largest value is " + fmt.format(s.largest( ))
				+ " and smallest is " + fmt.format(s.smallest( )) );
	}

	/**
	 * obtains n values from scanner and adds them to a Statistician
	 * @param scan
	 *    an existing scanner used for input
	 * @param s
	 *    a statistician to be filled
	 * @param n
	 *    number of values to obtain from user
	 */
	public void fillStat(Scanner scan, Statistician s, int n) {
		double value;

		try {
			System.out.print("Enter " + n + " values to add: ");
			for (int i = 1; i <= n; i++) {
				value = scan.nextDouble( );
				s.insert(value);
			}
		}
		catch (NoSuchElementException e) {
			throw new IllegalStateException("Bad input. Needed " + n + " double values");
		}	
	}

	/**
	 * builds statisticians, fills them with data from user, and 
	 * unions them to create a new statistician
	 * @param
	 *    existing scanner must be ready to provide input
	 */
	public void run (Scanner scan) {
		if (scan == null) {
			throw new IllegalStateException("Must have valid existing scanner to run this.");
		}

		Statistician a = new Statistician( );
		Statistician b = new Statistician( );
		Statistician c, d;

		fillStat(scan, a, 5);
		fillStat(scan, b, 4);

		print(a, "a");
		print(b, "b");

		c = a.union(b);
		print(c, "c is union(a,b)");

		d = a.clone( );
		print(d, "d is a clone of a");

		if (d.equals(a)) {
			System.out.println("a and d should be equivalent");
		} else {
			System.out.println("a and d are not equivalent. Why not?");
		}
		d.insert(42);
		print(d, "d after inserting 42");
		print(a, "a after d was changed");

		if (d.equals(a)) {
			System.out.println("d and a are still equivalent. Why?");
		} else {
			System.out.println("d and a should no longer be equivalent");
		}
	}
}
