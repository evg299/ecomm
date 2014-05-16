package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ecom4u.web.controllers.dto.CardProductDTO;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.ProductService;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;
import ru.ecom4u.web.domain.db.services.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by Evgeny on 22.04.14.
 */
@Controller
@RequestMapping(value = "card")
public class CardController extends AbstractController{
    @Autowired
    public UserService userService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public String card(Model model, HttpServletRequest request) {
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        List<CardProductDTO> cardProducts = new ArrayList<CardProductDTO>();
        Cookie cookies[] = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie c = cookies[i];
            try {
                String name = c.getName();
                if(name.startsWith("card-")){
                    String idString = name.substring("card-".length(), name.length());
                    String value = URLDecoder.decode(c.getValue(), "UTF-8");
                    Map<String, String> parmMap = parseCookieValue(value);

                    CardProductDTO cardProductDTO = new CardProductDTO();
                    cardProductDTO.setProduct(productService.getProductById(Integer.parseInt(idString)));
                    cardProductDTO.setCount(Integer.parseInt(parmMap.get("count")));
                    cardProductDTO.setAddedDate(new Date(Long.parseLong(parmMap.get("date"))));
                    cardProducts.add(cardProductDTO);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        model.asMap().put("cardProducts", cardProducts);

        return "card";
    }

    private Map<String, String> parseCookieValue(String value) {
        Map<String, String> result = new TreeMap<String, String>();
        String[] couples = value.split("&");
        for (String couple : couples) {
            String[] parts = couple.split("=");
            result.put(parts[0], parts[1]);
        }
        return result;
    }
}
