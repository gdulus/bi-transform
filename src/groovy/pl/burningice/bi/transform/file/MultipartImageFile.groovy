package pl.burningice.bi.transform.file

import org.springframework.web.multipart.MultipartFile

class MultipartImageFile extends ImageFile {

    MultipartImageFile(MultipartFile source) {
        super(source.bytes)
    }

}
