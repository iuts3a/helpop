package org.iuts3a.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the SalesQuote entity.
 */
public class SalesQuoteDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime quotedAt;


    private Long itemsId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getQuotedAt() {
        return quotedAt;
    }

    public void setQuotedAt(ZonedDateTime quotedAt) {
        this.quotedAt = quotedAt;
    }

    public Long getItemsId() {
        return itemsId;
    }

    public void setItemsId(Long salesQuoteItemId) {
        this.itemsId = salesQuoteItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalesQuoteDTO salesQuoteDTO = (SalesQuoteDTO) o;

        if ( ! Objects.equals(id, salesQuoteDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SalesQuoteDTO{" +
            "id=" + id +
            ", quotedAt='" + quotedAt + "'" +
            '}';
    }
}
