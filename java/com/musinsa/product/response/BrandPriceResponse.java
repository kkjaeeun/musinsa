package com.musinsa.product.response;

import com.musinsa.product.dto.CategoryInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BrandPriceResponse {
    private String brandName;
    private List<CategoryInfoDTO> categoryInfoList;
    private String totalPrice;
}
