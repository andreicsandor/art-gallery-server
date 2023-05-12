package org.server.dto;

import java.time.LocalDate;

public class ItemDTO extends ExhibitDTO {
    private double price;
    private String buyer;
    private LocalDate saleDate;
    private LocalDate deliveryDate;

    public ItemDTO() {
    }

    public ItemDTO(String name, String artist, String type, int year, String gallery, String image, double price, String buyer, LocalDate saleDate, LocalDate deliveryDate) {
        super(name, artist, type, year, gallery, image);
        this.price = price;
        this.buyer = buyer;
        this.saleDate = saleDate;
        this.deliveryDate = deliveryDate;
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
}
