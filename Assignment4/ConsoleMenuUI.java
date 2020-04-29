import java.text.DecimalFormat;
import java.util.Scanner;
import java.io.IOException;

/*
 * Created on January 2007; Updated January 2008
 */

/**
 * @author Beth Katz
 * The ConsoleMenuUI class handles the console-based menu-driven
 * interface for the Statistician 
 */

public class ConsoleMenuUI {

	Statistician theStat;
	private static Scanner scan = new Scanner (System.in);
	private static DecimalFormat fmt = new DecimalFormat("0.000");

	/**
	 * Constructor for ConsoleUI class constructs a new Statistician
	 */
	public ConsoleMenuUI( )	{
		theStat = new Statistician( );
	}


	/**
	 * prints a summary of a Statistician's info
	 * @param s
	 *    a statistician to be printed
	 */
	public void print(Statistician s) {
		System.out.println(s.length( ) + " entries total "
				+ fmt.format(s.sum( )) + " with mean of " 
				+ fmt.format(s.mean( )) );
		System.out.println("Largest value is " + fmt.format(s.largest( ))
				+ " and smallest is " + fmt.format(s.smallest( )) );
	}

	/**
	 * obtains a value from user and adds it to a Statistician
	 * @param s
	 *    statistician where value will be inserted
	 */
	public void addValue(Statistician s) {
		double value;

		System.out.print("Value to add: ");
		value = scan.nextDouble( );
		System.out.println(fmt.format(value) + " was inserted.");
		s.insert(value);
	}

	/**
	 * prints the menu choices
	 */
	public void printMenu( ) {
		System.out.println("Menu choices are:");
		System.out.println("q: quit the testing program");
		System.out.println("?: print this menu");
		System.out.println("+: add a value to the statistician");
		System.out.println("p: print a summary of the statistician values");
		System.out.println("r: reset the statistician");
		System.out.println("u: exercise the union operator");
	}

	/**
	 * reads commands from the user and handles them
	 */
	public void doMenu ( ) {
		String theCommand;
		try
		{

			System.out.println("Enter command:");
			while (scan.hasNext( ))
			{
				theCommand = scan.next( );
				if (theCommand.length() > 0) {
					if (theCommand.length( ) == 1) {
						char command = theCommand.charAt(0);
						if (command == 'q') {
							throw new IOException( );
						} else if (command == '?') {
							printMenu( );
						} else if (command == '+') {
							addValue(theStat);
						} else if (command == 'p') {
							print(theStat);
						} else if (command == 'r') {
							theStat.reset( );
						} else if (command == 'u') {
							UnionDriver ud = new UnionDriver( );
							ud.run(scan);
						}
					} else {
						System.out.println("Only one letter commands allowed. "
								+ "Your longer one was ignored.");
					}
					System.out.println("Enter command:");
				}
			}
		}
		catch ( IllegalStateException e ) {
			System.out.println(e.getMessage( ));
			System.out.println("Thank you for using the Statistician.");
			return;
		}
		catch ( IOException e ) {
			System.out.println("Thank you for using the Statistician.");
			return;
		}
	}


	public static void main(String[] args) {
		ConsoleMenuUI p = new ConsoleMenuUI( );

		p.printMenu( );
		p.doMenu( );
	}

}
