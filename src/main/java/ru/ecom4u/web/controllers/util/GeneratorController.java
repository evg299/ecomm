package ru.ecom4u.web.controllers.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ecom4u.web.domain.db.entities.Product;
import ru.ecom4u.web.domain.db.entities.ProductCategory;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;

import java.util.List;

/**
 * Created by Evgeny(e299792459@gmail.com) on 06.02.14.
 */
@Controller
public class GeneratorController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "util/gencats")
    @ResponseBody
    public String generateCategories() {
        for (int i = 0; i < 12; i++) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setName("Категория - " + i);
            productCategory.setUrlName("category-" + i);
            productCategoryService.save(productCategory);
        }

        return "Done";
    }

    @RequestMapping(value = "util/gencats/{id}")
    @ResponseBody
    public String generateCategories(@PathVariable(value = "id") Integer id) {
        ProductCategory parentProductCategory = productCategoryService.getById(id);
        for (int i = 0; i < 12; i++) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategory(parentProductCategory);
            productCategory.setName("Категория - " + id + "-" + i);
            productCategory.setUrlName("category-" + id + "-" + i);
            productCategoryService.save(productCategory);
        }

        return "Done";
    }

    public String generateProducts(){
        List<ProductCategory> categories = productCategoryService.getAll();
        for(ProductCategory productCategory: categories){
            Product product = new Product();
            product.setProductCategory(productCategory);
        }
        return "Done";
    }
}
