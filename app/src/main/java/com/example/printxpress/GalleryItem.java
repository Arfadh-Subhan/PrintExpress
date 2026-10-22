package com.example.printxpress;

public class GalleryItem {

    private int id;
    private String title;
    private String description;
    private String imageName;
    private int productId;

    public GalleryItem(int id, String title, String description, String imageName, int productId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageName = imageName;
        this.productId = productId;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageName() { return imageName; }
    public int getProductId() { return productId; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public void setProductId(int productId) { this.productId = productId; }
}