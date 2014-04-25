package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ecom4u.web.controllers.dto.forms.RegistrationForm;
import ru.ecom4u.web.controllers.validators.RegistrationValidator;
import ru.ecom4u.web.domain.db.entities.User;
import ru.ecom4u.web.domain.db.services.AuthenticationService;
import ru.ecom4u.web.domain.db.services.UserService;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping(value = "registration")
public class RegistrationController extends AbstractController {

    @Autowired
    private RegistrationValidator registrationValidator;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String main(Locale locale, Model model) {
        globalModelService.populateModel(model);
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
            try {
                authenticationService.registerNewUser(registrationForm);
                return "redirect:/";
            } catch (Throwable t) {
                model.addAttribute("icon", "q1w2");
                model.addAttribute("messageTitle", "Fail");
                model.addAttribute("messageText", "System fail, try later");
                return "sysmsg";
            }
        }
    }

    @RequestMapping(value = "confirm/{userId}/{confirmCode}", method = RequestMethod.GET)
    public String confirm(@PathVariable(value = "userId") Integer userId,
                          @PathVariable(value = "confirmCode") String confirmCode, Locale locale, Model model) {
        User user = userService.getById(userId);
        if (null != user && null != confirmCode && confirmCode.equalsIgnoreCase(user.getConfirmedCode())) {
            user.setConfirmedFlag(true);
            userService.save(user);

            model.addAttribute("icon", "q1w2");
            model.addAttribute("messageTitle", "Fail");
            model.addAttribute("messageText", "System fail, try later");
        } else {
            model.addAttribute("icon", "q1w2");
            model.addAttribute("messageTitle", "Fail");
            model.addAttribute("messageText", "System fail, try later");
        }
        return "sysmsg";
    }
}
