package pl.burningice.bi.transform.engine.jmagick

import org.apache.commons.lang.Validate
import pl.burningice.bi.transform.engine.api.*
import pl.burningice.bi.transform.file.ImageFile

import java.awt.image.BufferedImage

class JMagickEngine implements Engine {

    static {
        System.setProperty('jmagick.systemclassloader', 'false');
    }

    BufferedImage crop(final ImageFile loadedImage, final CropAction.CropParams params) {
        Validate.notNull(loadedImage)
        Validate.notNull(params)
        return new JMagickCropAction().crop(loadedImage, params)
    }

    BufferedImage scaleAccurate(ImageFile loadedImage, ScaleAccurateAction.ScaleAccurateParams params) {
        Validate.notNull(loadedImage)
        Validate.notNull(params)
        return new JMagickScaleAccurateAction().scaleAccurate(loadedImage, params)
    }

    BufferedImage scaleApproximate(final ImageFile loadedImage, final ScaleApproximateAction.ScaleApproximateParams params) {
        Validate.notNull(loadedImage)
        Validate.notNull(params)
        return new JMagickScaleApproximateAction().scaleApproximate(loadedImage, params)
    }

    BufferedImage text(final ImageFile loadedImage, final TextAction.TextParams params) {
        Validate.notNull(loadedImage)
        Validate.notNull(params)
        return new JMagickTextAction().text(loadedImage, params)
    }

    BufferedImage watermark(final ImageFile loadedImage, final WatermarkAction.WatermarkParams params) {
        Validate.notNull(loadedImage)
        Validate.notNull(params)
        return new JMagickWatermarkAction().watermark(loadedImage, params)
    }
}
