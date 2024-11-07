package factories

import dataObjects.LinterResult
import main.ConfigParser
import main.Linter
import main.LinterConfig
import utils.PercentageCollector
import java.io.InputStream

class LinterFactory {

    fun lintCode(src: InputStream, version: String, config: InputStream, collector: PercentageCollector):
        Sequence<LinterResult> {
        val linterConfig = ConfigParser.parseConfigToMap(config)
        val nodes = ValidatorFactory().validate(src, version, collector)
        return Linter().lint(nodes, linterConfig)
    }

    fun lintCode(src: InputStream, version: String, config: LinterConfig): Sequence<LinterResult> {
        val collector = PercentageCollector()
        val nodes = ValidatorFactory().validate(src, version, collector)
        return Linter().lint(nodes, config)
    }
}
