/**
 */
package eventbase.tests;

import eventbase.EFChangeEvent;
import eventbase.EventbaseFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>EF Change Event</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class EFChangeEventTest extends EFCompositeTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(EFChangeEventTest.class);
	}

	/**
	 * Constructs a new EF Change Event test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFChangeEventTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this EF Change Event test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EFChangeEvent getFixture() {
		return (EFChangeEvent)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(EventbaseFactory.eINSTANCE.createEFChangeEvent());
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

} //EFChangeEventTest
