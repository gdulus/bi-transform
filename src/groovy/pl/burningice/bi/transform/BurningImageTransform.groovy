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

import org.apache.commons.lang.Validate
import org.springframework.web.multipart.MultipartFile

/**
 * Main entry for the plugin
 *
 * @author pawel.gdula@burningice.pl
 */
class BurningImageTransform {

    def execute(final String filePath) {
        Validate.notEmpty((String) filePath, 'Path to source file must be provided')
        execute(new File(filePath))
    }

    def execute(final File file) {
        Validate.notNull(file, 'Source file must be provided')

    }

    def execute(MultipartFile file) {
        Validate.notNull(file, 'Uploaded image is null')
        Validate.notEmpty(file, 'Uploaded image is empty')

    }

    private def getWorker(file, resultDir) {
        if (!(new File(resultDir).exists())) {
            throw new FileNotFoundException("There is no output ${resultDir} directory")
        }

        if (resultDir[-1] == '/') {
            resultDir = resultDir[0..-2]
        }


    }
}
