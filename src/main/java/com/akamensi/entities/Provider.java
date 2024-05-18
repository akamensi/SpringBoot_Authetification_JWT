package com.akamensi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Provider {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;
    
    @NotBlank(message = "Address is mandatory")
    @Column(name = "address")
    private String address;
    
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email not valid")
    @Column(name = "email")
    private String email;
    
    @NotBlank(message = "Logo is mandatory")
    @Column(name = "logo")
    private String logo;

	public Provider() {
		super();
	}

    public Provider(String name, String address, String email, String logo) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.logo = logo;
    }


	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Provider [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", logo=" + logo
				+ "]";
	}
}
