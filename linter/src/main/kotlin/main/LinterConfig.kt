package main.kotlin.main

import com.fasterxml.jackson.annotation.JsonProperty

data class LinterConfig(
    var identifier_format: String = "",
    @JsonProperty("mandatory-variable-or-literal-in-println") var restrictPrintln: Boolean = false
)
