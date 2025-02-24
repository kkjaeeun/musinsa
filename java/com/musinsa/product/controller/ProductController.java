package com.musinsa.product.controller;

import com.musinsa.product.param.*;
import com.musinsa.product.response.ResponseObject;
import com.musinsa.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "브랜드 등록", description = "새로운 브랜드를 등록한다.")
    @PostMapping("/brand/add")
    public ResponseObject registerBrand(@RequestBody @Valid BrandParam param) {
        return productService.registerBrand(param);
    }

    @Operation(summary = "상품 등록 (브랜드명, 카테고리명으로 등록)", description = "브랜드의 상품을 등록한다.")
    @PostMapping("/add-name")
    public ResponseObject registerProductByName(@RequestBody @Valid ProductParam param) {
        return productService.registerProductByName(param);
    }

    @Operation(summary = "상품 등록", description = "브랜드의 상품을 등록한다.")
    @PostMapping("/add")
    public ResponseObject registerProduct(@RequestBody @Valid ProductRegisterParam param) {
        return productService.registerProduct(param);
    }

    @Operation(summary = "상품 수정", description = "상품을 수정한다.")
    @PutMapping("/update")
    public ResponseObject modifyProduct(@RequestBody @Valid ProductUpdateParam param) {
        return productService.modifyProduct(param);
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제한다.")
    @DeleteMapping("/delete")
    public ResponseObject removeProduct(@RequestBody @Valid ProductDeleteParam param) {
        return productService.removeProduct(param);
    }

    @Operation(summary = "상품 리스트 조회 (화면용)", description = "전체 상품을 조회한다.")
    @GetMapping("/list")
    public ResponseObject getProductList() {
        return productService.getProductList();
    }
}
