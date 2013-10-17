package pl.burningice.bi.transform.file

import org.springframework.web.multipart.MultipartFile

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

final class ImageFileFactory {

    public static ImageFile produce(final Object source) {
        switch (true) {
            case source instanceof File:
                return new ImageFile(asByteArray((File) source))
            case source instanceof MultipartFile:
                return new ImageFile(asByteArray((MultipartFile) source))
            case source instanceof BufferedImage:
                return new ImageFile(asByteArray((BufferedImage) source))
            case source instanceof ImageFile:
                return new ImageFile(asByteArray((ImageFile) source))
        }

        throw new IllegalArgumentException("Unsupported source type : ${source.class}")
    }

    private static byte[] asByteArray(final File source) {
        return source.bytes
    }

    private static byte[] asByteArray(final ImageFile source) {
        return source.byteArray
    }

    private static byte[] asByteArray(final MultipartFile source) {
        return source.bytes
    }

    private static byte[] asByteArray(final BufferedImage source) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(source, "png", baos);
        return baos.toByteArray()
    }

}
