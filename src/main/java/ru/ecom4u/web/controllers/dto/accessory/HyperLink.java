package ru.ecom4u.web.controllers.dto.accessory;

public class HyperLink {
	private String url;
	private String text;

	public HyperLink() {
	}

	public HyperLink(String url, String text) {
		this.url = url;
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
