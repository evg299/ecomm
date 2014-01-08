package ru.ecom4u.web.controllers.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.ecom4u.web.controllers.dto.forms.RegistrationForm;
import ru.ecom4u.web.domain.db.dao.UserDao;
import ru.ecom4u.web.domain.db.entities.User;

@Component
public class RegistrationValidator implements Validator {

	@Autowired
	public UserDao userDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return RegistrationForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegistrationForm form = (RegistrationForm) target;

		if (!form.getPwd1().equals(form.getPwd2()))
			errors.reject("regform.nePwds");

		if (!form.isAcceptRules())
			errors.reject("regform.capacity");

		User user = userDao.getByEmail(form.getEmail());
		if (null != user) 
			errors.reject("regform.emailExist");
	}

}
