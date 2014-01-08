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
import ru.ecom4u.web.services.UsersService;

@Controller
@RequestMapping(value = "registration")
public class RegistrationController {

	@Autowired
	private RegistrationValidator registrationValidator;
	@Autowired
	private UsersService usersService;

	@RequestMapping(method = RequestMethod.GET)
	public String main(Locale locale, Model model) {
		model.asMap().put("regform", new RegistrationForm());
		return "registration";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registration(@Valid @ModelAttribute("regform") RegistrationForm registrationForm,
			BindingResult result, Locale locale, Model model) {
		registrationValidator.validate(registrationForm, result);
		if (result.hasErrors()) {
			return "registration";
		} else {
			if (usersService.registerNewUser(registrationForm))
				return "redirect:/";
			else {
				model.addAttribute("icon", "q1w2");
				model.addAttribute("messageTitle", "Fail");
				model.addAttribute("messageText", "System fail, try later");
				return "sysmsg";
			}
		}
	}
}
