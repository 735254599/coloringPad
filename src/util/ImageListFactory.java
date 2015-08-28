package util;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.DefaultListModel;
/*
 * ��ȡͼƬ��һ������
 */
public class ImageListFactory {
	public static List<BufferedImage> Textureimg;
	public static String[] TextureName;
	public static DefaultListModel TextureModel;//����list�е�ģ��
	
	public static Map<String,DefaultListModel> StampModel;//ӡ���е�listģ��
	public static String[] StampFolderName;//ӡ���ļ������� 
	public static int StampFolderCount;//ӡ���ļ��и���
	public static Map<String,String[]> StampFileName;
	//public static  DefaultListModel ttt;
	public static List<Color[]> gradientColor;
	public static DefaultListModel gradientMode;
	/*
	 * ����һ��ָ����С������ͼ
	 */
	public static Image getThumbnail(Image img,int width,int height){
		BufferedImage temp = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics g = temp.getGraphics();
		g.drawImage(img,0, 0, width, height, null);
		g.dispose();
		return temp;
	}
	public static BufferedImage getThumbnail(BufferedImage img,int width,int height){
		BufferedImage temp = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics g = temp.getGraphics();
		g.drawImage(img,0, 0, width, height, null);
		g.dispose();
		return temp;
	}
}