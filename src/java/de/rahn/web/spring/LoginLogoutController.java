package de.rahn.web.spring;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Der Controller für das Login und Logout.
 * @author Frank W. Rahn
 */
@Controller
public class LoginLogoutController {

	private static final Logger logger = getLogger(LoginLogoutController.class);

	// @Autowired
	// private HttpServletRequest request;

	/**
	 * Die Methode für das Login.
	 */
	@RequestMapping("/login")
	public void login() {
		logger.info("Benutzer hat die Anmeldeseite aufgerufen.");
	}

	/**
	 * Die Methode für das Logout.
	 */
	@RequestMapping("/logouta")
	public void logout() throws Exception {
		logger.info("Benutzer hat die Abmeldeseite aufgerufen.");
		// HttpServletResponse response
		// request.getSession().invalidate();
		// response.sendRedirect(request.getContextPath());
	}

}