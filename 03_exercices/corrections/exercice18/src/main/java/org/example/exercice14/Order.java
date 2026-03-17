package org.example.exercice14;

import java.util.Objects;

public class Order {
    private Long id;
    private String item;

    public Order(Long id, String item) {
        this.id = id;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(item, order.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, item);
    }
}
