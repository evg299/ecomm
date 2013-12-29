package ru.ecom4u.web.controllers;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.ecom4u.web.controllers.dto.forms.RegistrationForm;
import ru.ecom4u.web.controllers.validators.RegistrationValidator;

@Controller
@RequestMapping(value = "registration")
public class RegistrationController {

	@Autowired
	private RegistrationValidator registrationValidator;

	@RequestMapping(method = RequestMethod.GET)
	public String main(Locale locale, Model model) {
		model.asMap().put("regform", new RegistrationForm());
		return "registration";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registration(
			@Valid @ModelAttribute("regform") RegistrationForm registrationForm,
			BindingResult result, Locale locale, Model model) {

		return "registration";
	}
}
