package com.vsvet.example.onlinebanking.view;

import com.vsvet.example.onlinebanking.validator.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import javax.persistence.Column;
import java.time.Instant;

public class ClientView {

    @NotNullOrBlank(groups = {OnCreate.class},fieldName = "email")
    @EmailNotUsed(groups = {OnCreate.class})
    @EmailFormat(groups = {OnCreate.class})
    private String email;

    @NotNullOrBlank(groups = {OnCreate.class},fieldName = "firstName")
    private String firstName;

    @NotNullOrBlank(groups = {OnCreate.class},fieldName = "lastName")
    private String lastName;

    @NotNullOrBlank(groups = {OnCreate.class},fieldName = "phoneNumber")
    @PhoneFormat(groups = {OnCreate.class})
    private String phoneNumber;

    @NotNullOrBlank(groups = {OnCreate.class},fieldName = "identityCard")
    @FieldLength(groups = {OnCreate.class},minLength = 12,maxLength = 14,fieldName = "identityCard")
    private String identityCard;

    @NotNullOrBlank(groups = {OnCreate.class},fieldName = "address")
    private String address;

    @NotNullOrBlank(groups = {OnCreate.class},fieldName = "password")
    @FieldLength(groups = {OnCreate.class},minLength = 8,fieldName = "password")
    private String password;

    @NotNullOrBlank(groups = {OnCreate.class},fieldName = "confirmPassword")
    private String confirmPassword;

    private ClientStatusView status;

    private Instant createdDate;

    private Instant modifiedDate;

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

    public ClientStatusView getStatus() {
        return status;
    }

    public void setStatus(ClientStatusView status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public interface OnCreate{}

    public interface OnUpdate{}
}
