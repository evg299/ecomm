package ru.ecom4u.web.controllers.dto;

import ru.ecom4u.web.domain.db.entities.Product;

import java.util.Date;

/**
 * Created by Evgeny on 22.04.14.
 */
public class CardProductDTO {
    private Product product;
    private int count;
    private Date addedDate;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
}
