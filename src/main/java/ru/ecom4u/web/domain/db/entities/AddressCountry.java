package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the address_countries database table.
 * 
 */
@Entity
@Table(name="address_countries")
public class AddressCountry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Lob
	@Column(nullable=false)
	private String name;

	//bi-directional many-to-one association to AddressState
	@OneToMany(mappedBy="addressCountry")
	private List<AddressState> addressStates;

	public AddressCountry() {
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

	public List<AddressState> getAddressStates() {
		return this.addressStates;
	}

	public void setAddressStates(List<AddressState> addressStates) {
		this.addressStates = addressStates;
	}

	public AddressState addAddressState(AddressState addressState) {
		getAddressStates().add(addressState);
		addressState.setAddressCountry(this);

		return addressState;
	}

	public AddressState removeAddressState(AddressState addressState) {
		getAddressStates().remove(addressState);
		addressState.setAddressCountry(null);

		return addressState;
	}

}