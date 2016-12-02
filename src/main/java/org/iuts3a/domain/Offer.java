package org.iuts3a.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.iuts3a.domain.enumeration.OfferTimeType;

import org.iuts3a.domain.enumeration.OfferType;

/**
 * A Offer.
 */
@Entity
@Table(name = "offer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price_usd", nullable = false)
    private Double priceUSD;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "limit_at")
    private ZonedDateTime limitAt;

    @Column(name = "grade")
    private Float grade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "time_type", nullable = false)
    private OfferTimeType timeType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "offer_type", nullable = false)
    private OfferType offerType;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Customer owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Offer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceUSD() {
        return priceUSD;
    }

    public Offer priceUSD(Double priceUSD) {
        this.priceUSD = priceUSD;
        return this;
    }

    public void setPriceUSD(Double priceUSD) {
        this.priceUSD = priceUSD;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public Offer discountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
        return this;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Offer createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getLimitAt() {
        return limitAt;
    }

    public Offer limitAt(ZonedDateTime limitAt) {
        this.limitAt = limitAt;
        return this;
    }

    public void setLimitAt(ZonedDateTime limitAt) {
        this.limitAt = limitAt;
    }

    public Float getGrade() {
        return grade;
    }

    public Offer grade(Float grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public OfferTimeType getTimeType() {
        return timeType;
    }

    public Offer timeType(OfferTimeType timeType) {
        this.timeType = timeType;
        return this;
    }

    public void setTimeType(OfferTimeType timeType) {
        this.timeType = timeType;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public Offer offerType(OfferType offerType) {
        this.offerType = offerType;
        return this;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public String getDescription() {
        return description;
    }

    public Offer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getOwner() {
        return owner;
    }

    public Offer owner(Customer customer) {
        this.owner = customer;
        return this;
    }

    public void setOwner(Customer customer) {
        this.owner = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Offer offer = (Offer) o;
        if (offer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, offer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Offer{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", priceUSD='" + priceUSD + "'" +
            ", discountPercent='" + discountPercent + "'" +
            ", createdAt='" + createdAt + "'" +
            ", limitAt='" + limitAt + "'" +
            ", grade='" + grade + "'" +
            ", timeType='" + timeType + "'" +
            ", offerType='" + offerType + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
