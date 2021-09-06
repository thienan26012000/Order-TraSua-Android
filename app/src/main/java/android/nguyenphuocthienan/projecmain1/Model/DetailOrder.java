package android.nguyenphuocthienan.projecmain1.Model;

import android.os.Parcel;

import java.io.Serializable;

public class DetailOrder {
    private String OrderNo;
    private String name;
    private String imageUrl;
    private String status;
    private int price;
    private int numProduct;

    public DetailOrder(){
    }

    public DetailOrder(String name, String imageUrl, String status, int price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.status = status;
        this.price = price;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public int getNumProduct() {
        return numProduct;
    }

    public void setNumProduct(int numProduct) {
        this.numProduct = numProduct;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
