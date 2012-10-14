package de.rahn.web.spring;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

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
	 * Test method for {@link SampleController#sample(Model)} .
	 */
	@Test
	public void testSample() {
		Model model = new ExtendedModelMap();
		controller.sample(model);

		assertThat("Attribut counter im ModelMap", model.asMap(),
			hasEntry("counter", (Object) new Integer(1)));
	}

	/**
	 * Test method for {@link SampleController#createError()} .
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateError() {
		controller.createError();
	}

	/**
	 * Test method for {@link SampleController#handleException(Exception)} .
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testHandleException() {
		Exception exception = new Exception("Test");
		exception.fillInStackTrace();

		ModelAndView modelAndView = controller.handleException(exception);
		assertThat("Keine Model & View zurückgeliefert", modelAndView,
			notNullValue());
		assertThat("Falscher View-Name", modelAndView.getViewName(),
			is("error"));

		Map<String, Object> model = modelAndView.getModel();
		assertThat("Meldung im Model", model,
			allOf(hasEntry("message", (Object) "Test"), hasKey("stackTrace")));
	}

}