package ru.ecom4u.web.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.ecom4u.web.controllers.dto.BreadcrumpDTO;
import ru.ecom4u.web.controllers.dto.accessory.HyperLink;
import ru.ecom4u.web.domain.db.entities.StaticPage;
import ru.ecom4u.web.domain.db.services.StaticPageService;

@Controller
@RequestMapping(value = "static")
public class StaticController {

	@Autowired
	private StaticPageService staticPageService;

	@RequestMapping(value = "{urlName}", method = RequestMethod.GET)
	public String getStaticPage(@PathVariable(value = "urlName") String urlName, Locale locale, Model model,
			HttpServletRequest request) {
		StaticPage staticPage = staticPageService.getStaticPageByUrlName(urlName);

		if (null != staticPage) {
			BreadcrumpDTO breadcrumpDTO = new BreadcrumpDTO();
			breadcrumpDTO.addHyperLink(new HyperLink(request.getContextPath() + "/static/" + urlName, staticPage.getTitle()));
			model.addAttribute("breadcrump", breadcrumpDTO);
			model.addAttribute("title", staticPage.getTitle());
			model.addAttribute("htmlContent", staticPage.getHtmlContent());

			return "static";
		} else {
			return "redirect:/";
		}
	}
}
