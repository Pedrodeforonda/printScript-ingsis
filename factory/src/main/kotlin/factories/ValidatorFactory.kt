package factories

import dataObjects.ParsingResult
import runners.ValidatorFactory
import utils.PercentageCollector
import java.io.InputStream

class ValidatorFactory {

    fun validate(src: InputStream, version: String, collector: PercentageCollector): Sequence<ParsingResult> {
        val validator = runners.ValidatorFactory()
        return validator.validate(src, version, collector)
    }
}
