package de.rahn.web.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

/**
 * Zeige Informationen über den {@link ApplicationContext}.
 * @author Frank W. Rahn
 */
@Controller
@RequestMapping("/info")
public class ApplicationContextInfoController {

	/**
	 * Das Model für die Informationen über den {@link ApplicationContext}.
	 * @author Frank W. Rahn
	 */
	public static class AppCtx {

		/** Die Id des ApplicationContexts. */
		private String id;

		/** Die Name des ApplicationContexts. */
		private String displayName;

		/** Die Klasse des ApplicationContexts. */
		private String appCtxClass;

		/** Die Daten der Beans des ApplicationContexts. */
		private List<List<String>> beans;

		/**
		 * Konstruktor.
		 */
		public AppCtx(String id, String displayName) {
			super();

			setId(id);
			setDisplayName(displayName);
		}

		/**
		 * @param bean Füge die Daten eines beans hinzu
		 */
		public void addBean(List<String> bean) {
			if (beans == null) {
				beans = new ArrayList<List<String>>();
			}
			beans.add(bean);
		}

		// Ab hier von Eclipse generierte Setter und Getter

		/**
		 * @param displayName the displayName to set
		 */
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		/**
		 * @return the displayName
		 */
		public String getDisplayName() {
			return displayName;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param appCtxClass the appCtxClass to set
		 */
		public void setAppCtxClass(String appCtxClass) {
			this.appCtxClass = appCtxClass;
		}

		/**
		 * @return the appCtxClass
		 */
		public String getAppCtxClass() {
			return appCtxClass;
		}

		/**
		 * @param beans the beans to set
		 */
		public void setBeans(List<List<String>> beans) {
			this.beans = beans;
		}

		/**
		 * @return the beans
		 */
		public List<List<String>> getBeans() {
			return beans;
		}

	}

	@Autowired
	private WebApplicationContext applicationContext;

	/**
	 * Ermittle die Informationen über die verschachtelte
	 * {@link ApplicationContext}.
	 * @return das Model
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ModelAttribute("appCtxs")
	public List<AppCtx> showApplicationContextInfo() {
		List<AppCtx> appCtxs = new ArrayList<AppCtx>();
		ApplicationContext current = applicationContext;

		// Ermitteln der verschachtelten ApplicationContexte
		while (current != null) {
			AppCtx appCtx =
				new AppCtx(current.getId(), current.getDisplayName());
			appCtx.setAppCtxClass(current.getClass().getName());
			appCtxs.add(appCtx);

			// Ermittlung der geladenen Beans
			Map<String, List<String>> beans =
				new HashMap<String, List<String>>();
			for (String name : current.getBeanDefinitionNames()) {
				List<String> bean = new ArrayList<String>();

				// Bean Class
				Class<?> type = current.getType(name);
				bean.add(type.getName());
				beans.put(type.getName(), bean);

				// Bean Id
				bean.add(name);

				// Bean Aliase
				String[] aliase = current.getAliases(name);
				if (aliase != null) {
					StringBuilder builder = new StringBuilder();
					for (String alias : aliase) {
						builder.append(alias).append(' ');
					}
					bean.add(builder.toString());
				} else {
					bean.add("&nbsp;");
				}
			}

			// Beans nach Klasse sortieren
			List<String> keys = new ArrayList<String>(beans.keySet());
			Collections.sort(keys);

			for (String key : keys) {
				appCtx.addBean(beans.get(key));
			}

			// Nächster ApplicationContext
			current = current.getParent();
		}

		return appCtxs;
	}

}
