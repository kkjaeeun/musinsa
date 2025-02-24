package com.musinsa.product.service;

import com.musinsa.product.repository.BrandRepository;
import com.musinsa.product.repository.CategoryRepository;
import com.musinsa.product.response.ManageProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HomeService {
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    /**
     * 브랜드, 카테고리 정보 조회
     *
     * @return
     */
    public ManageProductResponse getManageProduct() {
        ManageProductResponse response = new ManageProductResponse();

        response.setBrands(brandRepository.findAll());
        response.setCategories(categoryRepository.findAll());

        return response;
    }
}
