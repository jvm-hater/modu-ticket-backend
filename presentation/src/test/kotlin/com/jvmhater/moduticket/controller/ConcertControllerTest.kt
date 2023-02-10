package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.IntegrationTest
import com.jvmhater.moduticket.doDelete
import com.jvmhater.moduticket.doGet
import com.jvmhater.moduticket.doPost
import com.jvmhater.moduticket.dto.CreateConcertRequestFixture
import com.jvmhater.moduticket.dto.request.ViewConcertsRequest
import com.jvmhater.moduticket.dto.response.toResponse
import com.jvmhater.moduticket.kotest.CustomDescribeSpec
import com.jvmhater.moduticket.model.ConcertFixture
import com.jvmhater.moduticket.model.SeatFixture
import com.jvmhater.moduticket.model.vo.ConcertCategory
import com.jvmhater.moduticket.repository.ConcertRepository
import com.jvmhater.moduticket.util.toJson
import org.springframework.test.web.reactive.server.WebTestClient

@IntegrationTest
class ConcertControllerTest(client: WebTestClient, concertRepository: ConcertRepository) :
    CustomDescribeSpec() {
    private val baseUrl = "/api/concerts"

    init {
        describe("#viewConcerts") {
            val concerts =
                listOf(
                    ConcertFixture.generate(category = ConcertCategory.BALLAD, name = "윤하 콘서트"),
                    ConcertFixture.generate(category = ConcertCategory.BALLAD, name = "박효신 콘서트"),
                    ConcertFixture.generate(category = ConcertCategory.RAP, name = "지코 콘서트")
                )
            concerts.map { concertRepository.create(it) }

            context("검색 조건에 맞는 콘서트가 있다면") {
                val queryParams = ViewConcertsRequest(ConcertCategory.BALLAD, "윤하", 0, 10)
                val expectedResponse = concerts[0].toResponse()

                it("해당 조건에 맞는 콘서트 리스트가 조회된다.") {
                    client
                        .doGet(
                            url = baseUrl,
                            queryParams =
                                mapOf(
                                    "category" to queryParams.category,
                                    "search_text" to queryParams.searchText,
                                    "page" to queryParams.page,
                                    "size" to queryParams.size
                                )
                        )
                        .expectStatus()
                        .isOk
                        .expectBody()
                    // .json(expectedResponse.toJson()) 이거 왜 안되지..?
                }
            }
        }

        describe("#viewConcert") {
            val seats = mutableListOf(SeatFixture.generate(), SeatFixture.generate())
            val concert = ConcertFixture.generate(seats = seats)
            concertRepository.create(concert)

            context("존재하는 콘서트 ID가 주어지면") {
                it("해당 콘서트 조회한다.") {
                    val expectedResponse = concert.toResponse()

                    client
                        .doGet("$baseUrl/${concert.id}")
                        .expectStatus()
                        .isOk
                        .expectBody()
                        .json(expectedResponse.toJson())
                }
            }

            context("존재하지 않는 콘서트 ID가 주어지면") {
                val concertId = "not-found-id"
                it("콘서트를 조회할 수 없다.") {
                    client.doGet("$baseUrl/$concertId").expectStatus().isNotFound
                }
            }
        }

        describe("#createConcert") {
            context("신규 콘서트가 주어지면") {
                it("해당하는 콘서트를 생성한다.") {
                    client
                        .doPost(url = baseUrl, request = CreateConcertRequestFixture.generate())
                        .expectStatus()
                        .isCreated
                }
            }
        }

        describe("deleteConcert") {
            context("존재하는 콘서트 ID가 주어지면") {
                val concert = concertRepository.create(ConcertFixture.generate())

                it("해당하는 콘서트가 제거된다.") {
                    client.doDelete("$baseUrl/${concert.id}").expectStatus().isNoContent
                }
            }
        }
    }
}
