package ru.ecom4u.web.domain.db.services;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.ecom4u.web.controllers.dto.forms.RegistrationForm;
import ru.ecom4u.web.domain.db.entities.Person;
import ru.ecom4u.web.domain.db.entities.Role;
import ru.ecom4u.web.domain.db.entities.User;
import ru.ecom4u.web.services.HasherService;
import ru.ecom4u.web.services.MailService;

@Service
public class AuthenticationService extends AbstractService {
	@Autowired
	private HasherService hasherService;

	@Autowired
	private MailService mailService;

	@Transactional(rollbackFor = Throwable.class)
	public void registerNewUser(RegistrationForm form) {
		Session session = getCurrentSession();

		Role userRole = (Role) session.createCriteria(Role.class)
				.add(Restrictions.eq("name", Role.MainRoles.USER.toString()).ignoreCase()).uniqueResult();

		// код подтверждения
		String confirmedCode = UUID.randomUUID().toString();

		User user = new User();
		user.setLogin(form.getEmail());
		user.setEmail(form.getEmail());
		user.setConfirmedFlag(new Boolean(false));
		user.setHashPassord(hasherService.calculateHash(form.getPwd1()));
		user.setRole(userRole);
		user.setConfirmedCode(confirmedCode);
		session.save(user);

		Person person = new Person();
		person.setFname(form.getFname());
		person.setLname(form.getLname());
		person.setUser(user);
		session.save(person);

		// формирование email письма
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		System.out.println(request.getPathInfo());

		String tpl = mailService.getTemplate("registration", LocaleContextHolder.getLocale());
		Map<String, String> tokenMap = new TreeMap<String, String>();
		tokenMap.put("rootUrl", request.getPathInfo());
		tokenMap.put("email", form.getEmail());
		tokenMap.put("fname", form.getFname());
		tokenMap.put("lname", form.getLname());
		tokenMap.put("confirmedCode", confirmedCode);

		String content = replaceTokens(tokenMap, tpl);

		mailService.sendEmail(form.getEmail(), "reg", content);
	}

	private static final String WRAPPER_TPL = "__#%s__";

	private String replaceTokens(Map<String, String> tokenMap, String tpl) {
		for (Entry<String, String> tokenEntry : tokenMap.entrySet()) {
			tpl = tpl.replaceAll(String.format(WRAPPER_TPL, tokenEntry.getKey()), tokenEntry.getValue());
		}

		return tpl;
	}
}
