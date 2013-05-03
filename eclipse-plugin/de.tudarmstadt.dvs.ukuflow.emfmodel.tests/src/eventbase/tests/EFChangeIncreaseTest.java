/**
 */
package eventbase.tests;

import eventbase.EFChangeIncrease;
import eventbase.EventbaseFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>EF Change Increase</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class EFChangeIncreaseTest extends EFChangeEventTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(EFChangeIncreaseTest.class);
	}

	/**
	 * Constructs a new EF Change Increase test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFChangeIncreaseTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this EF Change Increase test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EFChangeIncrease getFixture() {
		return (EFChangeIncrease)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(EventbaseFactory.eINSTANCE.createEFChangeIncrease());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //EFChangeIncreaseTest
