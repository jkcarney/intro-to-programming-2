import java.util.Scanner;

public class Recursion {

	/**
	 * Returns a string that is "symmetrical" on both sides, where n is the largest
	 * integer.
	 * 
	 * @param n - the largest value
	 * @return - a string with the specific pattern OR an error message that
	 *         indicates n was less than 1
	 */
	public static String pattern(int n) {
		if (n < 1)
			return "Argument must be >= 1";

		if (n == 1)
			return "1";

		String optionalNewLine = "";
		if (n > 5)
			optionalNewLine = "\n";

		return pattern(n - 1) + " " + n + " " + optionalNewLine + pattern(n - 1);
	}

	/**
	 * Create an hourglass ASCII art out of asterisks and spaces. The hourglass ends
	 * with a newline character
	 * 
	 * @param n - the size of the hourglass
	 * @return - A string of the hourglass
	 */
	public static String hourglass(int n) {
		return hourglass(n, 0);
	}

	/**
	 * Private hourglass method to keep track of how many spaces go in front of the
	 * hourglass The public hourglass method first calls this with spaces at 0
	 * 
	 * @param n      - the size of the hourglass
	 * @param spaces - how many spaces go before a given line.
	 * @return - A string of the hourglass
	 */
	private static String hourglass(int n, int spaces) {
		if (n < 1)
			return "Argument must be greater than or equal to 1";
		if (n == 1)
			return hourglassHelper(1, spaces) + "\n" + hourglassHelper(1, spaces);
		String endingNewLineChar = "";
		if (spaces == 0)
			endingNewLineChar = "\n";

		return hourglassHelper(n, spaces) + "\n" + hourglass(n - 1, spaces + 1) + "\n" + hourglassHelper(n, spaces) + endingNewLineChar;
	}

	/**
	 * Private helper method that creates a single line of the hourglass
	 * 
	 * @param stars  - how many stars go in the given line
	 * @param spaces - how many spaces go in front of the given line
	 * @return - a single line of the hourglass, the number of spaces followed by
	 *         stars
	 */
	private static String hourglassHelper(int stars, int spaces) {
		return printMany(" ", spaces) + printMany("* ", stars);
	}

	/**
	 * Creates a string that repeats a certain string n times
	 * 
	 * @param toRepeat - string to be repeated
	 * @param n        - how many times a given string will be repeated.
	 * @return - The string toRepeat, repeated n times
	 */
	public static String printMany(String toRepeat, int n) {
		if (n < 1)
			return "";
		return toRepeat + printMany(toRepeat, (n - 1));
	}

	/**
	 * Recursive method that creates a string that formats a long with commas
	 * 
	 * @param n - A long to be comma'ized
	 * @return - A string of the long n that is separated by commas
	 */
	public static String commas(long n) {
		if (n % 1000 == n)
			return Long.toString(n);

		String negativeSign = "";
		if (n < 0) {
			n = Math.abs(n);
			negativeSign = "-";
		}

		String addedZeros = "";
		if (n % 1000 < 10)
			addedZeros = "00";
		else if (n % 1000 < 100 && n % 1000 != 0)
			addedZeros = "0";

		return negativeSign + commas(n / 1000) + "," + addedZeros + n % 1000;
	}

	public static void shell() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("==> ");
			String version = sc.next();
			if (version.equals("pattern")) {
				System.out.println(pattern(sc.nextInt()));
			} else if (version.equals("hourglass")) {
				System.out.print(hourglass(sc.nextInt()));
			} else if (version.equals("commas")) {
				System.out.println(commas(sc.nextLong()));
			} else if (version.equals("exit")) {
				break;
			} else {
				System.out.println("Available commands:");
				System.out.println("  pattern <n>");
				System.out.println("  hourglass <n>");
				System.out.println("  commas <n>");
				System.out.println("  exit");
			}
		}
		sc.close();
	}

	public static void main(String[] args) {
		System.out.println(pattern(-1));
		System.out.println(pattern(1));
		System.out.println(pattern(2));
		System.out.println(pattern(4));
		System.out.println(pattern(7));
		System.out.println(pattern(8));
		
		System.out.println();
		
		System.out.println(hourglass(-1) + "\n");
		System.out.println(hourglass(1) + "\n");
		System.out.println(hourglass(4) + "\n");
		System.out.println(hourglass(10) + "\n");
		
		System.out.println();
		
		System.out.println(commas(10));
		System.out.println(commas(1000));
		System.out.println(commas(-10));
		System.out.println(commas(-355353));
		System.out.println(commas(4236432));
		System.out.println(commas(9223372036854775807L));
		System.out.println(commas(-9223372036854775808L));
		
		shell();
	}

}
