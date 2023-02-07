package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.dto.request.CreateCouponRequest
import com.jvmhater.moduticket.dto.request.IssueCouponRequest
import com.jvmhater.moduticket.dto.request.UpdateCouponRequest
import com.jvmhater.moduticket.dto.response.CouponResponse
import com.jvmhater.moduticket.dto.response.toResponse
import com.jvmhater.moduticket.dto.response.toResponses
import com.jvmhater.moduticket.service.CouponService
import com.jvmhater.moduticket.util.createHandle
import com.jvmhater.moduticket.util.deleteHandle
import com.jvmhater.moduticket.util.handle
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/coupons")
class CouponController(val couponService: CouponService) {

    @Operation(description = "특정 쿠폰 이름에 맞는 할인 쿠폰 목록을 조회한다.")
    @GetMapping
    suspend fun viewCoupons(
        @RequestParam("coupon_name") couponName: String,
    ): ResponseEntity<List<CouponResponse>> = handle {
        couponService.findCoupons(couponName).toResponses()
    }

    @Operation(description = "특정 할인 쿠폰을 조회한다.")
    @GetMapping("/{coupon_id}")
    suspend fun viewCoupon(
        @PathVariable("coupon_id") couponId: String,
    ): ResponseEntity<CouponResponse> = handle { couponService.find(couponId).toResponse() }

    @Operation(description = "특정 할인 쿠폰을 생성한다.")
    @PostMapping
    suspend fun createCoupon(
        @RequestBody request: CreateCouponRequest,
    ): ResponseEntity<CouponResponse> = createHandle {
        couponService.create(request.toDomain()).toResponse()
    }

    @Operation(description = "특정 할인 쿠폰을 수정한다.")
    @PutMapping("/{coupon_id}")
    suspend fun updateCoupon(
        @PathVariable("coupon_id") couponId: String,
        @RequestBody request: UpdateCouponRequest,
    ): ResponseEntity<CouponResponse> = handle {
        couponService.update(request.toDomain(couponId)).toResponse()
    }

    @Operation(description = "특정 할인 쿠폰을 삭제한다.")
    @DeleteMapping("/{coupon_id}")
    suspend fun deleteCoupon(@PathVariable("coupon_id") couponId: String) = deleteHandle {
        couponService.delete(couponId)
    }

    @Operation(description = "할인 쿠폰을 특정 유저에게 발급한다.")
    @PostMapping("/issue-coupon")
    suspend fun issueCoupon(
        @RequestBody request: IssueCouponRequest,
    ): ResponseEntity<CouponResponse> = createHandle {
        couponService.issueCoupon(request.userId, request.couponId).toResponse()
    }
}
