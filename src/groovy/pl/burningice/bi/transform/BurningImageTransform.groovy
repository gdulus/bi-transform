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
package pl.burningice.bi.transform

import org.springframework.web.multipart.MultipartFile
import pl.burningice.bi.transform.engine.EnginesRepository
import pl.burningice.bi.transform.engine.api.Engine
import pl.burningice.bi.transform.file.ImageFileFactory
import pl.burningice.bi.transform.utils.ConfigUtils

class BurningImageTransform {

    EnginesRepository enginesRepository

    public TransformChain on(final String filePath) {
        if (!filePath) {
            throw new IllegalArgumentException('Path to source file must be provided')
        }

        on(new File(filePath))
    }

    public TransformChain on(final File file) {
        if (!file) {
            throw new IllegalArgumentException('Source file must be provided')
        }

        if (!file.exists()) {
            throw new FileNotFoundException("Can't find file in path ${file.absolutePath}")
        }

        Engine engine = enginesRepository.get(ConfigUtils.engine)
        return new TransformChain(new TransformResult(), engine, ImageFileFactory.produce(file))
    }

    public TransformChain on(final MultipartFile file) {
        if (file == null) {
            throw new IllegalArgumentException('Uploaded file must be provided')
        }

        if (file.isEmpty()) {
            throw new IllegalArgumentException('Uploaded image is empty')
        }

        Engine engine = enginesRepository.get(ConfigUtils.engine)
        return new TransformChain(new TransformResult(), engine, ImageFileFactory.produce(file))
    }
}
