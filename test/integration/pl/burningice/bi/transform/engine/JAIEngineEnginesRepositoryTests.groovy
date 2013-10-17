package pl.burningice.bi.transform.engine

import grails.test.mixin.TestMixin
import org.junit.Test
import pl.burningice.bi.transform.engine.api.*
import pl.burningice.bi.transform.engine.jai.JAIEngine
import pl.burningice.bi.transform.test.IntegrationTestFileUploadUtils

import javax.imageio.ImageIO

@TestMixin([IntegrationTestFileUploadUtils])
class JAIEngineEnginesRepositoryTests extends GroovyTestCase {

    private static final List<String> TEST_IMAGES = [
        'height_bigger.jpg',
        'image.bmp',
        'image.gif',
        'image.jpg',
        'image.png',
        'image2.bmp',
        'image2.jpg'
    ]

    EnginesRepository enginesRepository

    Engine engine

    @Override
    void setUp() {
        engine = enginesRepository.get(JAIEngine)
        assertNotNull(engine)
    }

    @Test(expected = IllegalArgumentException)
    public void crop_nullImage_shouldThrownAnException() {
        engine.crop(null, new CropAction.CropParams(deltaX: 0, deltaY: 0, width: 100, height: 100))
    }

    @Test(expected = IllegalArgumentException)
    public void crop_nullParams_shouldThrownAnException() {
        engine.crop(getSourceImageFile(TEST_IMAGES.first()), null)
    }

    @Test
    public void crop_paramsProvided_shouldExecuteAction() {
        TEST_IMAGES.each {
            def result = engine.crop(getSourceImageFile(it), new CropAction.CropParams(deltaX: 0, deltaY: 0, width: 100, height: 200))
            assertNotNull(result)
            assertEquals(100, result.width)
            assertEquals(200, result.height)
        }
    }

    @Test(expected = IllegalArgumentException)
    public void scaleAccurate_nullImage_shouldThrownAnException() {
        engine.scaleAccurate(null, new ScaleAccurateAction.ScaleAccurateParams(width: 100, height: 200))
    }

    @Test(expected = IllegalArgumentException)
    public void scaleAccurate_nullParams_shouldThrownAnException() {
        engine.scaleAccurate(getSourceImageFile(TEST_IMAGES.first()), null)
    }

    @Test
    public void scaleAccurateJPG_paramsProvided_shouldExecuteAction() {
        def result = engine.scaleAccurate(getSourceImageFile('height_bigger.jpg'), new ScaleAccurateAction.ScaleAccurateParams(width: 100, height: 100))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(100, result.height)
        ImageIO.write(result, 'jpg', new File("${resultDir}jai_scaleAccurateJPG_height_bigger.jpg"))

        result = engine.scaleAccurate(getSourceImageFile('image.jpg'), new ScaleAccurateAction.ScaleAccurateParams(width: 100, height: 100))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(100, result.height)
        ImageIO.write(result, 'jpg', new File("${resultDir}jai_scaleAccurateJPG_image.jpg"))

        result = engine.scaleAccurate(getSourceImageFile('image2.jpg'), new ScaleAccurateAction.ScaleAccurateParams(width: 100, height: 100))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(100, result.height)
        ImageIO.write(result, 'jpg', new File("${resultDir}jai_scaleAccurateJPG_image2.jpg"))
    }

    @Test
    public void scaleAccurateBMP_paramsProvided_shouldExecuteAction() {
        def result = engine.scaleAccurate(getSourceImageFile('image2.bmp'), new ScaleAccurateAction.ScaleAccurateParams(width: 100, height: 100))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(100, result.height)
        ImageIO.write(result, 'bmp', new File("${resultDir}jai_scaleAccurateBMP_image2.bmp"))

        result = engine.scaleAccurate(getSourceImageFile('image.bmp'), new ScaleAccurateAction.ScaleAccurateParams(width: 100, height: 100))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(100, result.height)
        ImageIO.write(result, 'bmp', new File("${resultDir}jai_scaleAccurateBMP_image.bmp"))
    }

    @Test
    public void scaleAccurateGIF_paramsProvided_shouldExecuteAction() {
        def result = engine.scaleAccurate(getSourceImageFile('image.gif'), new ScaleAccurateAction.ScaleAccurateParams(width: 100, height: 100))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(100, result.height)
        ImageIO.write(result, 'gif', new File("${resultDir}jai_scaleAccurateGIF_image.gif"))
    }

    @Test
    public void scaleAccuratePNG_paramsProvided_shouldExecuteAction() {
        def result = engine.scaleAccurate(getSourceImageFile('image.png'), new ScaleAccurateAction.ScaleAccurateParams(width: 100, height: 100))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(100, result.height)
        ImageIO.write(result, 'png', new File("${resultDir}jai_scaleAccuratePNG_image.png"))
    }

    @Test(expected = IllegalArgumentException)
    public void scaleApproximate_nullImage_shouldThrownAnException() {
        engine.scaleApproximate(null, new ScaleApproximateAction.ScaleApproximateParams(width: 100, height: 200))
    }

    @Test(expected = IllegalArgumentException)
    public void scaleApproximate_nullParams_shouldThrownAnException() {
        engine.scaleApproximate(getSourceImageFile(TEST_IMAGES.first()), null)
    }

    @Test
    public void scaleApproximateJPG_paramsProvided_shouldExecuteAction() {
        def result = engine.scaleApproximate(getSourceImageFile('height_bigger.jpg'), new ScaleApproximateAction.ScaleApproximateParams(width: 100, height: 200))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(150, result.height)
        ImageIO.write(result, 'jpg', new File("${resultDir}jai_scaleApproximateJPG_height_bigger.jpg"))

        result = engine.scaleApproximate(getSourceImageFile('image.jpg'), new ScaleApproximateAction.ScaleApproximateParams(width: 100, height: 200))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(62, result.height)
        ImageIO.write(result, 'jpg', new File("${resultDir}jai_scaleApproximateJPG_image.jpg"))

        result = engine.scaleApproximate(getSourceImageFile('image2.jpg'), new ScaleApproximateAction.ScaleApproximateParams(width: 100, height: 200))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(62, result.height)
        ImageIO.write(result, 'jpg', new File("${resultDir}jai_scaleApproximateJPG_image2.jpg"))
    }

    @Test
    public void scaleApproximateBMP_paramsProvided_shouldExecuteAction() {
        def result = engine.scaleApproximate(getSourceImageFile('image.bmp'), new ScaleApproximateAction.ScaleApproximateParams(width: 100, height: 200))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(89, result.height)
        ImageIO.write(result, 'bmp', new File("${resultDir}jai_scaleApproximateBMP_image.bmp"))
    }

    @Test
    public void scaleApproximateGIF_paramsProvided_shouldExecuteAction() {
        def result = engine.scaleApproximate(getSourceImageFile('image.gif'), new ScaleApproximateAction.ScaleApproximateParams(width: 100, height: 200))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(74, result.height)
        ImageIO.write(result, 'gif', new File("${resultDir}jai_scaleApproximateGIF_image.gif"))
    }

    @Test
    public void scaleApproximatePNG_paramsProvided_shouldExecuteAction() {
        def result = engine.scaleApproximate(getSourceImageFile('image.png'), new ScaleApproximateAction.ScaleApproximateParams(width: 100, height: 200))
        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(62, result.height)
        ImageIO.write(result, 'png', new File("${resultDir}jai_scaleApproximatePNG_image.png"))
    }

    @Test(expected = IllegalArgumentException)
    public void text_nullImage_shouldThrownAnException() {
        engine.text(null, new TextAction.TextParams(text: 'test', deltaX: 10, deltaY: 10))
    }

    @Test(expected = IllegalArgumentException)
    public void text_nullParams_shouldThrownAnException() {
        engine.text(getSourceImageFile(TEST_IMAGES.first()), null)
    }

    @Test
    public void textJPG_paramsProvided_shouldExecuteAction() {
        def result = engine.text(getSourceImageFile('height_bigger.jpg'), new TextAction.TextParams(text: 'test', deltaX: 10, deltaY: 10))
        assertNotNull(result)
        ImageIO.write(result, 'jpg', new File("${resultDir}jai_textJPG_height_bigger.jpg"))

        result = engine.text(getSourceImageFile('image.jpg'), new TextAction.TextParams(text: 'test', deltaX: 10, deltaY: 10))
        assertNotNull(result)
        ImageIO.write(result, 'jpg', new File("${resultDir}jai_textJPG_image.jpg"))

        result = engine.text(getSourceImageFile('image2.jpg'), new TextAction.TextParams(text: 'test', deltaX: 10, deltaY: 10))
        assertNotNull(result)
        ImageIO.write(result, 'jpg', new File("${resultDir}jai_textJPG_image2.jpg"))
    }

    @Test
    public void textBMP_paramsProvided_shouldExecuteAction() {
        def result = engine.text(getSourceImageFile('image.bmp'), new TextAction.TextParams(text: 'test', deltaX: 10, deltaY: 10))
        assertNotNull(result)
        ImageIO.write(result, 'bmp', new File("${resultDir}jai_textBMP_image.bmp"))
    }

    @Test
    public void textGIF_paramsProvided_shouldExecuteAction() {
        def result = engine.text(getSourceImageFile('image.gif'), new TextAction.TextParams(text: 'test', deltaX: 10, deltaY: 10))
        assertNotNull(result)
        ImageIO.write(result, 'gif', new File("${resultDir}jai_textGIF_image.gif"))
    }

    @Test
    public void textPNG_paramsProvided_shouldExecuteAction() {
        def result = engine.text(getSourceImageFile('image.png'), new TextAction.TextParams(text: 'test', deltaX: 10, deltaY: 10))
        assertNotNull(result)
        ImageIO.write(result, 'png', new File("${resultDir}jai_textPNG_image.png"))
    }

    @Test(expected = IllegalArgumentException)
    public void watermark_nullImage_shouldThrownAnException() {
        engine.watermark(null, new WatermarkAction.WatermarkParams(watermark: getSourceImageFile('watermark.png'), deltaX: 10, deltaY: 10))
    }

    @Test(expected = IllegalArgumentException)
    public void watermark_nullParams_shouldThrownAnException() {
        engine.watermark(getSourceImageFile(TEST_IMAGES.first()), null)
    }

    @Test
    public void watermarkJPG_paramsProvided_shouldExecuteAction() {
        def result = engine.watermark(getSourceImageFile('height_bigger.jpg'), new WatermarkAction.WatermarkParams(watermark: getSourceImageFile('watermark.png'), deltaX: 10, deltaY: 10))
        assertNotNull(result)
        ImageIO.write(result, 'jpg', new File("${resultDir}jai_watermarkJPG_height_bigger.jpg"))

        result = engine.watermark(getSourceImageFile('image.jpg'), new WatermarkAction.WatermarkParams(watermark: getSourceImageFile('watermark.png'), deltaX: 10, deltaY: 10))
        assertNotNull(result)
        ImageIO.write(result, 'jpg', new File("${resultDir}jai_watermarkJPG_image.jpg"))

        result = engine.watermark(getSourceImageFile('image2.jpg'), new WatermarkAction.WatermarkParams(watermark: getSourceImageFile('watermark.png'), deltaX: 10, deltaY: 10))
        assertNotNull(result)
        ImageIO.write(result, 'jpg', new File("${resultDir}jai_watermarkJPG_image2.jpg"))
    }

    @Test
    public void watermarkBMP_paramsProvided_shouldExecuteAction() {
        def result = engine.watermark(getSourceImageFile('image.bmp'), new WatermarkAction.WatermarkParams(watermark: getSourceImageFile('watermark.png'), deltaX: 10, deltaY: 10))
        assertNotNull(result)
        ImageIO.write(result, 'bmp', new File("${resultDir}jai_watermarkBMP_image.bmp"))
    }

    @Test
    public void watermarkGIF_paramsProvided_shouldExecuteAction() {
        def result = engine.watermark(getSourceImageFile('image.gif'), new WatermarkAction.WatermarkParams(watermark: getSourceImageFile('watermark.png'), deltaX: 10, deltaY: 10))
        assertNotNull(result)
        ImageIO.write(result, 'gif', new File("${resultDir}jai_watermarkGIF_image.gif"))
    }

    @Test
    public void watermarkPNG_paramsProvided_shouldExecuteAction() {
        def result = engine.watermark(getSourceImageFile('image.png'), new WatermarkAction.WatermarkParams(watermark: getSourceImageFile('watermark.png'), deltaX: 10, deltaY: 10))
        assertNotNull(result)
        ImageIO.write(result, 'png', new File("${resultDir}jai_watermarkPNG_image.png"))
    }

}

