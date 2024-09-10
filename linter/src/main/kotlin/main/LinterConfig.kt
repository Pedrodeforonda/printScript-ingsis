package main

import com.fasterxml.jackson.annotation.JsonProperty

data class LinterConfig(
    var identifier_format: String = "",
    @JsonProperty("mandatory-variable-or-literal-in-println") var restrictPrintln: Boolean = false,
    @JsonProperty("mandatory-variable-or-literal-in-readInput") var restrictReadInput: Boolean = false,
)
