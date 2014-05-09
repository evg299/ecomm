package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;

import java.util.Locale;

/**
 * Created by Evgeny on 09.05.14.
 */
@Controller
@RequestMapping(value = "private")
public class PrivateController {

    @Autowired
    private SitePropertiesService sitePropertiesService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));

        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());
        return "personal";
    }

    @RequestMapping(value = "orders",method = RequestMethod.GET)
    public String orders(Locale locale, Model model) {
        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));

        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());
        return "myorders";
    }
}
