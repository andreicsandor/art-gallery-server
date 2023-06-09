package org.server.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Item")
public class Item extends Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "buyer", nullable = false, length = 100)
    private String buyer;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    public Item() {
    }

    public Item(String name, String artist, String type, int year, String image, double price, String buyer, LocalDate saleDate, LocalDate deliveryDate) {
        super(name, artist, type, year);
        this.price = price;
        this.buyer = buyer;
        this.saleDate = saleDate;
        this.deliveryDate = deliveryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "<<ItemSold: " + super.toString() + ", Price: " + price + ", Buyer: " + buyer + ", Sale Date: " + saleDate + ", Delivery Date: " + deliveryDate + ">>";
    }
}
