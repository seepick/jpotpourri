package net.sourceforge.jpotpourri.jpotface;

import java.awt.Image;


/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtImageInfo {
	
	private final Image image;
	
	private final PtWidthHeight widthHeight;
	
	public PtImageInfo(final Image image, final PtWidthHeight widthHeight) {
		this.image = image;
		this.widthHeight = widthHeight;
	}
	
	@Override
	public String toString() {
		return "ImageInfo[image=...;widthHeight=" + this.widthHeight + "]";
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public PtWidthHeight getWidthHeight() {
		return this.widthHeight;
	}
	
}
