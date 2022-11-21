package com.texttosign.TextToSign.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import javax.imageio.ImageIO;
import com.texttosign.TextToSign.TextToSignApplication;
import java.awt.FontFormatException;

public class TextToGraphics {
    private ArrayList<Font> fonts = new ArrayList<Font>();

    public TextToGraphics() {
        fonts.add(new Font("Arial", Font.ITALIC, 48));
        try {
            for (int i = 1; i <= 5; ++i) {
                fonts.add(Font.createFont(Font.TRUETYPE_FONT, TextToSignApplication.class
                        .getResourceAsStream("/static/fonts/" + i + ".ttf")));
            }

        } catch (IOException | FontFormatException e) {
            System.out.println("Exception occurred - " + e);
            // Handle exception
        }
    }

    public String getImageFromText(String text, int option) {

        // String text = "Hello World Raaz";

        /*
         * Because font metrics is based on a graphics context, we need to create a small, temporary
         * image so we can ascertain the width and height of the final image
         */
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = getFont(option);// new Font("Arial", Font.ITALIC, 48);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();


        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            // ImageIO.write(img, "png", new File("Text.png"));

            ImageIO.write(img, "png", os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private Font getFont(int option) {

        return fonts.get(option).deriveFont(48f);
        // try {
        // // Font font = Font.createFont(Font.TRUETYPE_FONT,
        // // TextToSignApplication.class.getResourceAsStream("/static/fonts/1.ttf"));

        // return font.deriveFont(48f);

        // } catch (IOException | FontFormatException e) {
        // System.out.println("Exception occurred - " + e);
        // // Handle exception
        // }
        // return new Font("Arial", Font.ITALIC, 48);
    }

}

