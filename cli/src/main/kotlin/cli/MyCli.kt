package cli

import com.github.ajalt.clikt.core.CliktCommand

class MyCli : CliktCommand(
    name = "MyCLI",
    help = "CLI tool for executing, validating, formatting and analyzing source files.",
) {
    override fun run() = Unit
}
