package ths.core.graphics;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class TextWatermark {

	public byte[] getWatermarkImageData(String file, String drawText) throws IOException {
        BufferedImage bi = ImageIO.read(new File(file));
        drawText(bi, drawText);
        byte[] resultData = encodeJPEG(bi, 100);	
        return resultData;
	}
	
    private void drawText(BufferedImage original, String watermarkText) {
        // create graphics context and enable anti-aliasing
        Graphics2D g2d = original.createGraphics();
        g2d.scale(1, 1);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // create watermark text shape for rendering
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        GlyphVector fontGV = font.createGlyphVector(g2d.getFontRenderContext(), watermarkText);
        Rectangle size = fontGV.getPixelBounds(g2d.getFontRenderContext(), 0, 0);
        Shape textShape = fontGV.getOutline();
        double textWidth = size.getWidth();
        double textHeight = size.getHeight();
        AffineTransform rotate45 = AffineTransform.getRotateInstance(Math.PI / 4d);
        Shape rotatedText = rotate45.createTransformedShape(textShape);

        // use a gradient that repeats 4 times
        g2d.setPaint(new GradientPaint(0, 0,
                            new Color(0f, 0f, 0f, 0.1f),
                            original.getWidth() / 2, original.getHeight() / 2,
                            new Color(1f, 1f, 1f, 0.1f)));
        g2d.setStroke(new BasicStroke(0.5f));

        // step in y direction is calc'ed using pythagoras + 5 pixel padding
        double yStep = Math.sqrt(textWidth * textWidth / 2) + 5;

        // step over image rendering watermark text
        for (double x = -textHeight * 3; x < original.getWidth(); x += (textHeight * 3)) {
            double y = -yStep;
            for (; y < original.getHeight(); y += yStep) {
                g2d.draw(rotatedText);
                g2d.fill(rotatedText);
                g2d.translate(0, yStep);
            }
            g2d.translate(textHeight * 3, -(y + yStep));
        }
    }

    private byte[] encodeJPEG(BufferedImage image, int quality) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream((int) ((float) image.getWidth() * image.getHeight() / 4));
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
        quality = Math.max(0, Math.min(quality, 100));
        param.setQuality((float) quality / 100.0f, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(image);
        byte[] result = baos.toByteArray();
        baos.close();
        return result;
    }	
}
