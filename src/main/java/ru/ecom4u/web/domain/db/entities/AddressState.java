package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the address_states database table.
 * 
 */
@Entity
@Table(name="address_states")
public class AddressState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Lob
	@Column(nullable=false)
	private String name;

	//bi-directional many-to-one association to AddressCountry
	@ManyToOne
	@JoinColumn(name="address_country_id", nullable=false)
	private AddressCountry addressCountry;

	//bi-directional many-to-one association to Address
	@OneToMany(mappedBy="addressState")
	private List<Address> addresses;

	public AddressState() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AddressCountry getAddressCountry() {
		return this.addressCountry;
	}

	public void setAddressCountry(AddressCountry addressCountry) {
		this.addressCountry = addressCountry;
	}

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Address addAddress(Address address) {
		getAddresses().add(address);
		address.setAddressState(this);

		return address;
	}

	public Address removeAddress(Address address) {
		getAddresses().remove(address);
		address.setAddressState(null);

		return address;
	}

}