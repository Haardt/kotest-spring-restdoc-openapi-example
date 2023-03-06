package com.conpinion.restdocexample

import com.conpinion.restdocexample.ApiVersion.V2
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/api")
class ProfileRestApi {

    @GetMapping("/profiles/{id}", consumes = [APPLICATION_JSON_VALUE, V2])
    fun getProfile(@PathVariable id: String): Mono<Profile> =
        Profile(id, true).toMono()
}