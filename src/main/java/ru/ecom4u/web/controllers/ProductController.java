package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ecom4u.web.domain.db.entities.Product;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.ProductService;
import ru.ecom4u.web.domain.db.services.ProductVariantService;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;
import ru.ecom4u.web.utils.BreadcrumpUtil;
import ru.ecom4u.web.utils.LastVisitedIdsUtil;
import ru.ecom4u.web.utils.ProductUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Set;

@Controller
@RequestMapping(value = "products")
public class ProductController {

    @Autowired
    private SitePropertiesService sitePropertiesService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductVariantService productVariantService;

    @RequestMapping(value = "{productUuid}", method = RequestMethod.GET)
    public String product(@PathVariable(value = "productUuid") String productUuid, @CookieValue(value = "lastvisited", required = false) String lastVisitedIds,
                          Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));

        Product product = productService.getProductByUuid(productUuid);
        model.asMap().put("product", product);
        model.asMap().put("productPrice", ProductUtil.convertPrice(product.getPrice()));
        model.asMap().put("additionalPictures", productService.getAdditionalPictures(product));
        model.asMap().put("productProperties", productService.getProductProperties(product));
        model.asMap().put("notSelectVariants", productVariantService.getNotSelectByProduct(product));

        model.asMap().put("relatedProducts", productService.getRelatedProducts(product));
        model.asMap().put("breadcrump", BreadcrumpUtil.createByProductCategory(product, request, productCategoryService));
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        Set<Integer> ids = LastVisitedIdsUtil.parceIds(lastVisitedIds);
        ids.add(product.getId());
        Cookie cookie = new Cookie("lastvisited", LastVisitedIdsUtil.createIdsString(ids));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        return "product";
    }
}
