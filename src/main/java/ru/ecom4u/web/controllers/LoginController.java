package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.services.GlobalModelService;

/**
 * Created by Evgeny on 06.05.2014.
 */
@Controller
public class LoginController {
    @Autowired
    private GlobalModelService globalModelService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String home(Model model) {
        globalModelService.populateModel(model);

        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        return "login";
    }
}
