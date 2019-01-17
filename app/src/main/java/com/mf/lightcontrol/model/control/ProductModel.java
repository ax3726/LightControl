package com.mf.lightcontrol.model.control;

/**
 * Created by LiMing
 * Date 2019/1/17
 */
public class ProductModel {
    private int CommType;
    private String Product;

    public ProductModel(String product) {
        CommType = 2;
        Product = product;
    }

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }
}
