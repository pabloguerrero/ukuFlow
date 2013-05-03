/**
 */
package eventbase.tests;

import eventbase.EFProcessingSum;
import eventbase.EventbaseFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>EF Processing Sum</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class EFProcessingSumTest extends EFProcessingTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(EFProcessingSumTest.class);
	}

	/**
	 * Constructs a new EF Processing Sum test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFProcessingSumTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this EF Processing Sum test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EFProcessingSum getFixture() {
		return (EFProcessingSum)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(EventbaseFactory.eINSTANCE.createEFProcessingSum());
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

} //EFProcessingSumTest
