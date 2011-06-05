package de.rahn.web.spring;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Ein beispielhafter Controller.
 * @author Frank W. Rahn
 */
@Controller
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	private int counter = 0;

	/**
	 * Zeige die Beispielseite an.
	 * @param model In diesem Modell werden die Daten abgelegt
	 */
	@RequestMapping("/sample")
	public void sample(Model model) {
		logger.info("Die Methode SampleController.sample() wurde aufgerufen.");
		model.addAttribute("counter", ++counter);
	}

	/**
	 * Erzeuge einen Fehler.
	 */
	@RequestMapping("/erzeugeFehler")
	public void createError() {
		logger.info("Die Methode SampleController.createError() wurde aufgerufen.");
		throw new NullPointerException("Ein Fehler ist aufgetreten!");
	}

	/**
	 * Mit dieser Methode werden die Fehler angezeigt.
	 * @param exception die Ausnahme zum Fehler
	 * @return die Kombination aus Anzeige (View) und Daten (Model)
	 */
	@ExceptionHandler
	public ModelAndView handleException(Exception exception) {
		StringWriter writer = new StringWriter();
		exception.printStackTrace(new PrintWriter(writer));
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("message", exception.getMessage());
		modelAndView.addObject("stackTrace", writer.toString());
		return modelAndView;
	}

}