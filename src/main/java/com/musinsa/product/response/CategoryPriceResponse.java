package com.musinsa.product.response;

import com.musinsa.product.dto.CategoryPriceDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryPriceResponse {
    private List<CategoryPriceDTO> categoryPriceList;
    private String totalPrice;

}
