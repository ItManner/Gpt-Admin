package com.ruoyi.common.qrcode.renderer;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ruoyi.common.qrcode.LineDirection;
import com.ruoyi.common.qrcode.Parameters;
import com.ruoyi.common.qrcode.Shape;

import java.awt.*;


/**
 * ClassName: RendererLine (A-a1)
 *
 * @author guoxinlu
 * @since 2022-07-10 15:53
 */
public class RendererLine extends Renderer{

    public RendererLine() {
        style = RendererStyle.LINE;

        parameters = new Parameters();
        parameters.setErrorCorrectionLevel(ErrorCorrectionLevel.M);
        parameters.setAnchorPointShape(Shape.ROUNDED_RECTANGLE);
        parameters.setAnchorPointColor(Color.BLACK);
        parameters.setDataPointShape(Shape.LINE);
        parameters.setLineDirection(LineDirection.VERTICAL_HORIZONTAL);
        parameters.setLineStroke(50);
        parameters.setLineOpacity(100);
        parameters.setLineColor(Color.BLACK);
        parameters.setDataPointScale(50);
    }

    @Override
    public RendererLineAdjuster adjust() {
        return new RendererLineAdjuster(this);
    }

}
