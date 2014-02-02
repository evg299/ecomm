package ru.ecom4u.web.controllers.util.dto;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUploadForm {
	private String name;
	private List<CommonsMultipartFile> files;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CommonsMultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<CommonsMultipartFile> files) {
		this.files = files;
	}

}
