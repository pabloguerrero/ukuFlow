/**
 */
package eventbase.tests;

import eventbase.EFLogicNot;
import eventbase.EventbaseFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>EF Logic Not</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class EFLogicNotTest extends EFLogicTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(EFLogicNotTest.class);
	}

	/**
	 * Constructs a new EF Logic Not test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFLogicNotTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this EF Logic Not test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EFLogicNot getFixture() {
		return (EFLogicNot)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(EventbaseFactory.eINSTANCE.createEFLogicNot());
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

} //EFLogicNotTest
