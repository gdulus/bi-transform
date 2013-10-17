package pl.burningice.bi.transform

import grails.test.mixin.TestMixin
import grails.util.Holders
import org.junit.Test
import org.springframework.web.multipart.MultipartFile
import pl.burningice.bi.transform.engine.api.*
import pl.burningice.bi.transform.engine.jai.JAIEngine
import pl.burningice.bi.transform.file.ImageFile
import pl.burningice.bi.transform.test.IntegrationTestFileUploadUtils

@TestMixin([IntegrationTestFileUploadUtils])
class BurningImageTransformTests extends GroovyTestCase {

    BurningImageTransform burningImageTransform

    @Override
    void setUp() {
        Holders.config.bi.renderingEngine = JAIEngine
    }

    @Test(expected = IllegalArgumentException)
    public void withLocalFilePath_nullParam_shouldThrownAnException() {
        burningImageTransform.on((String) null)
    }

    @Test(expected = FileNotFoundException)
    public void withLocalFilePath_wrongPath_shouldThrownAnException() {
        burningImageTransform.on('NotExistingFile')
    }

    @Test
    public void withLocalFilePath_someBasicTransform_shouldReturnResult() {
        def result = burningImageTransform.on(getFilePath('image.png'))
            .perform('image1') {
            crop(new CropAction.CropParams(deltaX: 100, deltaY: 100, width: 100, height: 200))
            text(new TextAction.TextParams(text: '1234567890', deltaX: 10, deltaY: 10))
        }
        .perform('image2') {
            scaleAccurate(new ScaleAccurateAction.ScaleAccurateParams(width: 100, height: 100))
            watermark(new WatermarkAction.WatermarkParams(watermark: getSourceImageFile('watermark.png'), deltaX: 10, deltaY: 10))
        }
        .done()

        assertNotNull(result)
        assertEquals(2, result.size())

        ImageFile image1 = result.get('image1')
        assertEquals(100, image1.dimension.width)
        assertEquals(200, image1.dimension.height)
        writeImage(image1.bufferedImage, 'withLocalFilePath_someBasicTransform_image1.png')

        ImageFile image2 = result.get('image2')
        assertEquals(100, image2.dimension.width)
        assertEquals(100, image2.dimension.height)
        writeImage(image2.bufferedImage, 'withLocalFilePath_someBasicTransform_image2.png')
    }

    @Test(expected = IllegalArgumentException)
    public void withLocalFile_nullParam_shouldThrownAnException() {
        burningImageTransform.on((File) null)
    }

    @Test(expected = FileNotFoundException)
    public void withLocalFile_wrongPath_shouldThrownAnException() {
        burningImageTransform.on(new File('NotExistingFile'))
    }

    @Test
    public void withLocalFile_someBasicTransform_shouldReturnResult() {
        def result = burningImageTransform.on(getFilePath('image.bmp'))
            .perform('image1') {
            crop(new CropAction.CropParams(deltaX: 100, deltaY: 100, width: 100, height: 200))
            text(new TextAction.TextParams(text: '1234567890', deltaX: 10, deltaY: 10))
        }
        .perform('image2') {
            scaleAccurate(new ScaleAccurateAction.ScaleAccurateParams(width: 100, height: 100))
            watermark(new WatermarkAction.WatermarkParams(watermark: getSourceImageFile('watermark.png'), deltaX: 10, deltaY: 10))
        }
        .done()

        assertNotNull(result)
        assertEquals(2, result.size())

        ImageFile image1 = result.get('image1')
        assertEquals(100, image1.dimension.width)
        assertEquals(200, image1.dimension.height)
        writeImage(image1.bufferedImage, 'withLocalFile_someBasicTransform_image1.bmp')

        ImageFile image2 = result.get('image2')
        assertEquals(100, image2.dimension.width)
        assertEquals(100, image2.dimension.height)
        writeImage(image2.bufferedImage, 'withLocalFile_someBasicTransform_image2.bmp')
    }

    @Test(expected = IllegalArgumentException)
    public void withMultipartFile_nullParam_shouldThrownAnException() {
        burningImageTransform.on((MultipartFile) null)
    }

    @Test(expected = IllegalArgumentException)
    public void withMultipartFile_wrongPath_shouldThrownAnException() {
        burningImageTransform.on(emptyMultipartFile)
    }

    @Test
    public void withMultipartFile_someBasicTransform_shouldReturnResult() {
        def result = burningImageTransform.on(getMultipartFile('image.jpg'))
            .perform('image1') {
            crop(new CropAction.CropParams(deltaX: 100, deltaY: 100, width: 100, height: 200))
            text(new TextAction.TextParams(text: '1234567890', deltaX: 10, deltaY: 10))
        }
        .perform('image2') {
            scaleAccurate(new ScaleAccurateAction.ScaleAccurateParams(width: 100, height: 100))
            watermark(new WatermarkAction.WatermarkParams(watermark: getSourceImageFile('watermark.png'), deltaX: 10, deltaY: 10))
        }
        .done()

        assertNotNull(result)
        assertEquals(2, result.size())

        ImageFile image1 = result.get('image1')
        assertEquals(100, image1.dimension.width)
        assertEquals(200, image1.dimension.height)
        writeImage(image1.bufferedImage, 'withMultipartFile_someBasicTransform_image1.jpg')

        ImageFile image2 = result.get('image2')
        assertEquals(100, image2.dimension.width)
        assertEquals(100, image2.dimension.height)
        writeImage(image2.bufferedImage, 'withMultipartFile_someBasicTransform_image2.jpg')
    }

    @Test
    public void withMultipartFile_testEachAction_shouldReturnResult() {
        def result = burningImageTransform.on(getMultipartFile('image2.jpg'))
            .perform('crop') {
            crop(new CropAction.CropParams(deltaX: 100, deltaY: 100, width: 100, height: 200))
        }
        .perform('text') {
            text(new TextAction.TextParams(text: '1234567890', deltaX: 10, deltaY: 10))
        }
        .perform('scaleAccurate') {
            scaleAccurate(new ScaleAccurateAction.ScaleAccurateParams(width: 100, height: 100))
        }
        .perform('scaleApproximate') {
            scaleApproximate(new ScaleApproximateAction.ScaleApproximateParams(width: 100, height: 100))
        }
        .perform('watermark') {
            watermark(new WatermarkAction.WatermarkParams(watermark: getSourceImageFile('watermark.png'), deltaX: 10, deltaY: 10))
        }
        .done()

        assertNotNull(result)
        assertEquals(5, result.size())

        ImageFile crop = result.get('crop')
        assertEquals(100, crop.dimension.width)
        assertEquals(200, crop.dimension.height)
        writeImage(crop.bufferedImage, 'withMultipartFile_testEachAction_crop.jpg')

        ImageFile text = result.get('text')
        writeImage(text.bufferedImage, 'withMultipartFile_testEachAction_text.jpg')

        ImageFile scaleAccurate = result.get('scaleAccurate')
        assertEquals(100, scaleAccurate.dimension.width)
        assertEquals(100, scaleAccurate.dimension.height)
        writeImage(scaleAccurate.bufferedImage, 'withMultipartFile_testEachAction_scaleAccurate.jpg')

        ImageFile scaleApproximate = result.get('scaleApproximate')
        assertEquals(100, scaleApproximate.dimension.width)
        assertEquals(62, scaleApproximate.dimension.height)
        writeImage(scaleApproximate.bufferedImage, 'withMultipartFile_testEachAction_scaleApproximate.jpg')

        ImageFile watermark = result.get('watermark')
        writeImage(watermark.bufferedImage, 'withMultipartFile_testEachAction_watermark.jpg')
    }

}
