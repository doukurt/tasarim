package com.kapici.kapici.Models;

public class Products {
    private String productName;
    private String productDetail;
    private String productCategory;
    private String productPrice;
    private String productImage;
    private String productImageName;

    public Products(String productName, String productDetail, String productCategory, String productPrice, String productImage, String productImageName) {
        this.productName = productName;
        this.productDetail = productDetail;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productImageName=productImageName;
    }

    public Products() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    public String getProductImageName() {
        return productImageName;
    }

    public void setProductImageName(String productImageName) {
        this.productImageName = productImageName;
    }
}
