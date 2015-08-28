package canvas;

import java.awt.image.BufferedImage;

public class SaveImage extends BufferedImage{
	private boolean isSaved = true;//�Ƿ��Ѿ�����
	public SaveImage(int width, int height, int imageType) {
		super(width, height, imageType);
		this.getGraphics().fillRect(0, 0, width, height);
	}
	/*
	 * �����Ƿ��Ѿ�����
	 */
	public void setIsSaved(boolean b) {
		this.isSaved = b;
	}
	/*
	 * �����Ƿ��Ѿ�����
	 */
	public boolean isSaved() {
		return this.isSaved;
	}
}
