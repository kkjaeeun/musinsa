package com.musinsa.product.service;

import com.musinsa.product.entity.Brand;
import com.musinsa.product.entity.Category;
import com.musinsa.product.entity.ProductInfo;
import com.musinsa.product.param.*;
import com.musinsa.product.repository.BrandRepository;
import com.musinsa.product.repository.CategoryRepository;
import com.musinsa.product.repository.ProductInfoRepository;
import com.musinsa.product.response.ResponseObject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductInfoRepository productInfoRepository;

    /**
     * 브랜드 등록
     *
     * @param param
     * @return
     */
    public ResponseObject registerBrand(BrandParam param) {
        // 브랜드명 빈값일때
        if (null == param.getName() || "".equals(param.getName())) {
            return new ResponseObject("Error", "브랜드 이름을 입력해주세요.");
        }

        try {
            Brand brandInfo = brandRepository.findFirstByName(param.getName());

            if (brandInfo != null) {
                return new ResponseObject("Error", "이미 존재하는 브랜드입니다.");
            }

            Brand brand = new Brand();
            brand.setName(param.getName());

            brandRepository.save(brand);

            return new ResponseObject("Success", "등록되었습니다.", brandRepository.findAll());
        } catch (Exception e) {
            return new ResponseObject("Error", e.getMessage());
        }
    }

    /**
     * 상품 등록 (브랜드명, 카테고리명 직접 입력하여 등록)
     *
     * @param param
     * @return
     */
    @Transactional
    public ResponseObject registerProductByName(ProductParam param) {
        try {
            // category id 조회
            Category category = categoryRepository.findFirstByName(param.getCategoryName());
            if (category == null) {
                return new ResponseObject("Error", "존재하지 않는 카테고리입니다.");
            }
            // brand id 조회
            Brand brandInfo = brandRepository.findFirstByName(param.getBrandName());
            // 없는 브랜드일 경우 추가
            if (brandInfo == null) {
                Brand brand = new Brand();
                brand.setName(param.getBrandName());
                brandInfo = brandRepository.save(brand);
            }

            ProductInfo productInfo = new ProductInfo();
            productInfo.setBrand(brandInfo);
            productInfo.setCategory(category);
            productInfo.setPrice(param.getPrice());

            productInfoRepository.save(productInfo);
            return new ResponseObject("Success", "등록되었습니다.");
        } catch (Exception e) {
            return new ResponseObject("Error", e.getMessage());
        }
    }

    /**
     * 상품 등록
     *
     * @param param
     * @return
     */
    @Transactional
    public ResponseObject registerProduct(ProductRegisterParam param) {
        try {
            Category category = categoryRepository.findById(param.getCategoryId())
                    .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리입니다."));

            Brand brand = brandRepository.findById(param.getBrandId())
                    .orElseThrow(() -> new NoSuchElementException("존재하지 않는 브랜드입니다."));

            ProductInfo productInfo = new ProductInfo();
            productInfo.setBrand(brand);
            productInfo.setCategory(category);
            productInfo.setPrice(param.getPrice());

            productInfoRepository.save(productInfo);
            return new ResponseObject("Success", "등록되었습니다.");

        } catch (NoSuchElementException e) {
            return new ResponseObject("Error", e.getMessage());
        } catch (Exception e) {
            return new ResponseObject("Error", e.getMessage());
        }
    }

    /**
     * 상품 수정
     *
     * @param param
     * @return
     */
    @Transactional
    public ResponseObject modifyProduct(ProductUpdateParam param) {
        try {
            // 상품 유효성 체크
            ProductInfo originProduct = productInfoRepository.findById(param.getProductId())
                    .orElseThrow(() -> new NoSuchElementException("상품을 찾을수 없습니다."));

            // 카테고리 변경되었을 경우 유효성 체크
            if (param.getCategoryId() != null && param.getCategoryId() != originProduct.getCategory().getId()) {
                Category category = categoryRepository.findById(param.getCategoryId())
                        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리입니다."));

                originProduct.setCategory(category);
            }

            // 브랜드 변경되었을 경우 유효성 체크
            if (param.getBrandId() != null && param.getBrandId() != originProduct.getBrand().getId()) {
                Brand brand = brandRepository.findById(param.getBrandId())
                        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 브랜드입니다."));
                originProduct.setBrand(brand);
            }

            if (originProduct.getPrice() != param.getPrice()) {
                originProduct.setPrice(param.getPrice());
            }


            productInfoRepository.save(originProduct);
            return new ResponseObject("Success", "수정되었습니다.");

        } catch (NoSuchElementException e) {
            return new ResponseObject("Error", e.getMessage());
        } catch (Exception e) {
            return new ResponseObject("Error", e.getMessage());
        }
    }

    /**
     * 상품 삭제
     *
     * @param param
     * @return
     */
    @Transactional
    public ResponseObject removeProduct(ProductDeleteParam param) {
        try {
            if (!productInfoRepository.existsById(param.getProductId())) {
                return new ResponseObject("Error", "존재하지 않는 상품입니다.");
            }

            productInfoRepository.deleteById(param.getProductId());
            return new ResponseObject("Success", "삭제되었습니다.");
        } catch (Exception e) {
            return new ResponseObject("Error", e.getMessage());
        }
    }

    /**
     * 상품 리스트 조회 (화면용)
     * @return
     */
    public ResponseObject getProductList() {
        Sort sort = Sort.by(Sort.Order.asc("brandId"), Sort.Order.asc("categoryId"));
        List<ProductInfo> productList = productInfoRepository.findAll(sort);
        return new ResponseObject(productList);
    }
}
