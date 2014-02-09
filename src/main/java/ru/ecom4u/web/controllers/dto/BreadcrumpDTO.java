package ru.ecom4u.web.controllers.dto;

import ru.ecom4u.web.controllers.dto.accessory.HyperLink;

import java.util.LinkedList;
import java.util.List;

public class BreadcrumpDTO {
    private List<HyperLink> hyperLinks = new LinkedList<HyperLink>();

    public List<HyperLink> getHyperLinks() {
        return hyperLinks;
    }

    public void addHyperLink(HyperLink hyperLink) {
        if (null != hyperLink)
            hyperLinks.add(0, hyperLink);
    }

    public void setHyperLinks(List<HyperLink> hyperLinks) {
        this.hyperLinks = hyperLinks;
    }

}
