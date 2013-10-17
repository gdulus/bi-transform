package pl.burningice.bi.transform.file

class FileImageFile extends ImageFile {

    FileImageFile(File source) {
        super(source.bytes)
    }

}
