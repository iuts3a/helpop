package org.iuts3a.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SalesQuote.
 */
@Entity
@Table(name = "sales_quote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalesQuote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "quoted_at", nullable = false)
    private ZonedDateTime quotedAt;

    @ManyToOne
    private SalesQuoteItem items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getQuotedAt() {
        return quotedAt;
    }

    public SalesQuote quotedAt(ZonedDateTime quotedAt) {
        this.quotedAt = quotedAt;
        return this;
    }

    public void setQuotedAt(ZonedDateTime quotedAt) {
        this.quotedAt = quotedAt;
    }

    public SalesQuoteItem getItems() {
        return items;
    }

    public SalesQuote items(SalesQuoteItem salesQuoteItem) {
        this.items = salesQuoteItem;
        return this;
    }

    public void setItems(SalesQuoteItem salesQuoteItem) {
        this.items = salesQuoteItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SalesQuote salesQuote = (SalesQuote) o;
        if (salesQuote.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, salesQuote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SalesQuote{" +
            "id=" + id +
            ", quotedAt='" + quotedAt + "'" +
            '}';
    }
}
