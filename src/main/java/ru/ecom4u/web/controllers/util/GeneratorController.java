package ru.ecom4u.web.controllers.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ecom4u.web.domain.db.entities.*;
import ru.ecom4u.web.domain.db.services.CurrencyService;
import ru.ecom4u.web.domain.db.services.PictureService;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.ProductService;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Evgeny(e299792459@gmail.com) on 06.02.14.
 */
@Controller
public class GeneratorController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private ProductService productService;

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

    @RequestMapping(value = "util/genproducts")
    @ResponseBody
    public String generateProducts() {
        List<ProductCategory> categories = productCategoryService.getAll();
        Currency currency = currencyService.getById(1);
        List<Picture> pictures = pictureService.getAll();
        int pictCount = pictures.size();
        Random random = new Random();

        int count = 1;
        for (ProductCategory productCategory : categories) {
            for (int i = 0; i < 12; i++) {
                Product product = new Product();
                product.setProductCategory(productCategory);

                product.setName("Товар №" + count);
                product.setAmt(1 + count);
                product.setDescription("Отписание описание товара №" + count);
                product.setUuid(UUID.randomUUID().toString());

                Price price = new Price();
                price.setValue(12.3 * count);
                price.setCurrency(currency);
                productService.save(price);

                product.setPrice(price);
                product.setPicture(pictures.get(random.nextInt(pictCount)));

                productService.save(product);

                count++;
            }
        }
        return "Done";
    }
}
