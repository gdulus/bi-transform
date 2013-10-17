package pl.burningice.bi.transform.file

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class BufferedImageImageFile extends ImageFile {

    BufferedImageImageFile(BufferedImage source) {
        super(toByteArray(source))
    }

    private static byte[] toByteArray(BufferedImage source) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(source, "png", baos);
        return baos.toByteArray()
    }
}
