/**
 */
package eventbase.tests;

import eventbase.EFTemporalSequence;
import eventbase.EventbaseFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>EF Temporal Sequence</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class EFTemporalSequenceTest extends EFTemporalTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(EFTemporalSequenceTest.class);
	}

	/**
	 * Constructs a new EF Temporal Sequence test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFTemporalSequenceTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this EF Temporal Sequence test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EFTemporalSequence getFixture() {
		return (EFTemporalSequence)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(EventbaseFactory.eINSTANCE.createEFTemporalSequence());
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

} //EFTemporalSequenceTest
