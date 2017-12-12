package com.vsvet.example.onlinebanking.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client extends AbstractEntity {

    /**
     * In distributed environment we can use UUID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    /**
     * This represents identity card, the format depends on issued country.
     */
    @Column(name="identity_card",nullable = false)
    private String identityCard;

    /**
     * For simple example will use string field,
     * otherwise can be depicted in few fields or even be saved in separate table.
     */
    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientStatus status;

    @Column(name="password",nullable = false)
    private String password;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(getEmail(), client.getEmail()) &&
                Objects.equals(getFirstName(), client.getFirstName()) &&
                Objects.equals(getLastName(), client.getLastName()) &&
                Objects.equals(getPhoneNumber(), client.getPhoneNumber()) &&
                Objects.equals(getIdentityCard(), client.getIdentityCard()) &&
                Objects.equals(getAddress(), client.getAddress()) &&
                getStatus() == client.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getFirstName(), getLastName(), getPhoneNumber(), getIdentityCard(), getAddress(), getStatus());
    }
}
