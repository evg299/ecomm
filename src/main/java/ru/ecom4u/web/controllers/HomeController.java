package ru.ecom4u.web.controllers;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.ecom4u.web.domain.db.services.UserService;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	public UserService userService;

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public String product(Locale locale, Model model) {
		return "product";
	}

	@RequestMapping(value = "/card", method = RequestMethod.GET)
	public String card(Locale locale, Model model) {
		return "card";
	}

	@RequestMapping(value = "/myorders", method = RequestMethod.GET)
	public String myorders(Locale locale, Model model) {
		return "myorders";
	}

	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public String personal(Locale locale, Model model) {
		return "personal";
	}

}
