package ru.ecom4u.web.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "main";
	}

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String category(Locale locale, Model model) {
		return "category";
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public String product(Locale locale, Model model) {
		return "product";
	}

	@RequestMapping(value = "/static", method = RequestMethod.GET)
	public String staticPage(Locale locale, Model model) {
		return "static";
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
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Locale locale, Model model) {
		return "registration";
	}

}
