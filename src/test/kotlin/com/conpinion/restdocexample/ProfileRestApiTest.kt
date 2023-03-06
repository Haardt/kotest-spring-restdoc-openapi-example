package com.conpinion.restdocexample

import com.epages.restdocs.apispec.Schema
import com.epages.restdocs.apispec.WebTestClientRestDocumentationWrapper
import com.epages.restdocs.apispec.WebTestClientRestDocumentationWrapper.document
import com.epages.restdocs.apispec.WebTestClientRestDocumentationWrapper.resourceDetails
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.testContextManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
class ProfileRestApiTest : DescribeSpec({

    describe("Test the profile rest api") {
        it("blub") {
            getWinTestClient()
                .get()
                .uri("/api/profiles/{id}", "profileId")
                .header(HttpHeaders.CONTENT_TYPE, ApiVersion.V2, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith(
                    document(
                        "GET/api/profiles/id",
                        resourceDetails = resourceDetails()
                            .responseSchema(Schema("profile"))
                            .description("""This resource return the profile for the given id.""".trimMargin())
                            .summary("Get the profile data."),
                        snippets = arrayOf(
                            requestHeaders(
                                headerWithName("Content-Type")
                                    .description("The version definition. Compatible with V2")
                            ),
                            pathParameters(
                                parameterWithName("id")
                                    .description(
                                        "The unique id of the profile resource"
                                    )
                            ),
                            responseFields(
                                fieldWithPath("id").description("The unique id of the profile"),
                                fieldWithPath("darkMode").description("Is the dark mode active"),
                            )
                        ),
                    )
                )
        }
    }
}) {
    override fun extensions() = listOf(RestDocExtension(), SpringExtension)

}

suspend fun getWinTestClient(): WebTestClient =
    WebTestClient
        .bindToApplicationContext(testContextManager().testContext.applicationContext)
        .configureClient()
        .filter(WebTestClientRestDocumentation.documentationConfiguration(manualRestDocumentation()))
        .build()
