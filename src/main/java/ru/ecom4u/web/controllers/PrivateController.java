package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ecom4u.web.controllers.dto.BreadcrumpDTO;
import ru.ecom4u.web.controllers.dto.accessory.HyperLink;
import ru.ecom4u.web.domain.db.entities.User;
import ru.ecom4u.web.domain.db.services.PersonService;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;
import ru.ecom4u.web.domain.db.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by Evgeny on 09.05.14.
 */
@Controller
@RequestMapping(value = "private")
public class PrivateController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(HttpServletRequest request, Locale locale, Model model) {
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        BreadcrumpDTO breadcrump = new BreadcrumpDTO();
        HyperLink hyperLink = new HyperLink(request.getContextPath() + "/private/", "Личный кабинет");
        breadcrump.addHyperLink(hyperLink);
        model.asMap().put("breadcrump", breadcrump);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmailOrLogin(auth.getName());
        model.asMap().put("person", user.getPerson());
        model.asMap().put("personContacts", personService.getPersonContacts(user.getPerson()));

        return "personal";
    }

    @RequestMapping(value = "orders",method = RequestMethod.GET)
    public String orders(Locale locale, Model model) {
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());
        return "myorders";
    }
}
