package de.rahn.web.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

/**
 * Test für den Controller.
 * @author Frank W. Rahn
 */
public class SampleControllerTest {

	private SampleController controller;

	@Before
	public void setUp() {
		controller = new SampleController();
	}

	/**
	 * Test method for
	 * {@link SampleController#sample(org.springframework.ui.Model)} .
	 */
	@Test
	public void testSample() {
		Model model = new ExtendedModelMap();
		controller.sample(model);

		assertTrue("Attribut counter fehlt",
			model.asMap().containsKey("counter"));
		assertEquals("Attribut counter falsch gesetzt", new Integer(1), model
			.asMap().get("counter"));
	}

	/**
	 * Test method for {@link SampleController#createError()} .
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateError() {
		controller.createError();
	}

	/**
	 * Test method for
	 * {@link SampleController#handleException(java.lang.Exception)} .
	 */
	@Test
	public void testHandleException() {
		Exception exception = new Exception("Test");
		exception.fillInStackTrace();

		ModelAndView modelAndView = controller.handleException(exception);

		assertNotNull("Keine Model & View zurückgeliefert", modelAndView);
		assertEquals("Falscher View-Name", "error", modelAndView.getViewName());
		Map<String, Object> model = modelAndView.getModel();
		assertNotNull("Model ist nicht vorhanden", model);
		assertNotNull("Meldung ist nicht vorhanden",
			model.containsKey("message"));
		assertEquals("Meldung ist falsch", "Test", model.get("message"));
		assertNotNull("Stacktrace ist nicht vorhanden",
			model.containsKey("stackTrace"));
	}

}