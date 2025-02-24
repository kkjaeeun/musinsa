package com.musinsa.product.dto;

import com.musinsa.product.util.PriceUtil;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CategoryInfoDTO {
    private String categoryName;
    private String price;

    public CategoryInfoDTO(String categoryName, BigDecimal price) {
        this.categoryName = categoryName;
        this.price = PriceUtil.format(price);
    }
}
