/**
 */
package eventbase.tests;

import eventbase.EPeriodicEG;
import eventbase.EventbaseFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>EPeriodic EG</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class EPeriodicEGTest extends EventBaseOperatorTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(EPeriodicEGTest.class);
	}

	/**
	 * Constructs a new EPeriodic EG test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPeriodicEGTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this EPeriodic EG test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPeriodicEG getFixture() {
		return (EPeriodicEG)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(EventbaseFactory.eINSTANCE.createEPeriodicEG());
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

} //EPeriodicEGTest
