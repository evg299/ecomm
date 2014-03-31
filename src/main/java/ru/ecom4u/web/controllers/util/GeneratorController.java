package ru.ecom4u.web.controllers.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ecom4u.web.domain.db.entities.*;
import ru.ecom4u.web.domain.db.services.*;

import java.util.ArrayList;
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

    @Autowired
    private UnitService unitService;

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
            productCategory.setParentId(parentProductCategory.getId());
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

    @RequestMapping(value = "util/genstats")
    @ResponseBody
    public String generateStatistics() {
        int count = productService.countProducts().intValue();
        List<Product> products = productService.getProducts(0, count);
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Product product = products.get(random.nextInt(count));
            if (random.nextBoolean())
                productService.markView(product);
            if (random.nextBoolean())
                productService.markCard(product);
            if (random.nextBoolean())
                productService.markSell(product);
        }

        return "Done";
    }

    @RequestMapping(value = "util/genrecom")
    @ResponseBody
    public String generateRecommended() {
        int count = productService.countProducts().intValue();
        List<Product> products = productService.getProducts(0, count);
        Random random = new Random();

        for (int i = 0; i < count / 10; i++) {
            Product product = products.get(random.nextInt(count));
            if (random.nextBoolean())
                productService.markRecommended(product);
        }

        return "Done";
    }

    @RequestMapping(value = "util/genadditionalpictures")
    @ResponseBody
    public String generateAdditionalPictures() {
        Random random = new Random();
        List<Picture> pictures = pictureService.getAll();
        int pictSize = pictures.size();

        int prodSize = productService.countProducts().intValue();
        List<Product> products = productService.getProducts(0, prodSize);

        for (Product product : products) {
            List<Picture> additional = new ArrayList<Picture>();
            for (int i = 0; i < random.nextInt(8); i++) {
                additional.add(pictures.get(random.nextInt(pictSize)));
            }

            product.setAdditionalPictures(additional);
            productService.update(product);
        }

        return "Done";
    }

    @RequestMapping(value = "util/genproductproperties")
    @ResponseBody
    public String generateProductProperties() {
        Random random = new Random();
        List<Unit> units = unitService.getAll();
        int unitSize = units.size();

        int prodSize = productService.countProducts().intValue();
        List<Product> products = productService.getProducts(0, prodSize);
        for (Product product : products) {
            for (int i = 0; i < random.nextInt(8); i++) {
                ProductProperty property = new ProductProperty();
                property.setProduct(product);
                property.setUnit(units.get(random.nextInt(unitSize)));
                property.setName("Свойство №" + random.nextInt(100));
                property.setValue("" + random.nextInt(1000));
                productService.save(property);
            }
        }

        return "Done";
    }

    @RequestMapping(value = "util/genproductrelated")
    @ResponseBody
    public String generateProductRelated() {
        Random random = new Random();
        int prodSize = productService.countProducts().intValue();
        List<Product> products = productService.getProducts(0, prodSize);

        for (Product product : products) {
            if (random.nextBoolean())
                for (int i = 0; i < random.nextInt(8); i++) {
                    AuxProductRelated auxProductRelated = new AuxProductRelated();
                    auxProductRelated.setProduct1(product);
                    Product product2 = products.get(random.nextInt(prodSize));
                    if (product2 != product) {
                        auxProductRelated.setProduct2(product2);
                        productService.save(auxProductRelated);
                    }
                }
        }


        return "Done";
    }
}
