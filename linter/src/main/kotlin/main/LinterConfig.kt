package main

import com.google.gson.annotations.SerializedName

data class LinterConfig(
    var identifier_format: String = "",
    @SerializedName("mandatory-variable-or-literal-in-println")
    var restrictPrintln: Boolean = false,
    @SerializedName("mandatory-variable-or-literal-in-readInput")
    var restrictReadInput: Boolean = false,
)