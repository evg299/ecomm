package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ecom4u.web.busyness.DeliveryLogic;
import ru.ecom4u.web.busyness.PaymentLogic;
import ru.ecom4u.web.busyness.delivery.IDelivery;
import ru.ecom4u.web.busyness.payment.IPayment;
import ru.ecom4u.web.controllers.dto.CardProductDTO;
import ru.ecom4u.web.domain.db.entities.Product;
import ru.ecom4u.web.domain.db.entities.User;
import ru.ecom4u.web.domain.db.services.PersonService;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.ProductService;
import ru.ecom4u.web.domain.db.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Evgeny on 15.05.14.
 */
@Controller
public class OrderController
{

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private DeliveryLogic deliveryLogic;
    @Autowired
    private PaymentLogic paymentLogic;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "order", method = RequestMethod.GET)
    public String home(HttpServletRequest request, Locale locale, Model model)
    {
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        List<CardProductDTO> cardProducts = new ArrayList<CardProductDTO>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements())
        {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            String paramValue = paramValues[0];

            if (paramName.startsWith("pr"))
            {
                Integer productId = Integer.parseInt(paramName.substring("pr".length()));
                CardProductDTO cardProductDTO = new CardProductDTO();
                cardProductDTO.setProduct(productService.getProductById(productId));
                cardProductDTO.setCount(Integer.parseInt(paramValue));
                cardProducts.add(cardProductDTO);
            }
        }

        model.asMap().put("cardProducts", cardProducts);

        List<IDelivery> deliveries = deliveryLogic.getAvailableDeliveries();
        for (IDelivery delivery : deliveries)
        {
            System.err.println("delivery: " + delivery.getDeliveryName());
        }

        Collections.sort(deliveries, new Comparator<IDelivery>()
        {
            @Override
            public int compare(IDelivery delivery1, IDelivery delivery2)
            {
                return delivery1.getDeliveryName().compareTo(delivery2.getDeliveryName());
            }
        });

        model.asMap().put("deliveries", deliveries);

        List<IPayment> payments = paymentLogic.getAllIPayments();
        Collections.sort(payments, new Comparator<IPayment>()
        {
            @Override
            public int compare(IPayment iPayment1, IPayment iPayment2)
            {
                return iPayment1.getPaymentName().compareTo(iPayment2.getPaymentName());
            }
        });

        model.asMap().put("payments", payments);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmailOrLogin(auth.getName());
        model.asMap().put("person", user.getPerson());
        model.asMap().put("personContacts", personService.getPersonContacts(user.getPerson()));

        return "order";
    }

    @RequestMapping(value = "order-created", method = RequestMethod.POST)
    public String createOrder(HttpServletRequest request, Locale locale, Model model)
    {
        List<CardProductDTO> cardProducts = new ArrayList<CardProductDTO>();
        Double sumPrice = 0.0;
        Double sumWeight = 0.0;
        Double deliveryPrice = 0.0;
        String address = "";
        String apartments = "";
        String deliveryUnicName = "";
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements())
        {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            String paramValue = paramValues[0];

            System.err.println(paramName + ": " + paramValue);
            if (paramName.startsWith("picker-"))
            {
                int prId = Integer.parseInt(paramName.substring("picker-".length()));
                int count = Integer.parseInt(paramValue);

                System.err.println(String.format("prs: %s; %s", prId, count));
                CardProductDTO cardProductDTO = new CardProductDTO();
                Product product = productService.getProductById(prId);
                cardProductDTO.setProduct(product);
                cardProductDTO.setCount(count);
                cardProductDTO.setPrice(count * product.getPrice());
                sumPrice += cardProductDTO.getPrice();
                sumWeight += count * product.getWeight();
                cardProducts.add(cardProductDTO);
            }

            if (paramName.equalsIgnoreCase("delivery_price"))
            {
                deliveryPrice = Double.parseDouble(paramValue);
            }

            if (paramName.equalsIgnoreCase("geo_name"))
            {
                address = paramValue;
            }

            if (paramName.equalsIgnoreCase("apartments_input"))
            {
                apartments = paramValue;
            }

            if (paramName.equalsIgnoreCase("delivery"))
            {
                deliveryUnicName = paramValue;
            }
        }
        model.asMap().put("cardProducts", cardProducts);

        //todo
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        model.asMap().put("orderNum", UUID.randomUUID().toString().toUpperCase());
        model.asMap().put("sumPrice", sumPrice);
        model.asMap().put("deliveryPrice", deliveryPrice);
        model.asMap().put("allPrice", deliveryPrice + sumPrice);
        model.asMap().put("sumWeight", sumWeight);

        model.asMap().put("deliveryAddress", String.format("%s, %s", address, apartments));
        model.asMap().put("deliveryService", deliveryLogic.getDeliveryByUnicName(deliveryUnicName));

        return "order-created";
    }
}
