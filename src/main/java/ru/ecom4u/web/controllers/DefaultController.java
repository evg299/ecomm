package ru.ecom4u.web.controllers;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ecom4u.web.controllers.dto.forms.RegistrationForm;
import ru.ecom4u.web.domain.db.entities.ProductCategory;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;
import ru.ecom4u.web.utils.BreadcrumpUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DefaultController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private SitePropertiesService sitePropertiesService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
        List<ProductCategory> subCategories = productCategoryService.getRootProductCategories();
        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", subCategories);
		return "main";
	}

	@RequestMapping(value = "/{urlCategory}", method = RequestMethod.GET)
	public String categoryDefault(@PathVariable(value = "urlCategory") String urlCategory,
			@RequestParam(value = "order", required = false) String order, Locale locale, Model model, HttpServletRequest request) {
		System.out.println(urlCategory + " ---> " + order);

        ProductCategory currentCategory = productCategoryService.getByUrlName(urlCategory);
        List<ProductCategory> subCategories = productCategoryService.getSubProductCategories(currentCategory);

        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));
        model.asMap().put("categoryName", currentCategory.getName());
        model.asMap().put("subCategories", subCategories);
        model.asMap().put("breadcrump", BreadcrumpUtil.createByProductCategory(currentCategory, request));

		return "category";
	}

	@RequestMapping(value = "/{urlCategory}/{page}", method = RequestMethod.GET)
	public String category(@PathVariable(value = "urlCategory") String urlCategory,
			@PathVariable(value = "page") Integer page, @RequestParam(value = "order", required = false) String order) {
		System.out.println(urlCategory + " ---> " + page + " : " + order);
		return "category";
	}
}
