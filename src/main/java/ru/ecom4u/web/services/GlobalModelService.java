package ru.ecom4u.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;

/**
 * Created by Evgeny on 25.04.14.
 */
@Service
public class GlobalModelService {
    @Autowired
    private SitePropertiesService sitePropertiesService;

    public void populateModel(Model model) {
        model.asMap().put("siteName", sitePropertiesService.getValue("site_name"));
        model.asMap().put("siteDesc", sitePropertiesService.getValue("site_desc"));
        model.asMap().put("siteCurrency", sitePropertiesService.getSiteCurrency());
        model.asMap().put("siteWeightUnit", sitePropertiesService.getSiteWeightUnit());
    }
}
