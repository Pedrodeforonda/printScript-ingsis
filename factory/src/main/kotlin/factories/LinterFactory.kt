package factories

import dataObjects.LinterResult
import main.ConfigParser
import main.Linter
import utils.PercentageCollector
import java.io.InputStream

class LinterFactory {

    fun lintCode(src: InputStream, version: String, config: InputStream, collector: PercentageCollector):
        Sequence<LinterResult> {
        val linterConfig = ConfigParser.parseConfig(config)
        val nodes = ValidatorFactory().validate(src, version, collector)
        return Linter().lint(nodes, linterConfig)
    }
}
