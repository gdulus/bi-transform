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

import magick.ImageInfo
import magick.MagickImage
import pl.burningice.bi.transform.engine.api.CropAction
import pl.burningice.bi.transform.file.ImageFile
import pl.burningice.bi.transform.utils.ConfigUtils

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage

class JMagickCropAction implements CropAction {

    BufferedImage crop(final ImageFile loadedImage, final CropAction.CropParams params) {
        ImageInfo imageSource = new ImageInfo()
        imageSource.setQuality(ConfigUtils.imageMagickQuality)
        imageSource.setCompression(ConfigUtils.imageMagickCompression)
        MagickImage magickImage = new MagickImage(imageSource, loadedImage.byteArray)
        MagickImage croppedImage = magickImage.cropImage(new Rectangle(params.deltaX, params.deltaY, params.width, params.height))
        return ImageIO.read(new ByteArrayInputStream(croppedImage.imageToBlob(imageSource)))
    }

}