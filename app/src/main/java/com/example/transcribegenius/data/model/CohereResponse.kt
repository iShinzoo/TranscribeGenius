package com.example.transcribegenius.data.model


data class CohereResponse(
    val id: String,
    val generations: List<Generation>,
    val prompt: String,
    val meta: Meta
)

data class Generation(
    val id: String,
    val text: String,
    val finish_reason: String
)

data class Meta(
    val api_version: ApiVersion,
    val billed_units: BilledUnits
)

data class ApiVersion(
    val version: String
)

data class BilledUnits(
    val input_tokens: Int,
    val output_tokens: Int
)
