package ru.ecom4u.web.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageUtil {

	public static BufferedImage createResizedCopyScaled(BufferedImage originalImage, int scaledWidth, int scaledHeight,
			boolean preserveAlpha) {
		int originalWidth = originalImage.getWidth();
		int originalHeight = originalImage.getHeight();

		double kwh = (double) originalWidth / (double) originalHeight;

		int scaledWidthT = (int) (scaledHeight * kwh);
		int scaledHeightT = (int) (scaledWidth / kwh);
		
		if(scaledWidthT < scaledWidth){
			return createResizedCopy(originalImage, scaledWidthT, scaledHeight, preserveAlpha);
		} else {
			return createResizedCopy(originalImage, scaledWidth, scaledHeightT, preserveAlpha);
		}
	}

	public static BufferedImage createResizedCopy(BufferedImage originalImage, int scaledWidth, int scaledHeight,
			boolean preserveAlpha) {
		int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
		Graphics2D g = scaledBI.createGraphics();
		if (preserveAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
		g.dispose();
		return scaledBI;
	}
}
