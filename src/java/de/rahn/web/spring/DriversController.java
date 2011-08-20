package de.rahn.web.spring;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.rahn.services.drivers.Drivers;
import de.rahn.services.drivers.entity.Driver;

/**
 * Der Controller für den Service Drivers.
 * @author Frank W. Rahn
 */
@Controller
@RequestMapping("/drivers")
public class DriversController {

	private static final Logger logger = getLogger(DriversController.class);

	@Autowired
	private Drivers drivers;

	/**
	 * Diese Methode wird vor jedem Request aufgerufen.
	 * @param id die Id eines Fahrers oder <code>null</code>
	 * @return der existierende oder ein neuer Fahrer
	 */
	@ModelAttribute("driver")
	public Driver handleRequest(@RequestParam(required = false) Long id) {
		logger
			.info(
				"Die Methode DriversController.handleRequest() wurde aufgerufen. id={}",
				id);

		if (id != null) {
			return drivers.getDriver(id);
		}

		return new Driver();
	}

	/**
	 * Liste alle Fahrer auf.
	 * @return die Liste der Fahrer
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ModelAttribute("drivers")
	public List<Driver> listDriver() {
		logger
			.info("Die Methode DriversController.listDriver() wurde aufgerufen.");

		return drivers.getDrivers();
	}

	/**
	 * Bereite die View für das Bearbeiten bzw. Anlegen des Fahrer vor.
	 * @param driver der aktuelle Fahrer
	 * @return der Namen der View (default ist: "drivers/edit")
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editDriver(Driver driver) {
		logger
			.info(
				"Die Methode DriversController.editDriver() wurde aufgerufen. driver={}",
				driver);
		return "edit";
	}

	/**
	 * Speichere einen Fahrer.
	 * @param driver der aktuelle Fahrer
	 * @param model das Modell
	 * @return die Liste der Fahrer
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ModelAttribute("drivers")
	public List<Driver> saveDriver(Driver driver, Model model) {
		logger
			.info(
				"Die Methode DriversController.saveDriver() wurde mit aufgerufen. driver={}",
				driver);

		if (driver.getId() == null) {
			drivers.create(driver);
		} else {
			drivers.save(driver);
		}

		model.addAttribute("statusMessage",
			"Der Fahrer mit der Id " + driver.getId() + " wurde gespeichert.");
		return drivers.getDrivers();
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