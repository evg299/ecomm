package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the site_properties database table.
 * 
 */
@Entity
@Table(name="site_properties")
public class SiteProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, length=128)
	private String name;

	@Lob
	@Column(nullable=false)
	private String value;

	public SiteProperty() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}