package pl.burningice.bi.transform.engine.api

import pl.burningice.bi.transform.engine.api.TextAction.TextParams
import pl.burningice.bi.transform.file.ImageFile

import java.awt.*
import java.awt.image.BufferedImage

public interface TextAction {

    BufferedImage text(ImageFile loadedImage, TextParams params)

    public final static class TextParams {

        String text

        Color color

        Font font

        float deltaX

        float deltaY

    }

}