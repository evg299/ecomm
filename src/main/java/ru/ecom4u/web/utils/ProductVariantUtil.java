package ru.ecom4u.web.utils;

import ru.ecom4u.web.domain.db.entities.ProductVariantOption;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeny(e299792459@gmail.com) on 03.04.14.
 */
public class ProductVariantUtil {

    public static List<ProductVariantOption> getOptionsNullUnit(List<ProductVariantOption> productVariantOptions) {
        List<ProductVariantOption> result = new ArrayList<ProductVariantOption>();
        for (ProductVariantOption productVariantOption : productVariantOptions) {
            if (null == productVariantOption.getUnit())
                result.add(productVariantOption);
        }
        return result;
    }
}
