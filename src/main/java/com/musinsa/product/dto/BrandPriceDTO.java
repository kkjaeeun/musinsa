package com.musinsa.product.dto;

import com.musinsa.product.util.PriceUtil;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BrandPriceDTO {
    private String brandName;
    private String price;

    public BrandPriceDTO(String brandName, BigDecimal price) {
        this.brandName = brandName;
        this.price = PriceUtil.format(price);
    }
}
