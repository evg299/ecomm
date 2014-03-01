package ru.ecom4u.web.utils;

import ru.ecom4u.web.controllers.dto.BreadcrumpDTO;
import ru.ecom4u.web.controllers.dto.accessory.HyperLink;
import ru.ecom4u.web.controllers.reqvalues.CategoryOrder;
import ru.ecom4u.web.domain.db.entities.ProductCategory;
import ru.ecom4u.web.domain.db.entities.StaticPage;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Evgeny(e299792459@gmail.com) on 09.02.14.
 */
public class BreadcrumpUtil {

    public static BreadcrumpDTO createByStaticPage(StaticPage staticPage, HttpServletRequest request) {
        BreadcrumpDTO breadcrumpDTO = new BreadcrumpDTO();
        breadcrumpDTO.addHyperLink(new HyperLink(request.getContextPath() + "/static/" + staticPage.getUrlName(), staticPage.getTitle()));
        return breadcrumpDTO;
    }

    public static BreadcrumpDTO createByProductCategory(ProductCategory productCategory, HttpServletRequest request, ProductCategoryService productCategoryService) {
        BreadcrumpDTO breadcrumpDTO = new BreadcrumpDTO();

        ProductCategory parentCategory = null;
        do {
            if (null != productCategory.getParentId())
                parentCategory = productCategoryService.getById(productCategory.getParentId());
            else
                parentCategory = null;
            breadcrumpDTO.addHyperLink(new HyperLink(request.getContextPath() + "/categories/" + productCategory.getUrlName(), productCategory.getName()));
            productCategory = parentCategory;
        } while (null != parentCategory);


        return breadcrumpDTO;
    }
}
