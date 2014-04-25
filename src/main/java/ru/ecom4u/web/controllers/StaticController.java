package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ecom4u.web.controllers.dto.BreadcrumpDTO;
import ru.ecom4u.web.domain.db.entities.StaticPage;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.StaticPageService;
import ru.ecom4u.web.utils.BreadcrumpUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "static")
public class StaticController extends AbstractController {

    @Autowired
    private StaticPageService staticPageService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "{urlName}", method = RequestMethod.GET)
    public String getStaticPage(@PathVariable(value = "urlName") String urlName, Model model,
                                HttpServletRequest request) {
        StaticPage staticPage = staticPageService.getStaticPageByUrlName(urlName);

        if (null != staticPage) {
            BreadcrumpDTO breadcrumpDTO = BreadcrumpUtil.createByStaticPage(staticPage, request);
            model.addAttribute("breadcrump", breadcrumpDTO);
            model.addAttribute("title", staticPage.getTitle());
            model.addAttribute("htmlContent", staticPage.getHtmlContent());

            globalModelService.populateModel(model);

            model.asMap().put("categoryName", "Категории товаров");
            model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

            return "static";
        } else {
            return "redirect:/";
        }
    }
}
