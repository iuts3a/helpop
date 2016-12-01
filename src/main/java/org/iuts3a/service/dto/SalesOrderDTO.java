package org.iuts3a.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the SalesOrder entity.
 */
public class SalesOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime orderedAt;


    private Long itemsId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(ZonedDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public Long getItemsId() {
        return itemsId;
    }

    public void setItemsId(Long salesOrderItemId) {
        this.itemsId = salesOrderItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalesOrderDTO salesOrderDTO = (SalesOrderDTO) o;

        if ( ! Objects.equals(id, salesOrderDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SalesOrderDTO{" +
            "id=" + id +
            ", orderedAt='" + orderedAt + "'" +
            '}';
    }
}
