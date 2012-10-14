package de.rahn.web.spring;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

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

		listDriver = new ArrayList<>();
		listDriver.add(driver);
	}

	/**
	 * Test method for {@link DriversController#handleRequest(Long)}.
	 */
	@Test
	public void testHandleRequestWithoutId() {
		given(drivers.getDriver(driver.getId())).willReturn(driver);

		Driver testDriver = controller.handleRequest(null);
		assertThat("Kein Fahrer geliefert", testDriver, notNullValue());
		assertThat("Dieser Fahrer darf keine Id haben", testDriver.getId(),
			nullValue());
	}

	/**
	 * Test method for {@link DriversController#handleRequest(Long)}.
	 */
	@Test
	public void testHandleRequestWithId() {
		given(drivers.getDriver(driver.getId())).willReturn(driver);

		Driver testDriver = controller.handleRequest(driver.getId());
		assertThat(testDriver, sameInstance(driver));
	}

	/**
	 * Test method for {@link DriversController#listDriver()}.
	 */
	@Test
	public void testListDriver() {
		given(drivers.getDrivers()).willReturn(listDriver);

		List<Driver> testDrivers = controller.listDriverForView();
		assertThat("Der Controller hat ein falsches Ergebnis geliefert",
			testDrivers, sameInstance(listDriver));
	}

	/**
	 * Test method for {@link DriversController#editDriver(Driver)}.
	 */
	@Test
	public void testEditDriver() {
		String model = controller.editDriver(driver);
		assertThat("Der Name der View ist nicht richtig", model, is("edit"));
	}

	/**
	 * Test method for {@link DriversController#saveDriver(Driver, Model)}.
	 */
	@Test
	public void testSaveDriver() {
		given(drivers.save(driver)).willReturn(driver);
		given(drivers.create(driver)).willReturn(1L);
		given(drivers.getDrivers()).willReturn(listDriver);

		// Ruft save() und getDrivers() auf
		Model model = new ExtendedModelMap();
		List<Driver> testDrivers = controller.saveDriver(driver, model);
		assertThat(testDrivers, sameInstance(listDriver));
		assertThat("Die Variable für die Oberfläche ist nicht gefüllt",
			model.asMap(), hasKey("statusMessage"));
		verify(drivers, times(2)).getDrivers();

		// Ruft create() und getDrivers() auf
		driver.setId(null);
		model = new ExtendedModelMap();
		testDrivers = controller.saveDriver(driver, model);
		assertThat(testDrivers, sameInstance(listDriver));
		assertThat("Die Variable für die Oberfläche ist nicht gefüllt",
			model.asMap(), hasKey("statusMessage"));
		verify(drivers, times(3)).getDrivers();
	}

	/**
	 * Test method for {@link DriversController#handleException(Exception)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testHandleException() {
		NullPointerException exception = new NullPointerException("Test");
		exception.fillInStackTrace();

		ModelAndView modelAndView = controller.handleException(exception);
		assertThat("Kein Model und View geliefert", modelAndView,
			notNullValue());
		assertThat("Viewname ist nicht richtig", modelAndView.getViewName(),
			is("error"));
		assertThat("Die Attribute sind nicht richtig",
			modelAndView.getModelMap(),
			allOf(hasEntry("message", (Object) "Test"), hasKey("stackTrace")));
	}

}