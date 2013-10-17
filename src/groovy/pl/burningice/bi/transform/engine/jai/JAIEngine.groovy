package pl.burningice.bi.transform.engine.jai

import org.apache.commons.lang.Validate
import pl.burningice.bi.transform.engine.api.*
import pl.burningice.bi.transform.file.ImageFile

import java.awt.image.BufferedImage

class JAIEngine implements Engine {

    static {
        System.setProperty("com.sun.media.jai.disableMediaLib", "true");
    }

    BufferedImage crop(final ImageFile loadedImage, final CropAction.CropParams params) {
        Validate.notNull(loadedImage)
        Validate.notNull(params)
        return new JAICropAction().crop(loadedImage, params)
    }

    BufferedImage scaleAccurate(final ImageFile loadedImage, final ScaleAccurateAction.ScaleAccurateParams params) {
        Validate.notNull(loadedImage)
        Validate.notNull(params)
        return new JAIScaleAccurateAction().scaleAccurate(loadedImage, params)
    }

    BufferedImage scaleApproximate(final ImageFile loadedImage, final ScaleApproximateAction.ScaleApproximateParams params) {
        Validate.notNull(loadedImage)
        Validate.notNull(params)
        return new JAIScaleApproximateAction().scaleApproximate(loadedImage, params)
    }

    BufferedImage text(final ImageFile loadedImage, final TextAction.TextParams params) {
        Validate.notNull(loadedImage)
        Validate.notNull(params)
        return new JAITextEngine().text(loadedImage, params)
    }

    BufferedImage watermark(final ImageFile loadedImage, final WatermarkAction.WatermarkParams params) {
        Validate.notNull(loadedImage)
        Validate.notNull(params)
        return new JAIWatermarkAction().watermark(loadedImage, params)
    }
}
