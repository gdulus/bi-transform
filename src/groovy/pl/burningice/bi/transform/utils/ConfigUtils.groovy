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
package pl.burningice.bi.transform.utils

import grails.util.Holders
import pl.burningice.bi.transform.engine.api.Engine
import pl.burningice.bi.transform.engine.jai.JAIEngine

/**
 * Class that provide access to configuration specified for BI
 *
 * @author pawel.gdula@burningice.pl
 */
class ConfigUtils {

    private static final String CONFIG_NAMESPACE = 'bi'

    private static final Class<? extends Engine> DEFAULT_RENDERING_ENGINE = JAIEngine

    private static final int DEFAULT_JMAGICK_QUALITY = 0

    private static final int DEFAULT_JMAGICK_COMPRESSION = 100

    static Class<? extends Engine> getEngine() {
        return Holders.config."${CONFIG_NAMESPACE}"?.renderingEngine ?: DEFAULT_RENDERING_ENGINE
    }

    static int getImageMagickQuality() {
        return Holders.config."${CONFIG_NAMESPACE}".jmagick.quality ?: DEFAULT_JMAGICK_QUALITY
    }

    static int getImageMagickCompression() {
        return Holders.config."${CONFIG_NAMESPACE}".jmagick.compression ?: DEFAULT_JMAGICK_COMPRESSION
    }
}
