package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ecom4u.web.controllers.dto.forms.CommentForm;
import ru.ecom4u.web.controllers.dto.services.SelectVariantDTO;
import ru.ecom4u.web.domain.db.entities.Comment;
import ru.ecom4u.web.domain.db.entities.Product;
import ru.ecom4u.web.domain.db.entities.ProductVariant;
import ru.ecom4u.web.domain.db.entities.ProductVariantOption;
import ru.ecom4u.web.domain.db.services.*;
import ru.ecom4u.web.utils.BreadcrumpUtil;
import ru.ecom4u.web.utils.LastVisitedIdsUtil;
import ru.ecom4u.web.utils.ProductUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(value = "products")
public class ProductController {

    public static final int COOKIE_MAX_AGE = 3600;

    @Autowired
    private SitePropertiesService sitePropertiesService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "{productUuid}", method = RequestMethod.GET)
    public String product(@PathVariable(value = "productUuid") String productUuid,
                          @CookieValue(value = "lastvisited", required = false) String lastVisitedIds,
                          Locale locale,
                          Model model,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));

        Product product = fillProductModel(model.asMap(), productUuid, request, response);
        model.asMap().put("comments", commentService.getByProduct(product));
        model.asMap().put("commentForm", new CommentForm());

        Set<Integer> ids = LastVisitedIdsUtil.parceIds(lastVisitedIds);
        ids.add(product.getId());
        setCookieToResponse(response, "lastvisited", LastVisitedIdsUtil.createIdsString(ids));

        return "product";
    }

    @RequestMapping(value = "{productUuid}", method = RequestMethod.POST)
    public String addComment(@Valid @ModelAttribute("commentForm") CommentForm commentForm,
                             BindingResult result,
                             @PathVariable(value = "productUuid") String productUuid,
                             @CookieValue(value = "lastvisited", required = false) String lastVisitedIds,
                             Locale locale,
                             Model model,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));

        Product product = fillProductModel(model.asMap(), productUuid, request, response);
        if (!result.hasErrors()) {
            Comment comment = new Comment();
            comment.setProduct(product);
            comment.setTitle(commentForm.getTitle());
            comment.setContent(commentForm.getText());
            // TODO: пользователя надо извлекать из контекста
            comment.setPerson(userService.getById(14).getPerson());
            commentService.save(comment);

            model.asMap().put("commentForm", new CommentForm());
        }

        model.asMap().put("comments", commentService.getByProduct(product));

        return "product";
    }

    private Product fillProductModel(Map<String, Object> modelMap, String productUuid, HttpServletRequest request, HttpServletResponse response) {
        Product product = productService.getProductByUuid(productUuid);
        modelMap.put("product", product);
        modelMap.put("productPrice", ProductUtil.convertPrice(product.getPrice()));
        modelMap.put("additionalPictures", productService.getAdditionalPictures(product));
        modelMap.put("productProperties", productService.getProductProperties(product));
        modelMap.put("notSelectVariants", productVariantService.getNotSelectByProduct(product));

        List<SelectVariantDTO> selectVariantDTOs = new ArrayList<SelectVariantDTO>();
        List<ProductVariant> selectVariants = productVariantService.getSelectByProduct(product);
        for (ProductVariant selectVariant : selectVariants) {
            List<ProductVariantOption> variantOptions = productVariantService.getOptionsByProductVariant(selectVariant);
            selectVariantDTOs.add(new SelectVariantDTO(selectVariant, variantOptions));
        }
        modelMap.put("selectVariantDTOs", selectVariantDTOs);

        modelMap.put("relatedProducts", productService.getRelatedProducts(product));
        modelMap.put("breadcrump", BreadcrumpUtil.createByProductCategory(product, request, productCategoryService));
        modelMap.put("categoryName", "Категории товаров");
        modelMap.put("subCategories", productCategoryService.getRootProductCategories());

        return product;
    }

    private void setCookieToResponse(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        response.addCookie(cookie);
    }
}
