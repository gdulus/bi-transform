package pl.burningice.bi.transform

import org.apache.commons.lang.Validate
import pl.burningice.bi.transform.engine.api.*
import pl.burningice.bi.transform.file.ImageFile
import pl.burningice.bi.transform.file.ImageFileFactory

import java.awt.image.BufferedImage

class TransformChain {

    private final TransformResult result

    private final Engine engine

    private final ImageFile originalImage

    TransformChain(TransformResult result, Engine engine, ImageFile originalImage) {
        this.result = result
        this.engine = engine
        this.originalImage = originalImage
    }

    public TransformChain perform(final String identifier, final Closure transform) {
        // lets make a copy of a original file
        ImageFile image = ImageFileFactory.produce(originalImage)
        // lets specify an interface of transformation which should be equal to an interface of an Engine
        // override first param with null, we want to use an image from a local context
        transform.delegate = new Engine() {
            BufferedImage crop(ImageFile loadedImage = null, CropAction.CropParams params) {
                Validate.isTrue(loadedImage == null)
                image = ImageFileFactory.produce(engine.crop(image, params))
                return null
            }

            BufferedImage scaleAccurate(ImageFile loadedImage = null, ScaleAccurateAction.ScaleAccurateParams params) {
                Validate.isTrue(loadedImage == null)
                image = ImageFileFactory.produce(engine.scaleAccurate(image, params))
                return null
            }

            BufferedImage scaleApproximate(ImageFile loadedImage = null, ScaleApproximateAction.ScaleApproximateParams params) {
                Validate.isTrue(loadedImage == null)
                image = ImageFileFactory.produce(engine.scaleApproximate(image, params))
                return null
            }

            BufferedImage text(ImageFile loadedImage = null, TextAction.TextParams params) {
                Validate.isTrue(loadedImage == null)
                image = ImageFileFactory.produce(engine.text(image, params))
                return null
            }

            BufferedImage watermark(ImageFile loadedImage = null, WatermarkAction.WatermarkParams params) {
                Validate.isTrue(loadedImage == null)
                image = ImageFileFactory.produce(engine.watermark(image, params))
                return null
            }
        }
        // perform transformation
        transform.call()
        // store result
        result.add(identifier, image)
        // propagate chain
        return this
    }

    public TransformResult done() {
        return result
    }

}
