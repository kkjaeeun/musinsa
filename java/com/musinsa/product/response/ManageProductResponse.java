package com.musinsa.product.response;

import com.musinsa.product.entity.Brand;
import com.musinsa.product.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ManageProductResponse {
    private List<Brand> brands;
    private List<Category> categories;
}
