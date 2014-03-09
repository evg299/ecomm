package ru.ecom4u.web.controllers;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.ecom4u.web.domain.db.services.SitePropertiesService;
import ru.ecom4u.web.domain.db.services.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	public UserService userService;

    @Autowired
    private SitePropertiesService sitePropertiesService;

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public String product(Locale locale, Model model,
                          @CookieValue(value = "lastvisited", required = false) String lastVisitedIds,
                          HttpServletResponse response) {
        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));

        if(null == lastVisitedIds || lastVisitedIds.isEmpty()) {
            lastVisitedIds = "1";
        } else {
            lastVisitedIds += ":1";
        }


        response.addCookie(new Cookie("lastvisited", lastVisitedIds));
		return "product";
	}

	@RequestMapping(value = "/card", method = RequestMethod.GET)
	public String card(Locale locale, Model model) {
        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));
		return "card";
	}

	@RequestMapping(value = "/myorders", method = RequestMethod.GET)
	public String myorders(Locale locale, Model model) {
        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));
		return "myorders";
	}

	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public String personal(Locale locale, Model model) {
        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));
		return "personal";
	}

}
