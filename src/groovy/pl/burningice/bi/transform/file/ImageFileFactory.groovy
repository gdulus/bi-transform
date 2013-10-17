package pl.burningice.bi.transform.file

import org.springframework.web.multipart.MultipartFile

import java.awt.image.BufferedImage

final class ImageFileFactory {

    public static ImageFile produce(final Object source) {
        switch (true) {
            case source instanceof File:
                return new FileImageFile((File) source)
            case source instanceof MultipartFile:
                return new MultipartImageFile((MultipartFile) source)
            case source instanceof BufferedImage:
                return new BufferedImageImageFile((BufferedImage) source)
        }

        throw new IllegalArgumentException("Unsupported source type : ${source.class}")
    }

}
