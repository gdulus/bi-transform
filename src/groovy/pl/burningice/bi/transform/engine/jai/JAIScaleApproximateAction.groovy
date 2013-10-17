/*
Copyright (c) 2010 Pawel Gdula <pawel.gdula@burningice.pl>

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

package pl.burningice.bi.transform.engine.jai

import com.sun.media.jai.codec.ByteArraySeekableStream
import com.sun.media.jai.codec.SeekableStream
import pl.burningice.bi.transform.engine.api.ScaleApproximateAction
import pl.burningice.bi.transform.engine.api.ScaleApproximateAction.ScaleApproximateParams
import pl.burningice.bi.transform.file.ImageFile

import javax.media.jai.InterpolationNearest
import javax.media.jai.JAI
import javax.media.jai.RenderedOp
import java.awt.image.BufferedImage
import java.awt.image.renderable.ParameterBlock

public class JAIScaleApproximateAction implements ScaleApproximateAction {

    BufferedImage scaleApproximate(final ImageFile loadedImage, final ScaleApproximateParams params) {
        SeekableStream stream = new ByteArraySeekableStream(loadedImage.byteArray)
        RenderedOp image = JAI.create('stream', stream)

        if (params.width > image.width && params.height > image.height) {
            return image.getAsBufferedImage()
        }

        double scaleX = params.width / image.width
        double scaleY = params.height / image.height
        double targetScale = scaleX > scaleY ? scaleY : scaleX
        return doScale(image, targetScale).getAsBufferedImage()
    }

    private RenderedOp doScale(final RenderedOp image, final double targetScale) {
        doBySubsampleAverage(image, targetScale)
    }

    private RenderedOp doByScale(final RenderedOp image, final double scale) {
        ParameterBlock scaleParams = new ParameterBlock();
        scaleParams.addSource(image);
        scaleParams.add((float) scale);
        scaleParams.add((float) scale);
        scaleParams.add(0.0f);
        scaleParams.add(0.0f);
        scaleParams.add(new InterpolationNearest());
        JAI.create('scale', scaleParams, null);
    }

    private RenderedOp doBySubsampleAverage(final RenderedOp image, final double scale) {
        ParameterBlock scaleParams = new ParameterBlock();
        scaleParams.addSource(image)
        scaleParams.add(scale)
        scaleParams.add(scale)
        scaleParams.add(new InterpolationNearest())
        JAI.create('SubsampleAverage', scaleParams, null)
    }
}