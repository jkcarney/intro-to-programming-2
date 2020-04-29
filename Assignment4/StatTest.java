/**
 * JUnit tests for Statistician
 *
 * @author Beth Katz and David Hutchens
 * 
 */
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * You must include the Junit4 library to use this test unit.
 *
 * To add it, select the project and then choose Properties... from the File menu.
 * Click on "Java Build Path" in the panel at the left.
 * Select Libraries from the list at the top.
 * Select Add Library... from the right side of the panel.
 * Select JUnit and click on Next>.
 * Change the version to JUnit 4 and click on Finish..
 * Click on OK in the properties panel.
 */


public class StatTest {
	/**
	 * create Statistician and check whether it's constructed properly
	 */
	@Test
	public void testConstructor() {
		Statistician s = new Statistician( );

		assertTrue("Sum wrong", s.sum( ) == 0);
		assertTrue("Length wrong", s.length( ) == 0);
		assertTrue("Mean wrong", Double.isNaN(s.mean( )));
		assertTrue("Largest wrong", Double.isNaN(s.largest( )));
		assertTrue("Smallest wrong", Double.isNaN(s.smallest( )));
	}

	/**
	 * try length and insert
	 */
	@Test
	public void one( ) {
		Statistician s = new Statistician( );
		s.insert(42);
		assertTrue("Length should be one", s.length( ) == 1);
		s.insert(12);
		s.insert(17);
		assertTrue("Length should be three", s.length( ) == 3);
	}


	/**
	 * test reset by adding some things and reseting
	 */
	@Test
	public void reset() {
		Statistician s = new Statistician( );

		s.insert(42);
		s.insert(17);
		s.insert(-5.5);
		s.insert(0);

		assertTrue("Sum wrong", s.sum( ) == 53.5);
		assertTrue("Length wrong", s.length( ) == 4);
		assertEquals("Mean wrong", 13.375, s.mean( ), 0.0001);
		assertEquals("Largest wrong", 42, s.largest( ), 0.0001);
		assertEquals("Smallest wrong", -5.5, s.smallest( ), 0.0001);

		s.reset( );	

		assertTrue("Sum wrong after reset", s.sum( ) == 0);
		assertTrue("Length wrong after reset", s.length( ) == 0);
		assertTrue("Mean wrong after reset", Double.isNaN(s.mean( )));
		assertTrue("Largest wrong after reset", Double.isNaN(s.largest( )));
		assertTrue("Smallest wrong after reset", Double.isNaN(s.smallest( )));
	}

	/**
	 * try to find problem of not handling all values being negative correctly
	 */
	@Test
	public void allNegative( ) {
		Statistician s = new Statistician( );

		s.insert(-42);
		s.insert(-10);
		s.insert(-8.15);

		assertEquals("Sum wrong", -60.15, s.sum( ), 0.0001);
		assertTrue("Length wrong", s.length( ) == 3);
		assertEquals("Mean wrong", -20.05, s.mean( ), 0.0001);
		assertEquals("Largest wrong", -8.15, s.largest( ), 0.0001);
		assertEquals("Smallest wrong", -42, s.smallest( ), 0.0001);
	}

	/**
	 * uncover bug of using 0 in initial seting for smallest
	 */
	@Test
	public void smallestZero( ) {
		Statistician s = new Statistician( );

		s.insert(1);
		s.insert(0);
		s.insert(2);

		assertEquals("Smallest wrong", 0, s.smallest( ), 0.0001);
	}

	/**
	 * Creates statisticians with single different values
	 * and compares the statisticians to see if they are different.
	 * It then adds the 'opposing' values to each and compares to
	 * see that the statisticians seem to be the same.
	 */
	@Test
	public void compareTwoSmall() {
		Statistician s = new Statistician( );
		Statistician t = new Statistician( );

		s.insert(42);
		t.insert(-736.3);

		assertTrue("t not equal to s", !s.equals(t));
		assertTrue("s not equal to t", !t.equals(s));

		s.insert(-736.3);
		t.insert(42);

		assertTrue("t equals to s", s.equals(t));
		assertTrue("s equals to t", t.equals(s));
		assertTrue("not equal to null", !s.equals(null));
	}


	/**
	 * Creates statisticians with several different values
	 * and compares the statisticians to see if they are the same.
	 * It then adds an extra value to one and compares to
	 * see that the statisticians seem to be different.
	 */
	@Test
	public void compareTwoMedium() {
		Statistician s = new Statistician( );
		Statistician t = new Statistician( );

		for (int i = -5; i <= 10; i++) {
			s.insert(i);
		}
		for (int i = 10; i >= -5; i--) {
			t.insert(i);
		}
		assertTrue("t equals to s", s.equals(t));
		assertTrue("s equals to t", t.equals(s));

		s.insert(15);

		assertTrue("t not equal to s", !s.equals(t));
		assertTrue("s not equal to t", !t.equals(s));

		t.insert(-20);

		assertTrue("t not equal to s", !s.equals(t));
		assertTrue("s not equal to t", !t.equals(s));		
	}

	/**
	 * Creates statisticians with a lot of values and sees
	 * if that causes problems. But it doesn't try to
	 * overflow it.
	 */
	@Test
	public void compareALot() {
		Statistician s = new Statistician( );
		for (int i=1; i <= 10000; i++) {
			s.insert(i);
		}

		assertEquals("Mean too far off", 5000.5, s.mean( ), 0.0000001);
		assertTrue("Wrong count", s.length( ) == 10000);
	}

	/**
	 * Creates two statisticians, adds them, and checks answers
	 */
	@Test
	public void union() {
		Statistician s = new Statistician( );
		Statistician t = new Statistician( );
		Statistician w = s.union(t);

		assertTrue("All empty", (s.length( ) + t.length( ) + w.length( )) == 0);

		for (int i=1; i <= 3; i++) {
			s.insert(i);
		}
		for (int i=5; i <= 7; i++) {
			t.insert(i);
		}

		w =  s.union(t);

		assertTrue("Union not empty", w.length( ) == 6);
		assertTrue("Sums equal", w.sum( ) == (t.sum( ) + s.sum( )));

		double big = s.largest( ) > t.largest( ) ? s.largest( ) : t.largest( );
		assertEquals("Union has largest large", big, w.largest( ), 0.000001);

		double little = s.smallest( ) < t.smallest( ) ? s.smallest( ) : t.smallest( );
		assertEquals("Union has smallest small", little, w.smallest( ), 0.000001);
	}

	/**
	 * Creates two empty statisticians, unions them, and checks answers
	 */
	@Test
	public void unionEmpty() {

		Statistician s = new Statistician( );
		Statistician t = new Statistician( );
		Statistician w = s.union(t);

		assertTrue("All should be empty", (s.length( ) + t.length( ) + w.length( )) == 0);
		assertTrue("Largest NaN after empty union", Double.isNaN(w.largest( )));
		assertTrue("Smallest NaN after empty union", Double.isNaN(w.smallest( )));
	}

	/**
	 * Creates two empty statisticians, adds them, and checks answers
	 */
	@Test
	public void addEmpty() {

		Statistician s = new Statistician( );
		Statistician t = new Statistician( );
		s.add(t);

		assertTrue("Both should be empty", (s.length( ) + t.length( )) == 0);
		assertTrue("Largest NaN after empty union", Double.isNaN(s.largest( )));
		assertTrue("Smallest NaN after empty union", Double.isNaN(s.smallest( )));
	}


	/**
	 * Creates two statisticians, adds them, and checks largest and smallest
	 */
	@Test
	public void unionSmall() {

		Statistician s = new Statistician( );
		Statistician t = new Statistician( );
		Statistician w = new Statistician( );
		double value = 42.0;

		s.insert(value);  // s contains one

		s.add(w);   // add empty to s
		assertEquals("after adding empty w", value, s.smallest( ), 0.000001);
		assertEquals("after adding empty w", value, s.largest( ), 0.000001);

		t.add(s);   // add s to empty
		assertEquals("t smallest after add", value, t.smallest( ), 0.000001);
		assertEquals("t largest after add", value, t.largest( ), 0.000001);

		t.insert(value*2 + 10);
		w =  s.union(t);  // union the two

		assertTrue("Union not empty", w.length( ) == 3);
		assertTrue("Sums equal", w.sum( ) == (t.sum( ) + s.sum( )));

		double big = s.largest( ) > t.largest( ) ? s.largest( ) : t.largest( );
		assertEquals("Union has largest large", big, w.largest( ), 0.000001);

		double little = s.smallest( ) < t.smallest( ) ? s.smallest( ) : t.smallest( );
		assertEquals("Union has smallest small", little, w.smallest( ), 0.000001);
	}

	/**
	 * Creates two statisticians, adds them, and checks largest and smallest
	 */
	@Test
	public void addExtremes( ) {

		Statistician s = new Statistician( );
		Statistician t = new Statistician( );
		s.insert(17.17);
		s.insert(78.0);
		t.insert(4.0);
		t.insert(100.0);

		t.add(s);
		assertEquals("t smallest after add", 4.0, t.smallest( ), 0.000001);
		assertEquals("t largest after add", 100.0, t.largest( ), 0.000001);
	}

	/**
	 * Looks for bugs in the clone and equals methods
	 */
	@Test
	public void cloneEquals( ) {
		Statistician a = new Statistician( );
		Statistician b;

		a.insert(42);
		a.insert(-1.5);
		a.insert(0);
		a.insert(-0.5);
		b = a.clone( );
		assertTrue("clone should be equal", a.equals(b));
		assertTrue("clone should be equal", b.equals(a));
		assertFalse("clone should be separate object", a == b);
		b.insert(40);
		assertTrue("clone should not be equal after insert", !a.equals(b));		
	}

	/**
	 * Looks for bug in passing a null pointer to union
	 */
	@Test(expected=NullPointerException.class)
	public void unionException( ) {
		Statistician a = new Statistician( );
		Statistician b = null;

		a.union(b);		
	}

	/**
	 * Looks for bug in passing a null pointer to add
	 */
	@Test(expected=NullPointerException.class)
	public void addException( ) {
		Statistician a = new Statistician( );
		Statistician b = null;

		a.add(b);		
	}

}
