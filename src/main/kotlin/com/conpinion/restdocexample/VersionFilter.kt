package com.conpinion.restdocexample

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class VersionFilter : WebFilter {

  override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
    val builtRequest = addVersionHeaderIfNotPresent(exchange.request)
    val mutatedExchange = exchange.mutate().request(builtRequest).build()
    return chain.filter(mutatedExchange)
  }

  internal fun addVersionHeaderIfNotPresent(request: ServerHttpRequest): ServerHttpRequest {
    val contentTypeHeader = request.headers[HttpHeaders.CONTENT_TYPE]

    val hasApplicationJson = contentTypeHeader?.contains(MediaType.APPLICATION_JSON_VALUE) == true

    var requestBuilder = request.mutate()
    if (hasApplicationJson) {
      requestBuilder = requestBuilder.headers { headers ->
        headers[HttpHeaders.CONTENT_TYPE] = contentTypeHeader?.filter { contentTypeHeader ->
          !contentTypeHeader.equals(MediaType.APPLICATION_JSON_VALUE)
        }
      }
    }
    if (contentTypeHeader == null || contentTypeHeader.none(ApiVersion.versions::contains)) {
      requestBuilder = requestBuilder.header(HttpHeaders.CONTENT_TYPE, ApiVersion.V1)
    }

    return requestBuilder.build()
  }

}
