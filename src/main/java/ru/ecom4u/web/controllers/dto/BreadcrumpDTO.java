package ru.ecom4u.web.controllers.dto;

import java.util.ArrayList;
import java.util.List;

import ru.ecom4u.web.controllers.dto.accessory.HyperLink;

public class BreadcrumpDTO {
	private List<HyperLink> hyperLinks = new ArrayList<HyperLink>();

	public List<HyperLink> getHyperLinks() {
		return hyperLinks;
	}

	public void addHyperLink(HyperLink hyperLink) {
		if (null != hyperLink)
			hyperLinks.add(hyperLink);
	}

	public void setHyperLinks(List<HyperLink> hyperLinks) {
		this.hyperLinks = hyperLinks;
	}

}
