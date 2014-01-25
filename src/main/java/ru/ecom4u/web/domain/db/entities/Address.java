package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the addresses database table.
 * 
 */
@Entity
@Table(name = "addresses")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Lob
	@Column(nullable = false)
	private String address;

	@Lob
	@Column(name = "hierarhy_json")
	private String hierarhyJson;

	// bi-directional many-to-one association to AddressCity
	@ManyToOne
	@JoinColumn(name = "address_city_id")
	private AddressCity addressCity;

	// bi-directional many-to-one association to AddressState
	@ManyToOne
	@JoinColumn(name = "address_satate_id", nullable = false)
	private AddressState addressState;

	// bi-directional many-to-one association to Delivery
	@OneToMany(mappedBy = "address")
	private List<Delivery> deliveries;

	public Address() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHierarhyJson() {
		return this.hierarhyJson;
	}

	public void setHierarhyJson(String hierarhyJson) {
		this.hierarhyJson = hierarhyJson;
	}

	public AddressCity getAddressCity() {
		return this.addressCity;
	}

	public void setAddressCity(AddressCity addressCity) {
		this.addressCity = addressCity;
	}

	public AddressState getAddressState() {
		return this.addressState;
	}

	public void setAddressState(AddressState addressState) {
		this.addressState = addressState;
	}

	public List<Delivery> getDeliveries() {
		return this.deliveries;
	}

	public void setDeliveries(List<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public Delivery addDelivery(Delivery delivery) {
		getDeliveries().add(delivery);
		delivery.setAddress(this);

		return delivery;
	}

	public Delivery removeDelivery(Delivery delivery) {
		getDeliveries().remove(delivery);
		delivery.setAddress(null);

		return delivery;
	}

}