package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.readResourceFile
import com.jvmhater.moduticket.testcontainers.TestMySQLContainer
import io.kotest.core.spec.style.DescribeSpec
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestR2bcConfiguration::class])
class SpringDataCouponRepositoryTest(
    r2dbcCouponRepository: R2dbcCouponRepository,
) : DescribeSpec({ afterEach { TestMySQLContainer.sql(readResourceFile("ddl/truncate.sql")) } }) {

    private val couponRepository = SpringDataCouponRepository(r2dbcCouponRepository)

    init {

    }
}
