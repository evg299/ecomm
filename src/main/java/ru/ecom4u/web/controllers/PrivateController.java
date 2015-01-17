package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ecom4u.web.controllers.dto.BreadcrumpDTO;
import ru.ecom4u.web.controllers.dto.accessory.HyperLink;
import ru.ecom4u.web.domain.db.entities.Order;
import ru.ecom4u.web.domain.db.entities.OrderStatus;
import ru.ecom4u.web.domain.db.entities.User;
import ru.ecom4u.web.domain.db.services.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Evgeny on 09.05.14.
 */
@Controller
@RequestMapping(value = "private")
public class PrivateController
{

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SitePropertiesService sitePropertiesService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(HttpServletRequest request, Locale locale, Model model)
    {
        model.asMap().put("categoryName", sitePropertiesService.getValue("rus_product_categories"));
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        BreadcrumpDTO breadcrump = new BreadcrumpDTO();
        HyperLink hyperLink = new HyperLink(request.getContextPath() + "/private/", "Личный кабинет");
        breadcrump.addHyperLink(hyperLink);
        model.asMap().put("breadcrump", breadcrump);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmailOrLogin(auth.getName());
        model.asMap().put("person", user.getPerson());
        model.asMap().put("personContacts", personService.getPersonContacts(user.getPerson()));

        return "personal";
    }

    @RequestMapping(value = "orders", method = RequestMethod.GET)
    public String orders(@RequestParam(required = false) String status, Locale locale, Model model)
    {
        model.asMap().put("categoryName", sitePropertiesService.getValue("rus_product_categories"));
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmailOrLogin(auth.getName());
        List<Order> userOrders = orderService.getOrdersByPerson(user.getPerson());

        model.asMap().put("countAll", userOrders.size());
        int countDone = 0;
        for (Order order : userOrders)
        {
            if (OrderStatus.done == order.getOrderStatus())
                countDone++;
        }
        model.asMap().put("countDone", countDone);
        model.asMap().put("countActive", userOrders.size() - countDone);

        List<Order> filteredOrders = new ArrayList<Order>();
        if ("active".equalsIgnoreCase(status))
        {
            for (Order order : userOrders)
            {
                if (OrderStatus.done != order.getOrderStatus())
                    filteredOrders.add(order);
            }
            model.asMap().put("userOrders", filteredOrders);
        }
        else if ("complete".equalsIgnoreCase(status))
        {
            for (Order order : userOrders)
            {
                if (OrderStatus.done == order.getOrderStatus())
                    filteredOrders.add(order);
            }
            model.asMap().put("userOrders", filteredOrders);
        }
        else
        {
            status = "all";
            model.asMap().put("userOrders", userOrders);
        }

        model.asMap().put("status", status);


        return "myorders";
    }
}
