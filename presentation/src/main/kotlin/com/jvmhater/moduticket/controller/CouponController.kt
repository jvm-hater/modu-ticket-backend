package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.dto.response.CouponResponse
import com.jvmhater.moduticket.dto.response.IssueCouponResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/coupons")
class CouponController {

    @Operation(description = "모든 할인 쿠폰 목록을 조회한다.")
    @GetMapping
    suspend fun viewCoupons(): List<CouponResponse> {
        return listOf(CouponResponse())
    }

    @Operation(description = "특정 할인 쿠폰을 조회한다.")
    @GetMapping("/{coupon_id}")
    suspend fun viewCoupon(@PathVariable("coupon_id") couponId: String): CouponResponse {
        return CouponResponse()
    }

    @Operation(description = "특정 할인 쿠폰을 생성한다.")
    @PostMapping
    suspend fun createCoupon(): CouponResponse {
        return CouponResponse()
    }

    @Operation(description = "특정 할인 쿠폰을 수정한다.")
    @PutMapping
    suspend fun updateCoupon(): CouponResponse {
        return CouponResponse()
    }

    @Operation(description = "특정 할인 쿠폰을 삭제한다.")
    @DeleteMapping
    suspend fun deleteCoupon(): CouponResponse {
        return CouponResponse()
    }

    /*
    TODO: Authorization 헤더에 있는 JWT 토큰의 페이 로드(user_id)를 토대로 아규먼트 리졸버에게 넘겨서 User를 받아와야 합니다.
     */
    @Operation(description = "할인 쿠폰을 특정 유저에게 발급한다.")
    @PostMapping("/{coupon_id}")
    suspend fun issueCoupon(@PathVariable("coupon_id") couponId: String): IssueCouponResponse {
        return IssueCouponResponse()
    }
}