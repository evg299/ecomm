package ru.ecom4u.web.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Evgeny on 11.05.14.
 */
@Component
public class MaintenanceInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SitePropertiesService sitePropertiesService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String uri = request.getRequestURI();
        if (checkUri(uri)) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.err.println("name: " + auth.getName());
            System.err.println("authorities: " + auth.getAuthorities());
            System.err.println("url: " + request.getRequestURL());
            System.err.println("uri: " + request.getRequestURI());

            // Данные header-а
            modelAndView.getModel().put("siteName", sitePropertiesService.getValue("site_name"));
            modelAndView.getModel().put("siteDesc", sitePropertiesService.getValue("site_desc"));
            modelAndView.getModel().put("siteCurrency", sitePropertiesService.getSiteCurrency());
            modelAndView.getModel().put("siteWeightUnit", sitePropertiesService.getSiteWeightUnit());
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

        return true;
    }
}
