package org.iuts3a.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Customer entity.
 */
public class CustomerDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private String consummerGrade;

    private String offererGrade;


    private Long salesQuoteId;
    
    private Long addressId;
    
    private Set<SalesOrderDTO> orders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getConsummerGrade() {
        return consummerGrade;
    }

    public void setConsummerGrade(String consummerGrade) {
        this.consummerGrade = consummerGrade;
    }
    public String getOffererGrade() {
        return offererGrade;
    }

    public void setOffererGrade(String offererGrade) {
        this.offererGrade = offererGrade;
    }

    public Long getSalesQuoteId() {
        return salesQuoteId;
    }

    public void setSalesQuoteId(Long salesQuoteId) {
        this.salesQuoteId = salesQuoteId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Set<SalesOrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(Set<SalesOrderDTO> salesOrders) {
        this.orders = salesOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;

        if ( ! Objects.equals(id, customerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", password='" + password + "'" +
            ", email='" + email + "'" +
            ", consummerGrade='" + consummerGrade + "'" +
            ", offererGrade='" + offererGrade + "'" +
            '}';
    }
}
