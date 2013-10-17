/*
Copyright (c) 2009 Pawel Gdula <pawel.gdula@burningice.pl>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package pl.burningice.bi.transform.engine.jmagick

import magick.DrawInfo
import magick.ImageInfo
import magick.MagickImage
import magick.PixelPacket
import pl.burningice.bi.transform.engine.api.TextAction
import pl.burningice.bi.transform.file.ImageFile
import pl.burningice.bi.transform.utils.ConfigUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

final class JMagickTextAction implements TextAction {

    private static final int JMAGICK_COLOR_MAX_VALUE = 0xffff

    private static final int CLIENT_COLOR_MAX_VALUE = 255

    BufferedImage text(final ImageFile loadedImage, final TextAction.TextParams params) {
        ImageInfo imageSource = new ImageInfo()
        imageSource.setQuality(ConfigUtils.imageMagickQuality)
        imageSource.setCompression(ConfigUtils.imageMagickCompression)
        MagickImage magickImage = new MagickImage(imageSource, loadedImage.byteArray)

        DrawInfo aInfo = new DrawInfo(imageSource);

        if (params.color) {
            aInfo.setFill(new PixelPacket(
                recalculateColorRange(params.color.red),
                recalculateColorRange(params.color.green),
                recalculateColorRange(params.color.blue),
                0
            ))
        }

        if (params.font) {
            aInfo.setPointsize(params.font.size)
            aInfo.setFont(params.font.name)
        }

        aInfo.setGeometry("+${params.deltaX}+${params.deltaY}")
        aInfo.setText(params.text)
        aInfo.setTextAntialias(true)
        magickImage.annotateImage(aInfo)
        return ImageIO.read(new ByteArrayInputStream(magickImage.imageToBlob(imageSource)))
    }

    private int recalculateColorRange(final int currentRange) {
        return (currentRange / CLIENT_COLOR_MAX_VALUE) * JMAGICK_COLOR_MAX_VALUE
    }
}