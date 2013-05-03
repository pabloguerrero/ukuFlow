/**
 */
package eventbase.tests;

import eventbase.EGOffset;
import eventbase.EventbaseFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>EG Offset</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class EGOffsetTest extends EGNonRecurringTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(EGOffsetTest.class);
	}

	/**
	 * Constructs a new EG Offset test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EGOffsetTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this EG Offset test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EGOffset getFixture() {
		return (EGOffset)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(EventbaseFactory.eINSTANCE.createEGOffset());
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

} //EGOffsetTest
