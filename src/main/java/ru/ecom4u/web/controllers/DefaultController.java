package ru.ecom4u.web.controllers;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaultController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "main";
	}

	@RequestMapping(value = "/{urlCategory}", method = RequestMethod.GET)
	public String categoryDefault(@PathVariable(value = "urlCategory") String urlCategory,
			@RequestParam(value = "order", required = false) String order) {
		System.out.println(urlCategory + " ---> " + order);
		return "category";
	}

	@RequestMapping(value = "/{urlCategory}/{page}", method = RequestMethod.GET)
	public String category(@PathVariable(value = "urlCategory") String urlCategory,
			@PathVariable(value = "page") Integer page, @RequestParam(value = "order", required = false) String order) {
		System.out.println(urlCategory + " ---> " + page + " : " + order);
		return "category";
	}
}
