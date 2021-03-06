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
package pl.burningice.bi.transform.test

import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import pl.burningice.bi.transform.file.ImageFile
import pl.burningice.bi.transform.file.ImageFileFactory

import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.ImageWriter
import javax.imageio.stream.FileImageOutputStream
import javax.imageio.stream.ImageOutputStream
import java.awt.image.BufferedImage

/**
 * Class provide additional methods for testing file upload
 *
 * @author pawel.gdula@burningice.pl
 */
abstract class FileUploadUtils {

    private static final Map<String, String> TYPES = ['jpg': 'image/jpeg', 'png': 'image/png', 'gif': 'image/gif', 'bmp': 'image/bmp']

    public abstract String getResultDir()

    public abstract getSourceDir()

    public void cleanUpTestDir() {
        new File(resultDir).list().toList().each {
            def filePath = "${resultDir}${it}"
            println "Remove ${filePath}"
            new File(filePath).delete()
        }
    }

    public boolean fileExists(final String fileName) {
        new File("${resultDir}${fileName}").exists()
    }

    public String getFilePath(final String fileName) {
        "${sourceDir}${fileName}"
    }

    public BufferedImage getBufferedImage(final String fileName, final String dir = null) {
        ImageIO.read(new File("${dir ?: resultDir}${fileName}"))
    }

    public ImageFile getSourceImageFile(final String fileName) {
        ImageFileFactory.produce(new File("${sourceDir}${fileName}"))
    }

    public MultipartFile getEmptyMultipartFile() {
        new MockMultipartFile('empty', new byte[0])
    }

    public MultipartFile getMultipartFile(final String fileName) {
        def fileNameParts = fileName.split(/\./)
        new MockMultipartFile(fileNameParts[0], fileName, TYPES[fileNameParts[1]], new FileInputStream(getFilePath(fileName)))
    }

    public void writeImage(final BufferedImage bufferedImage, final String imageName) {
        ImageOutputStream output = new FileImageOutputStream(new File("${resultDir}${imageName}"))
        IIOImage image = new IIOImage(bufferedImage, null, null);
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next()
        ImageWriteParam param = writer.getDefaultWriteParam()
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(1.0F);
        writer.setOutput(output)
        writer.write(null, image, param);
        writer.dispose()
        output.flush()
    }

}
