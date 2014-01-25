package ru.ecom4u.web.controllers.dto;

public class CategoryDTO {
	private int id;
	private String name;
	private String url;
	private int pid;

	public CategoryDTO() {
		// TODO Auto-generated constructor stub
	}

	public CategoryDTO(int id, String name, String url, int pid) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.pid = pid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

}
