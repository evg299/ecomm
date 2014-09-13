package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ecom4u.web.busyness.DeliveryLogic;
import ru.ecom4u.web.busyness.PaymentLogic;
import ru.ecom4u.web.busyness.delivery.IDelivery;
import ru.ecom4u.web.busyness.payment.IPayment;
import ru.ecom4u.web.controllers.dto.CardProductDTO;
import ru.ecom4u.web.domain.db.entities.*;
import ru.ecom4u.web.domain.db.services.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Evgeny on 15.05.14.
 */
@Controller
@RequestMapping(value = "order")
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
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String home(HttpServletRequest request, Locale locale, Model model)
    {
        model.asMap().put("title", "Оформление заказа");
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        List<CardProductDTO> cardProducts = new ArrayList<CardProductDTO>();
        Cookie cookies[] = request.getCookies();
        for (int i = 0; i < cookies.length; i++)
        {
            Cookie c = cookies[i];
            try
            {
                String name = c.getName();
                if (name.startsWith("card-"))
                {
                    String idString = name.substring("card-".length(), name.length());
                    String value = URLDecoder.decode(c.getValue(), "UTF-8");
                    Map<String, String> parmMap = parseCookieValue(value);

                    CardProductDTO cardProductDTO = new CardProductDTO();
                    cardProductDTO.setProduct(productService.getProductById(Integer.parseInt(idString)));
                    cardProductDTO.setCount(Integer.parseInt(parmMap.get("count")));
                    cardProductDTO.setAddedDate(new Date(Long.parseLong(parmMap.get("date"))));
                    cardProducts.add(cardProductDTO);
                }
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }

        if (0 == cardProducts.size())
        {
            return "redirect:/";
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

        return "order-new";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addOrder(@RequestParam(value = "delivery_price", required = false) Double deliveryPrice,
                           @RequestParam(value = "geo_name", required = false) String address,
                           @RequestParam(value = "apartments_input", required = false) String apartments,
                           @RequestParam(value = "delivery", required = false) String deliveryUnicName,
                           @RequestParam(value = "addr_country", required = false) String addressCountry,
                           @RequestParam(value = "addr_region", required = false) String addressRegion,
                           @RequestParam(value = "hierarhy_addr", required = false) String hierarchyJson,
                           @RequestParam(value = "comment", required = false) String comment,
                           @RequestParam(value = "payment", required = false) String payment,
                           HttpServletRequest request, HttpServletResponse response, Locale locale, Model model)
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


        Double sumPrice = 0.0;
        Double sumWeight = 0.0;
        List<OrderProduct> orderProducts = new ArrayList<OrderProduct>();
        Cookie cookies[] = request.getCookies();
        for (int i = 0; i < cookies.length; i++)
        {
            Cookie c = cookies[i];
            String name = c.getName();
            if (name.startsWith("card-"))
            {
                String idString = name.substring("card-".length(), name.length());
                String value = getCookieValue(c);
                Map<String, String> parmMap = parseCookieValue(value);

                Product product = productService.getProductById(Integer.parseInt(idString));
                Integer count = Integer.parseInt(parmMap.get("count"));
                Long dateMS = Long.parseLong(parmMap.get("date"));

                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setProduct(product);
                orderProduct.setCount(count);
                orderProduct.setAddedDate(new Date(dateMS));
                orderProduct.setOrder(order);
                orderService.save(orderProduct);
                orderProducts.add(orderProduct);

                CardProductDTO cardProductDTO = new CardProductDTO();
                cardProductDTO.setProduct(product);
                cardProductDTO.setCount(count);
                cardProductDTO.setPrice(count * product.getPrice());

                sumPrice += cardProductDTO.getPrice();
                sumWeight += count * product.getWeight();

                c.setMaxAge(0);
                response.addCookie(c);
            }
        }

        AddressCountry country = addressService.getAddressCountry(addressCountry);
        AddressState state = addressService.getAddressState(addressRegion, country);
        Address addressDelivery = addressService.getOrCreateAddress(address, apartments, hierarchyJson, state);

        order.setCoast(sumPrice);
        order.setSumCoast(sumPrice + deliveryPrice);
        order.setPaymentClass(payment);
        order.setComment(comment);
        order.setOrderProducts(orderProducts);
        orderService.update(order);

        Delivery delivery = new Delivery();
        delivery.setCoast(deliveryPrice);
        delivery.setAddress(addressDelivery);
        delivery.setWeight(sumWeight);
        delivery.setPerson(user.getPerson());
        delivery.setDeliveryClass(deliveryUnicName);
        delivery.setOrder(order);
        orderService.save(delivery);

        List<CardProductDTO> cardProducts = new ArrayList<CardProductDTO>();
        for(OrderProduct orderProduct: order.getOrderProducts()){
            CardProductDTO cardProductDTO = new CardProductDTO();
            cardProductDTO.setProduct(orderProduct.getProduct());
            cardProductDTO.setCount(orderProduct.getCount());
            cardProductDTO.setAddedDate(orderProduct.getAddedDate());
            cardProducts.add(cardProductDTO);
        }

        model.asMap().put("title", String.format("Заказ № %s", uuid.toUpperCase()));
        model.asMap().put("cardProducts", cardProducts);
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        model.asMap().put("message", "Заказ создан");
        model.asMap().put("orderNum", uuid.toUpperCase());
        model.asMap().put("sumPrice", sumPrice);
        model.asMap().put("deliveryPrice", delivery.getCoast());
        model.asMap().put("allPrice", delivery.getCoast() + sumPrice);
        model.asMap().put("sumWeight", delivery.getWeight());
        model.asMap().put("deliveryAddress", String.format("%s, %s", addressDelivery.getAddress(), addressDelivery.getApartments()));
        model.asMap().put("deliveryService", deliveryLogic.getDeliveryByUnicName(delivery.getDeliveryClass()));
        model.asMap().put("person", order.getPerson());
        model.asMap().put("personContacts", personService.getPersonContacts(order.getPerson()));

        IPayment iPayment = paymentLogic.getPaymentByUnicName(order.getPaymentClass());
        if(null != iPayment)
        {
            model.asMap().put("paymentName", iPayment.getPaymentName());
        }

        return "order";
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
        model.asMap().put("title", String.format("Заказ № %s", orderUUID.toUpperCase()));
        model.asMap().put("cardProducts", cardProducts);
        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        Delivery delivery = order.getDelivery();
        Address address = delivery.getAddress();

        model.asMap().put("orderNum", orderUUID.toUpperCase());
        model.asMap().put("sumPrice", sumPrice);
        model.asMap().put("deliveryPrice", delivery.getCoast());
        model.asMap().put("allPrice", delivery.getCoast() + sumPrice);
        model.asMap().put("sumWeight", delivery.getWeight());
        model.asMap().put("deliveryAddress", String.format("%s, %s", address.getAddress(), address.getApartments()));
        model.asMap().put("deliveryService", deliveryLogic.getDeliveryByUnicName(delivery.getDeliveryClass()));
        model.asMap().put("person", order.getPerson());
        model.asMap().put("personContacts", personService.getPersonContacts(order.getPerson()));

        IPayment iPayment = paymentLogic.getPaymentByUnicName(order.getPaymentClass());
        if(null != iPayment)
        {
            model.asMap().put("paymentName", iPayment.getPaymentName());
        }

        return "order";
    }

    private String getCookieValue(Cookie c)
    {
        try
        {
            return URLDecoder.decode(c.getValue(), "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, String> parseCookieValue(String value)
    {
        Map<String, String> result = new TreeMap<String, String>();
        String[] couples = value.split("&");
        for (String couple : couples)
        {
            String[] parts = couple.split("=");
            result.put(parts[0], parts[1]);
        }
        return result;
    }
}
