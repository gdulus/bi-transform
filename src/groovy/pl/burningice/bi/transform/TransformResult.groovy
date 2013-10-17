package pl.burningice.bi.transform

import pl.burningice.bi.transform.file.ImageFile

class TransformResult {

    private final Map<String, ImageFile> result = [:]

    public void add(final String identifier, final ImageFile imageFile) {
        if (result.containsKey(identifier)) {
            throw new IllegalArgumentException("Result with specified identifier exists ${identifier}")
        }

        result[identifier] = imageFile
    }

    public ImageFile get(final String identifier) {
        result[identifier]
    }

    public Map<String, ImageFile> getAll() {
        result
    }

    public int size() {
        result.size()
    }
}
