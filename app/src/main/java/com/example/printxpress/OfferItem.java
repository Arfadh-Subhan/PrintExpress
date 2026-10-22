package com.example.printxpress;

public class OfferItem {

    private String title;
    private String description;
    private String discount;
    private String validUntil;

    public OfferItem(String title, String description, String discount, String validUntil) {
        this.title = title;
        this.description = description;
        this.discount = discount;
        this.validUntil = validUntil;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDiscount() {
        return discount;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }
}