package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.dto.request.CreateCouponRequest
import com.jvmhater.moduticket.dto.request.UpdateCouponRequest
import com.jvmhater.moduticket.dto.response.CouponResponse
import com.jvmhater.moduticket.service.CouponService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/coupons")
class CouponController(val couponService: CouponService) {

    @Operation(description = "특정 쿠폰 이름에 맞는 할인 쿠폰 목록을 조회한다.")
    @GetMapping
    suspend fun viewCoupons(@RequestParam("coupon_name") couponName: String): List<CouponResponse> {
        return couponService.findCoupons(couponName).map { CouponResponse.from(it) }
    }

    @Operation(description = "특정 할인 쿠폰을 조회한다.")
    @GetMapping("/{coupon_id}")
    suspend fun viewCoupon(@PathVariable("coupon_id") couponId: String): CouponResponse {
        return CouponResponse.from(couponService.find(couponId))
    }

    @Operation(description = "특정 할인 쿠폰을 생성한다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createCoupon(
        @RequestBody createCouponRequest: CreateCouponRequest
    ): CouponResponse {
        return CouponResponse.from(couponService.create(createCouponRequest.toDomain()))
    }

    @Operation(description = "특정 할인 쿠폰을 수정한다.")
    @PutMapping("/{coupon_id}")
    suspend fun updateCoupon(
        @PathVariable("coupon_id") couponId: String,
        @RequestBody updateCouponRequest: UpdateCouponRequest,
    ): CouponResponse {
        return CouponResponse.from(couponService.update(updateCouponRequest.toDomain(couponId)))
    }

    @Operation(description = "특정 할인 쿠폰을 삭제한다.")
    @DeleteMapping("/{coupon_id}")
    suspend fun deleteCoupon(@PathVariable("coupon_id") couponId: String) {
        couponService.delete(couponId)
    }

    @Operation(description = "할인 쿠폰을 특정 유저에게 발급한다.")
    @PostMapping("/{coupon_id}")
    suspend fun issueCoupon(@PathVariable("coupon_id") couponId: String): CouponResponse {
        val userId = "userId" // TODO: JWT 토큰 페이로드에서 user_id 가져오는 로직 필요.
        return CouponResponse.from(couponService.issueCoupon(userId, couponId))
    }
}
