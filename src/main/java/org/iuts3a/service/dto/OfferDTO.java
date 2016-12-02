package org.iuts3a.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.iuts3a.domain.enumeration.OfferTimeType;
import org.iuts3a.domain.enumeration.OfferType;

/**
 * A DTO for the Offer entity.
 */
public class OfferDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double priceUSD;

    private Double discountPercent;

    @NotNull
    private ZonedDateTime createdAt;

    private ZonedDateTime limitAt;

    private Float grade;

    @NotNull
    private OfferTimeType timeType;

    @NotNull
    private OfferType offerType;

    private String description;


    private Long ownerId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Double getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(Double priceUSD) {
        this.priceUSD = priceUSD;
    }
    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public ZonedDateTime getLimitAt() {
        return limitAt;
    }

    public void setLimitAt(ZonedDateTime limitAt) {
        this.limitAt = limitAt;
    }
    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }
    public OfferTimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(OfferTimeType timeType) {
        this.timeType = timeType;
    }
    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long customerId) {
        this.ownerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfferDTO offerDTO = (OfferDTO) o;

        if ( ! Objects.equals(id, offerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OfferDTO{" +
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
