package ru.ecom4u.web.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.ecom4u.web.domain.db.entities.User;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;
import ru.ecom4u.web.domain.db.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * Created by Evgeny on 11.05.14.
 */
@Component
public class MaintenanceInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SitePropertiesService sitePropertiesService;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String uri = request.getRequestURI();
        if (checkUri(uri)) {
            // Данные header-а
            modelAndView.getModel().put("siteName", sitePropertiesService.getValue("site_name"));
            modelAndView.getModel().put("siteDesc", sitePropertiesService.getValue("site_desc"));
            modelAndView.getModel().put("siteCurrency", sitePropertiesService.getSiteCurrency());
            modelAndView.getModel().put("siteWeightUnit", sitePropertiesService.getSiteWeightUnit());


            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.err.println("name: " + auth.getName());
            System.err.println("authorities: " + auth.getAuthorities());
            System.err.println("url: " + request.getRequestURL());
            System.err.println("uri: " + request.getRequestURI());

            if (request instanceof SecurityContextHolderAwareRequestWrapper) {
                SecurityContextHolderAwareRequestWrapper scharWrapper = (SecurityContextHolderAwareRequestWrapper) request;

                if(scharWrapper.isUserInRole("ROLE_USER")) {
                    User user = userService.getByEmailOrLogin(auth.getName());
                    modelAndView.getModel().put("isAuth", true);
                    modelAndView.getModel().put("currentUser", user);
                }
            }



        }

        super.postHandle(request, response, handler, modelAndView);
    }

    private boolean checkUri(String uri) {
        if (null == uri)
            return false;

        if (uri.startsWith("/filestorage/"))
            return false;

        if (uri.startsWith("/categories-json/"))
            return false;

        if (uri.startsWith("/deliveryCalculate/"))
            return false;

        return true;
    }


}
