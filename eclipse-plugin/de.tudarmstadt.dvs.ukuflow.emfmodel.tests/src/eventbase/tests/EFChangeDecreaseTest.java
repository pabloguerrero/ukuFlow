/**
 */
package eventbase.tests;

import eventbase.EFChangeDecrease;
import eventbase.EventbaseFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>EF Change Decrease</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class EFChangeDecreaseTest extends EFChangeEventTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(EFChangeDecreaseTest.class);
	}

	/**
	 * Constructs a new EF Change Decrease test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFChangeDecreaseTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this EF Change Decrease test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EFChangeDecrease getFixture() {
		return (EFChangeDecrease)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(EventbaseFactory.eINSTANCE.createEFChangeDecrease());
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

} //EFChangeDecreaseTest
