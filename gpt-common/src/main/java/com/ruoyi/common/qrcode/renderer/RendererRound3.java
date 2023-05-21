package com.ruoyi.common.qrcode.renderer;


import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ruoyi.common.qrcode.Parameters;
import com.ruoyi.common.qrcode.Shape;

import java.awt.*;


/**
 * ClassName: RendererRound3 (A3)
 *
 * @author guoxinlu
 * @since 2022-07-10 15:53
 */
public class RendererRound3 extends Renderer {

    public RendererRound3() {
        style = RendererStyle.ROUND3;

        parameters = new Parameters();
        parameters.setErrorCorrectionLevel(ErrorCorrectionLevel.M);
        parameters.setAnchorPointShape(Shape.PLANET);
        parameters.setAnchorPointColor(Color.BLACK);
        parameters.setDataPointShape(Shape.RANDOM);
        parameters.setDataPointScale(80);
        parameters.setDataPointOpacity(100);
        parameters.setDataPointColor(Color.BLACK);
    }

    @Override
    public RendererBaseAdjuster adjust() {
        return new RendererBaseAdjuster(this);
    }

}
