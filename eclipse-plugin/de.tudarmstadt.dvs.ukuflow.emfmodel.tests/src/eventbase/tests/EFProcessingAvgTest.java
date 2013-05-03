/**
 */
package eventbase.tests;

import eventbase.EFProcessingAvg;
import eventbase.EventbaseFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>EF Processing Avg</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class EFProcessingAvgTest extends EFProcessingTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(EFProcessingAvgTest.class);
	}

	/**
	 * Constructs a new EF Processing Avg test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFProcessingAvgTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this EF Processing Avg test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EFProcessingAvg getFixture() {
		return (EFProcessingAvg)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(EventbaseFactory.eINSTANCE.createEFProcessingAvg());
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

} //EFProcessingAvgTest
