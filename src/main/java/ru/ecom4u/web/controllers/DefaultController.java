package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ecom4u.web.controllers.dto.PaginatorDTO;
import ru.ecom4u.web.controllers.dto.QueryResult;
import ru.ecom4u.web.controllers.reqvalues.CategoryOrder;
import ru.ecom4u.web.domain.db.entities.Product;
import ru.ecom4u.web.domain.db.entities.ProductCategory;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.ProductService;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;
import ru.ecom4u.web.utils.BreadcrumpUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@Controller
public class DefaultController {

    private static final int PRODUCTS_ON_PAGE = 28;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private SitePropertiesService sitePropertiesService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        List<ProductCategory> subCategories = productCategoryService.getRootProductCategories();
        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", subCategories);
        return "main";
    }

    @RequestMapping(value = "/categories/{urlCategory}", method = RequestMethod.GET)
    public String categoryDefault(@PathVariable(value = "urlCategory") String urlCategory,
                                  @RequestParam(value = "order", required = false) CategoryOrder order,
                                  @RequestParam(value = "page", required = false) Integer page,
                                  Locale locale, Model model, HttpServletRequest request) {
        System.out.println(urlCategory + " ---> " + order);

        if (null == page)
            page = 0;

        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));

        if (!"".equals(urlCategory.trim())) {
            ProductCategory currentCategory = productCategoryService.getByUrlName(urlCategory);
            List<ProductCategory> subCategories = productCategoryService.getSubProductCategories(currentCategory);

            model.asMap().put("categoryName", currentCategory.getName());
            model.asMap().put("subCategories", subCategories);
            model.asMap().put("breadcrump", BreadcrumpUtil.createByProductCategory(currentCategory, request, productCategoryService));

            QueryResult<Product> productQueryResult = productService.getProductsOfCategory(currentCategory, page * PRODUCTS_ON_PAGE, PRODUCTS_ON_PAGE, order);
            model.asMap().put("order", order);
            model.asMap().put("page", page);
            model.asMap().put("products", productQueryResult.getData());
            model.asMap().put("productsCount", productQueryResult.getCountAll());
            model.asMap().put("paginator", new PaginatorDTO(page, productQueryResult.getCountAll(), PRODUCTS_ON_PAGE));
        }

        return "category";
    }


}
