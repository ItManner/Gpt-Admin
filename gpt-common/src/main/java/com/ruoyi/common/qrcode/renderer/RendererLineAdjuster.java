package com.ruoyi.common.qrcode.renderer;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ruoyi.common.qrcode.LineDirection;
import com.ruoyi.common.qrcode.Shape;

import java.awt.*;


public class RendererLineAdjuster {

    private final Renderer renderer;

    public RendererLineAdjuster(Renderer renderer) {
        if (renderer == null || renderer.getParameters() == null) {
            throw new IllegalArgumentException("Renderer or Parameters is null");
        }
        this.renderer = renderer;
    }

    public Renderer end() {
        return this.renderer;
    }

    public RendererLineAdjuster errorCorrectionLevel(ErrorCorrectionLevel level) {
        renderer.getParameters().setErrorCorrectionLevel(level);
        return this;
    }

    public RendererLineAdjuster anchorPointShape(com.ruoyi.common.qrcode.Shape anchorPointShape) {
        if (com.ruoyi.common.qrcode.Shape.RECTANGLE == anchorPointShape
                || com.ruoyi.common.qrcode.Shape.CIRCLE == anchorPointShape
                || com.ruoyi.common.qrcode.Shape.PLANET == anchorPointShape
                || Shape.ROUNDED_RECTANGLE == anchorPointShape) {
            renderer.getParameters().setAnchorPointShape(anchorPointShape);
        }
        return this;
    }

    public RendererLineAdjuster anchorPointColor(Color anchorPointColor) {
        renderer.getParameters().setAnchorPointColor(anchorPointColor);
        return this;
    }

    public RendererLineAdjuster iconScale(int iconScale) {
        if (iconScale > 100) {
            iconScale = 100;
        }
        if (iconScale < 5) {
            iconScale = 0;
        }
        renderer.getParameters().setIconScale(iconScale);
        return this;
    }

    public RendererLineAdjuster iconBase64(String iconBase64) {
        if (iconBase64 == null) {
            iconBase64 = "";
        }
        renderer.getParameters().setIconBase64(iconBase64);
        return this;
    }

    public RendererLineAdjuster backgroundColor(Color backgroundColor) {
        renderer.getParameters().setBackgroundColor(backgroundColor);
        return this;
    }

    public RendererLineAdjuster lineStroke(int lineStroke) {
        if (lineStroke > 100) {
            lineStroke = 100;
        }
        if (lineStroke < 5) {
            lineStroke = 5;
        }
        renderer.getParameters().setLineStroke(lineStroke);
        renderer.getParameters().setDataPointScale(lineStroke);
        return this;
    }

    public RendererLineAdjuster lineOpacity(int lineOpacity) {
        if (lineOpacity > 100) {
            lineOpacity = 100;
        }
        if (lineOpacity < 0) {
            lineOpacity = 0;
        }
        renderer.getParameters().setLineOpacity(lineOpacity);
        renderer.getParameters().setDataPointOpacity(lineOpacity);
        return this;
    }

    public RendererLineAdjuster lineColor(Color lineColor) {
        renderer.getParameters().setLineColor(lineColor);
        renderer.getParameters().setDataPointColor(lineColor);
        return this;
    }

    public RendererLineAdjuster lineDirection(LineDirection lineDirection) {
        renderer.getParameters().setLineDirection(lineDirection);
        return this;
    }

}
