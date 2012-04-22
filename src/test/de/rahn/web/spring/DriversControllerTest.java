package de.rahn.web.spring;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import de.rahn.services.drivers.Drivers;
import de.rahn.services.drivers.entity.Driver;

/**
 * Der Test für den Controller der Fahrerverwaltung.
 * @author Frank W. Rahn
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DriversControllerTest {

	@Autowired
	private DriversController controller;

	@Autowired
	private Drivers drivers;

	private Driver driver;

	private List<Driver> listDriver;

	/**
	 * Diese Methode wird vor jedem Unit Test aufgerufen.
	 */
	@Before
	public void setUp() {
		driver = new Driver();
		driver.setId(0L);
		driver.setFirstname("Frank");
		driver.setName("Rahn");

		listDriver = new ArrayList<Driver>();
		listDriver.add(driver);
	}

	/**
	 * Diese Methode wird nach jedem Unit Test aufgerufen.
	 */
	@After
	public void tearDown() {
		reset(drivers);
	}

	/**
	 * Test method for {@link DriversController#handleRequest(Long)}.
	 */
	@Test
	public void testHandleRequest() {
		expect(drivers.getDriver(driver.getId())).andReturn(driver);
		replay(drivers);

		Driver testDriver = controller.handleRequest(null);
		assertNotNull("Kein Fahrer geliefert", testDriver);
		assertNull("Dieser Fahrer darf keine Id haben", testDriver.getId());

		testDriver = controller.handleRequest(driver.getId());
		assertNotNull("Kein Fahrer geliefert", testDriver);
		assertSame("Die Ergebnisse sind unterschiedlich", driver, testDriver);
		verify(drivers);
	}

	/**
	 * Test method for {@link DriversController#listDriver()}.
	 */
	@Test
	public void testListDriver() {
		expect(drivers.getDrivers()).andReturn(listDriver);
		replay(drivers);

		List<Driver> testDrivers = controller.listDriverForView();
		assertNotNull("Controller hat kein Ergebnis geliefert", testDrivers);
		assertSame("Die Ergebnisse sind unterschiedlich", listDriver,
			testDrivers);
		verify(drivers);
	}

	/**
	 * Test method for {@link DriversController#editDriver(Driver)}.
	 */
	@Test
	public void testEditDriver() {
		String model = controller.editDriver(driver);
		assertNotNull("Der Name der View ist nicht da", model);
		assertEquals("Der Name der View ist nicht richtig", "edit", model);
	}

	/**
	 * Test method for {@link DriversController#saveDriver(Driver, Model)}.
	 */
	@Test
	public void testSaveDriver() {
		expect(drivers.save(driver)).andReturn(driver);
		expect(drivers.create(driver)).andReturn(1L);
		expect(drivers.getDrivers()).andReturn(listDriver).times(2);
		replay(drivers);

		Model model = new ExtendedModelMap();

		// Ruft save() und getDrivers() auf
		List<Driver> testDrivers = controller.saveDriver(driver, model);
		assertNotNull("Controller hat kein Ergebnis geliefert", testDrivers);
		assertSame("Die Ergebnisse sind unterschiedlich", listDriver,
			testDrivers);
		assertTrue("Die Variable für die Oberfläche ist nicht gefüllt",
			model.containsAttribute("statusMessage"));

		driver.setId(null);
		model = new ExtendedModelMap();
		// Ruft create() und getDrivers() auf
		testDrivers = controller.saveDriver(driver, model);
		assertNotNull("Controller hat kein Ergebnis geliefert", testDrivers);
		assertSame("Die Ergebnisse sind unterschiedlich", listDriver,
			testDrivers);
		assertTrue("Die Variable für die Oberfläche ist nicht gefüllt",
			model.containsAttribute("statusMessage"));
		verify(drivers);
	}

	/**
	 * Test method for {@link DriversController#handleException(Exception)}.
	 */
	@Test
	public void testHandleException() {
		NullPointerException exception = new NullPointerException("Test");
		exception.fillInStackTrace();

		ModelAndView modelAndView = controller.handleException(exception);
		assertNotNull("Kein Model und View geliefert", modelAndView);
		assertEquals("Viewname ist nicht richtig", "error",
			modelAndView.getViewName());
		assertEquals("Die Message ist nicht richtig", "Test", modelAndView
			.getModelMap().get("message"));
		assertTrue("Der Stacktrace fehlt", modelAndView.getModelMap()
			.containsAttribute("stackTrace"));
	}

}