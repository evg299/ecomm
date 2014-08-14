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
<<<<<<< HEAD
import ru.ecom4u.web.domain.db.entities.*;
import ru.ecom4u.web.domain.db.services.*;
=======
import ru.ecom4u.web.domain.db.entities.Product;
import ru.ecom4u.web.domain.db.entities.User;
import ru.ecom4u.web.domain.db.services.PersonService;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.ProductService;
import ru.ecom4u.web.domain.db.services.UserService;
>>>>>>> f13c0105780753766da1b5b461e5ac3f65071602

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

<<<<<<< HEAD
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public ModelAndView createOrder(HttpServletRequest request, HttpServletResponse response, Locale locale, Model model, RedirectAttributes redirectAttributes) throws IOException
    {
        String uuid = UUID.randomUUID().toString();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmailOrLogin(auth.getName());

        Order order = new Order();
        order.setUuid(uuid);
        order.setPerson(user.getPerson());
        order.setCreationDate(new Timestamp(System.currentTimeMillis()));
        order.setOrderStatus(OrderStatus.expects);
        orderService.save(order);

        List<OrderProduct> orderProducts = new ArrayList<OrderProduct>();
=======
    @RequestMapping(value = "order-created", method = RequestMethod.POST)
    public String createOrder(HttpServletRequest request, Locale locale, Model model)
    {
        List<CardProductDTO> cardProducts = new ArrayList<CardProductDTO>();
>>>>>>> f13c0105780753766da1b5b461e5ac3f65071602
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
<<<<<<< HEAD

            if (paramName.equalsIgnoreCase("addr_country"))
            {
                addrCountry = paramValue;
            }

            if (paramName.equalsIgnoreCase("addr_region"))
            {
                addrRegion = paramValue;
            }

            if (paramName.equalsIgnoreCase("hierarhy_addr"))
            {
                hierarhyJson = paramValue;
            }

            if (paramName.equalsIgnoreCase("comment"))
            {
                comment = paramValue;
            }

            if (paramName.equalsIgnoreCase("payment"))
            {
                payment = paramValue;
            }

        }

        Cookie cookies[] = request.getCookies();
        for (int i = 0; i < cookies.length; i++)
        {
            Cookie c = cookies[i];
            String name = c.getName();
            if (name.startsWith("card-"))
            {
                c.setMaxAge(0);
                response.addCookie(c);
            }
=======
>>>>>>> f13c0105780753766da1b5b461e5ac3f65071602
        }
        model.asMap().put("cardProducts", cardProducts);

        //todo
<<<<<<< HEAD
        AddressCountry country = addressService.getAddressCountry(addrCountry);
        AddressState state = addressService.getAddressState(addrRegion, country);
        Address address = addressService.getOrCreateAddress(addr, apartments, hierarhyJson, state);

        order.setCoast(sumPrice);
        order.setSumCoast(sumPrice + deliveryPrice);
        order.setPaymentClass(payment);
        order.setComment(comment);
        order.setOrderProducts(orderProducts);
        orderService.update(order);

        Delivery delivery = new Delivery();
        delivery.setCoast(deliveryPrice);
        delivery.setAddress(address);
        delivery.setWeight(sumWeight);
        delivery.setPerson(user.getPerson());
        delivery.setDeliveryClass(deliveryUnicName);
        delivery.setOrder(order);
        orderService.save(delivery);

        redirectAttributes.asMap().clear();

        return new ModelAndView(String.format("redirect:/order/%s", uuid));
    }

    @RequestMapping(value = "{orderUUID}", method = RequestMethod.GET)
    public String viewOrder(@PathVariable String orderUUID, HttpServletRequest request, Locale locale, Model model)
    {
        System.err.println("orderUUID: " + orderUUID);
        Order order = orderService.getByUuid(orderUUID);

        Double sumPrice = 0.0;
        List<CardProductDTO> cardProducts = new ArrayList<CardProductDTO>();
        for (OrderProduct orderProduct : order.getOrderProducts())
        {
            CardProductDTO cardProductDTO = new CardProductDTO();
            cardProductDTO.setProduct(orderProduct.getProduct());
            cardProductDTO.setCount(orderProduct.getCount());
            cardProductDTO.setPrice(orderProduct.getCount() * orderProduct.getProduct().getPrice());
            cardProducts.add(cardProductDTO);

            sumPrice += cardProductDTO.getPrice();
        }
        model.asMap().put("cardProducts", cardProducts);
=======
>>>>>>> f13c0105780753766da1b5b461e5ac3f65071602
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        model.asMap().put("orderNum", UUID.randomUUID().toString().toUpperCase());
        model.asMap().put("sumPrice", sumPrice);
        model.asMap().put("deliveryPrice", deliveryPrice);
        model.asMap().put("allPrice", deliveryPrice + sumPrice);
        model.asMap().put("sumWeight", sumWeight);

        model.asMap().put("deliveryAddress", String.format("%s, %s", address, apartments));
        model.asMap().put("deliveryService", deliveryLogic.getDeliveryByUnicName(deliveryUnicName));

<<<<<<< HEAD
=======
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmailOrLogin(auth.getName());
        model.asMap().put("person", user.getPerson());
        model.asMap().put("personContacts", personService.getPersonContacts(user.getPerson()));

>>>>>>> f13c0105780753766da1b5b461e5ac3f65071602
        return "order-created";
    }
}
