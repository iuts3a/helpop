package org.iuts3a.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SalesOrder.
 */
@Entity
@Table(name = "sales_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalesOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ordered_at", nullable = false)
    private ZonedDateTime orderedAt;

    @ManyToOne
    private SalesOrderItem items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getOrderedAt() {
        return orderedAt;
    }

    public SalesOrder orderedAt(ZonedDateTime orderedAt) {
        this.orderedAt = orderedAt;
        return this;
    }

    public void setOrderedAt(ZonedDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public SalesOrderItem getItems() {
        return items;
    }

    public SalesOrder items(SalesOrderItem salesOrderItem) {
        this.items = salesOrderItem;
        return this;
    }

    public void setItems(SalesOrderItem salesOrderItem) {
        this.items = salesOrderItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SalesOrder salesOrder = (SalesOrder) o;
        if (salesOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, salesOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SalesOrder{" +
            "id=" + id +
            ", orderedAt='" + orderedAt + "'" +
            '}';
    }
}
