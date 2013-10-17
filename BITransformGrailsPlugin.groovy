import pl.burningice.bi.transform.BurningImageTransform
import pl.burningice.bi.transform.engine.EnginesRepository
import pl.burningice.bi.transform.engine.jai.JAIEngine
import pl.burningice.bi.transform.engine.jmagick.JMagickEngine

class BITransformGrailsPlugin {

    def version = "2.0"

    def grailsVersion = "2.0 > *"

    def dependsOn = [:]

    def pluginExcludes = [
        "grails-app/views/error.gsp",
        "grails-app/domain/pl/burningice/plugins/image/ast/test/**",
        "resources/**",
        "web-app/**"
    ]

    def author = "Pawel Gdula"

    def authorEmail = "pawel.gdula@burningice.pl"

    def title = "BI-Transform"

    def doWithSpring = {
        enginesRepository(EnginesRepository) { bean ->
            bean.constructorArgs = [[new JAIEngine(), new JMagickEngine()]]
        }

        burningImageTransform(BurningImageTransform) {
            enginesRepository = ref('enginesRepository')
        }
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def doWithWebDescriptor = { xml ->
    }

    def doWithDynamicMethods = { ctx ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}
