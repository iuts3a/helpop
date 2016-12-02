package org.iuts3a.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "consummer_grade")
    private String consummerGrade;

    @Column(name = "offerer_grade")
    private String offererGrade;

    @OneToOne
    @JoinColumn(unique = true)
    private SalesQuote salesQuote;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "customer_orders",
               joinColumns = @JoinColumn(name="customers_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="orders_id", referencedColumnName="ID"))
    private Set<SalesOrder> orders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public Customer password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConsummerGrade() {
        return consummerGrade;
    }

    public Customer consummerGrade(String consummerGrade) {
        this.consummerGrade = consummerGrade;
        return this;
    }

    public void setConsummerGrade(String consummerGrade) {
        this.consummerGrade = consummerGrade;
    }

    public String getOffererGrade() {
        return offererGrade;
    }

    public Customer offererGrade(String offererGrade) {
        this.offererGrade = offererGrade;
        return this;
    }

    public void setOffererGrade(String offererGrade) {
        this.offererGrade = offererGrade;
    }

    public SalesQuote getSalesQuote() {
        return salesQuote;
    }

    public Customer salesQuote(SalesQuote salesQuote) {
        this.salesQuote = salesQuote;
        return this;
    }

    public void setSalesQuote(SalesQuote salesQuote) {
        this.salesQuote = salesQuote;
    }

    public Address getAddress() {
        return address;
    }

    public Customer address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<SalesOrder> getOrders() {
        return orders;
    }

    public Customer orders(Set<SalesOrder> salesOrders) {
        this.orders = salesOrders;
        return this;
    }

    public Customer addOrders(SalesOrder salesOrder) {
        orders.add(salesOrder);
        return this;
    }

    public Customer removeOrders(SalesOrder salesOrder) {
        orders.remove(salesOrder);
        return this;
    }

    public void setOrders(Set<SalesOrder> salesOrders) {
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
        Customer customer = (Customer) o;
        if (customer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
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
