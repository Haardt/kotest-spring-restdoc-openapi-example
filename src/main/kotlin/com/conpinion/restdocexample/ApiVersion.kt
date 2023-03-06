package com.conpinion.restdocexample

private const val contentTypeHeaderValue = "application/vnd.conpinion.restdocexample"
private const val andJson = "+json"

object ApiVersion {

  const val V1 = "${contentTypeHeaderValue}.v1${andJson}"
  const val V2 = "${contentTypeHeaderValue}.v2${andJson}"
  const val V3 = "${contentTypeHeaderValue}.v3${andJson}"

  val versions = listOf(V1, V2, V3) // Update versions!!!!!
}