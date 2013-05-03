/**
 */
package eventbase.tests;

import eventbase.EFProcessingStDev;
import eventbase.EventbaseFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>EF Processing St Dev</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class EFProcessingStDevTest extends EFProcessingTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(EFProcessingStDevTest.class);
	}

	/**
	 * Constructs a new EF Processing St Dev test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFProcessingStDevTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this EF Processing St Dev test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EFProcessingStDev getFixture() {
		return (EFProcessingStDev)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(EventbaseFactory.eINSTANCE.createEFProcessingStDev());
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

} //EFProcessingStDevTest
