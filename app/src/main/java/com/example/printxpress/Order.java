package com.example.printxpress;

public class Order {

    private int id;
    private int userId;
    private int productId;
    private String productName;
    private int quantity;
    private String totalPrice;
    private String status;
    private String orderDate;
    private String deliveryOption;
    private String address;
    private String filePath;
    private String paymentMethod;
    private String notes;
    private String productImage;

    public Order(int id, int userId, int productId, String productName, int quantity,
                 String totalPrice, String status, String orderDate,
                 String deliveryOption, String address, String filePath,
                 String paymentMethod, String notes, String productImage) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = orderDate;
        this.deliveryOption = deliveryOption;
        this.address = address;
        this.filePath = filePath;
        this.paymentMethod = paymentMethod;
        this.notes = notes;
        this.productImage = productImage;
    }

    // Getters & Setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public String getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public String getOrderDate() { return orderDate; }
    public String getDeliveryOption() { return deliveryOption; }
    public String getAddress() { return address; }
    public String getFilePath() { return filePath; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getNotes() { return notes; }
    public String getProductImage() { return productImage; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setProductId(int productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setTotalPrice(String totalPrice) { this.totalPrice = totalPrice; }
    public void setStatus(String status) { this.status = status; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    public void setDeliveryOption(String deliveryOption) { this.deliveryOption = deliveryOption; }
    public void setAddress(String address) { this.address = address; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setProductImage(String productImage) { this.productImage = productImage; }
}