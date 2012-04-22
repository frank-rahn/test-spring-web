package de.rahn.web.spring;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.rahn.services.drivers.Drivers;
import de.rahn.services.drivers.entity.Driver;

/**
 * Der Controller für die Operationen auf einen Fahrer des Service Drivers.
 * @author Frank W. Rahn
 */
@Controller
@RequestMapping("/drivers/{id}")
public class DriverController {

	private static final Logger logger = getLogger(DriverController.class);

	@Autowired
	private Drivers drivers;

	/**
	 * Lese einen Fahrer.
	 * @param id die Id eines Fahrers
	 * @return der existierende Fahrer
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Driver> getDriver(@PathVariable long id) {
		logger
			.info(
				"Die Methode DriverController.getDriver() wurde mit aufgerufen. id={}",
				id);

		Driver driver = drivers.getDriver(id);
		if (driver == null) {
			return new ResponseEntity<Driver>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Driver>(driver, HttpStatus.OK);
	}

	/**
	 * Speichere einen Fahrer.
	 * @param id die Id eines Fahrers
	 * @param driver der aktuelle Fahrer
	 * @return der geänderte Fahrer
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Driver> saveDriver(@PathVariable long id,
		@RequestBody Driver driver) {
		logger
			.info(
				"Die Methode DriverController.saveDriver() wurde mit aufgerufen. Id={}, driver={}",
				id, driver);

		if (driver.getId() != null && !driver.getId().equals(id)) {
			// URI und Fahrer stimmen nicht überein => 409 Conflict
			return new ResponseEntity<Driver>(HttpStatus.CONFLICT);
		}

		// Normaler Rückgabestatus
		HttpStatus status = HttpStatus.OK;

		if (driver.getId() == null) {
			// Rückgabe: 201 Created
			status = HttpStatus.CREATED;
			driver.setId(id);
		}

		// Speichere den Fahrer
		driver = drivers.save(driver);

		return new ResponseEntity<Driver>(driver, status);
	}

	/**
	 * Speichere einen Fahrer.
	 * @param driver der aktuelle Fahrer
	 * @return der existierende Fahrer
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteDriver(@PathVariable long id) {
		logger
			.info(
				"Die Methode DriverController.deleteDriver() wurde mit aufgerufen. Id={}",
				id);

		Driver driver = drivers.getDriver(id);
		if (driver != null) {
			drivers.deleteDriver(id);
		}
	}

}