package core;

import junit.framework.TestCase;

public class ArithmeticSolverTest extends TestCase {

	public void testAdd() {
		ModelController solver = new ModelController();
		
		assertEquals(4.0, solver.add(2, 2));
	}
	
	public void testAddNegativeNumbers() {
		ModelController solver = new ModelController();
		
		assertEquals(-4.0, solver.add(-2, -2));
		assertEquals(-55.0, solver.add(-58, 3));
		assertEquals(10.0, solver.add(25, -15));
	}
	
	public void testAddLargeNumbers() {
		ModelController solver = new ModelController();
		
		//assertEquals(Double.MIN_VALUE, solver.add(Double.MAX_VALUE, 1));
		assertEquals(-58110005.0, solver.add(-58752887, 642882));
	}
	
	public void testAddDecimalNumbers() {
		ModelController solver = new ModelController();
		
		assertEquals(8.6, solver.add(5.3, 3.3));
		assertEquals(12.778, solver.add(9.445, 3.333));
		assertNotNull(solver.add(9.445, 3.333));
		
	}
	
	
	public void testSubtract() {
		ModelController solver = new ModelController();
		
		assertEquals(2.0, solver.subtract(4, 2));
	}
	
	public void testMultiply() {
		ModelController solver = new ModelController();
		
		assertEquals(16.0, solver.multiply(8, 2));
	}
	
	public void testDivide() {
		ModelController solver = new ModelController();
		
		assertEquals(3.0, solver.divide(9, 3));
	}
	
	public void testModulus() {
		ModelController solver = new ModelController();
		
		assertEquals(1.0, solver.modulus(5, 2));
	}
}
