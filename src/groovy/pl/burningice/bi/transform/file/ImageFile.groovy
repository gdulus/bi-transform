package pl.burningice.bi.transform.file

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage

final class ImageFile {

    final byte[] byteArray

    ImageFile(byte[] byteArray) {
        this.byteArray = byteArray
    }

    @Lazy
    public InputStream inputStream = {
        return new ByteArrayInputStream(byteArray)
    }()

    @Lazy
    public BufferedImage bufferedImage = {
        return ImageIO.read(inputStream)
    }()

    @Lazy
    public Dimension dimension = {
        return new Dimension(bufferedImage.width, bufferedImage.height)
    }()

}
