package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ecom4u.web.busyness.DeliveryLogic;
import ru.ecom4u.web.busyness.delivery.IDelivery;
import ru.ecom4u.web.controllers.dto.CardProductDTO;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

/**
 * Created by Evgeny on 15.05.14.
 */
@Controller
@RequestMapping(value = "order")
public class OrderController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private DeliveryLogic deliveryLogic;

    @RequestMapping(method = RequestMethod.GET)
    public String home(HttpServletRequest request, Locale locale, Model model) {
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        List<CardProductDTO> cardProducts = new ArrayList<CardProductDTO>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            String paramValue = paramValues[0];

            if (paramName.startsWith("pr")) {
                Integer productId = Integer.parseInt(paramName.substring("pr".length()));
                CardProductDTO cardProductDTO = new CardProductDTO();
                cardProductDTO.setProduct(productService.getProductById(productId));
                cardProductDTO.setCount(Integer.parseInt(paramValue));
                cardProducts.add(cardProductDTO);
            }
        }

        model.asMap().put("cardProducts", cardProducts);

        List<IDelivery> deliveries = deliveryLogic.getAvailableDeliveries();
        for(IDelivery delivery: deliveries){
            System.err.println("delivery: " + delivery.deliveryName());
        }

        return "order";
    }
}
