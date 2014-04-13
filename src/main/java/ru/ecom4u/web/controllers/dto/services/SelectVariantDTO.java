package ru.ecom4u.web.controllers.dto.services;

import ru.ecom4u.web.domain.db.entities.ProductVariant;
import ru.ecom4u.web.domain.db.entities.ProductVariantOption;

import java.util.List;

/**
 * Created by Evgeny(e299792459@gmail.com) on 09.04.14.
 */
public class SelectVariantDTO {
    private ProductVariant productVariant;
    private List<ProductVariantOption> productVariantOptions;

    public SelectVariantDTO(ProductVariant productVariant, List<ProductVariantOption> productVariantOptions) {
        this.productVariant = productVariant;
        this.productVariantOptions = productVariantOptions;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    public List<ProductVariantOption> getProductVariantOptions() {
        return productVariantOptions;
    }

    public void setProductVariantOptions(List<ProductVariantOption> productVariantOptions) {
        this.productVariantOptions = productVariantOptions;
    }
}
