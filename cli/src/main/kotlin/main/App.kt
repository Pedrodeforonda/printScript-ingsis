package main

import cli.AnalyzerOperation
import cli.ExecutorOperation
import cli.FormatterOperation
import cli.MyCli
import cli.ValidatorOperation
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) =
    MyCli()
        .subcommands(ExecutorOperation(), ValidatorOperation(), AnalyzerOperation(), FormatterOperation())
        .main(args)
