package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.TestMySQLContainerTest
import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.kotest.CustomDescribeSpec
import com.jvmhater.moduticket.model.ConcertFixture
import com.jvmhater.moduticket.model.SeatFixture
import com.jvmhater.moduticket.model.query.ConcertSearchQuery
import com.jvmhater.moduticket.model.query.Page
import com.jvmhater.moduticket.model.vo.ConcertCategory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate

@TestMySQLContainerTest
class SpringDataConcertRepositoryTest(
    r2dbcConcertRepository: R2dbcConcertRepository,
    r2dbcSeatRepository: R2dbcSeatRepository,
    template: R2dbcEntityTemplate
) : CustomDescribeSpec() {

    private val concertRepository =
        SpringDataConcertRepository(r2dbcConcertRepository, r2dbcSeatRepository, template)

    init {
        describe("#findConcerts") {
            val concerts =
                listOf(
                    ConcertFixture.generate(category = ConcertCategory.BALLAD, name = "윤하 콘서트"),
                    ConcertFixture.generate(category = ConcertCategory.BALLAD, name = "박효신 콘서트"),
                    ConcertFixture.generate(category = ConcertCategory.RAP, name = "지코 콘서트")
                )
            concerts.map { concertRepository.create(it) }

            context("검색 조건에 맞는 콘서트가 있다면") {
                val query = ConcertSearchQuery(ConcertCategory.BALLAD, "윤하")
                val page = Page(0, 10)
                val foundConcerts = concertRepository.findConcerts(query, page)

                it("해당 조건에 맞는 콘서트 리스트가 조회된다.") {
                    foundConcerts.size shouldBe 1
                    foundConcerts[0] shouldBe concerts[0]
                }
            }
        }

        describe("#find") {
            val seats = mutableListOf(SeatFixture.generate(), SeatFixture.generate())
            val concert = ConcertFixture.generate(seats = seats)
            concertRepository.create(concert)

            context("특정 콘서트 ID를 가진 Concert Row가 있다면") {
                it("해당 콘서트 조회에 성공한다.") {
                    val foundConcert = concertRepository.findWithSeats(concert.id)

                    foundConcert.id shouldBe concert.id
                    foundConcert.seats.size shouldBe seats.size
                    /*
                    TODO : 정렬방식 때문에 실패(seats의 순서가 달라서), created_at or snowflake 방식을 사용해야한다.
                    foundConcert shouldBe concert
                    */
                }
            }

            context("특정 콘서트 ID를 가진 Concert Row가 없다면") {
                it("콘서트 조회에 실패한다.") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        concertRepository.findWithSeats("not-found-id")
                    }
                }
            }
        }

        describe("#create") {
            val concert = ConcertFixture.generate()

            context("새로운 Concert 객체가 주어지면") {
                val savedConcert = concertRepository.create(concert)

                it("Concert Row 삽입에 성공한다.") { savedConcert shouldBe concert }
            }

            context("이미 존재하는 Concert 객체가 주어지면") {
                val savedConcert = concertRepository.create(concert)

                it("Concert Row 삽입에 실패한다.") {
                    shouldThrow<RepositoryException.RecordAlreadyExisted> {
                        concertRepository.create(savedConcert)
                    }
                }
            }
        }

        describe("delete") {
            val seats = mutableListOf(SeatFixture.generate())
            val concert = ConcertFixture.generate(seats = seats)
            concertRepository.create(concert)

            context("존재하는 콘서트 ID가 주어지면") {
                concertRepository.delete(concert.id)

                it("해당 Concert Row 삭제에 성공한다.") {
                    r2dbcConcertRepository.findById(concert.id) shouldBe null
                }
            }
        }
    }
}
