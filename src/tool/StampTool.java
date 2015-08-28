package tool;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import util.ImageCosur;
import util.ImageListFactory;

import canvas.Canvas;

public class StampTool extends AbstractTool{
	private static DrawTool tool = null;
	private Graphics g;
	private Graphics2D g2;
	public static BufferedImage image;
	//private int first,last;//ͼƬ���ŵķ��Ӻͷ�ĸ
	private int count = 1;
	private ImageIcon stampIcon;
	
	private int angle = 1;//ͼƬ��ת�Ƕ�
	private boolean max = true;//��С���ﻹ�ǴӴ�С
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	private int multiple = 7;//ͼƬ��С�ı���
	private StampTool(Canvas canvas) {
		super(canvas);
		// TODO Auto-generated constructor stub
	}
	public static DrawTool getInstance(Canvas canvas) {
		if (tool == null) {
			tool = new StampTool(canvas);
		}

		return tool;
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		g = getCanvas().getImg().getGraphics();
		g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);//�������ʾ��
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, AbstractTool.stampOpactity));//����Stamp�е�ֵ��������͸��ֵ
		if(AbstractTool.stamp !=null){//���ӡ��ͼƬ��Ϊ��
		if((count++ % stampGap) ==0){//ͼƬ�����ÿ��stampGap�Σ�ͼƬ��λ
			image = AbstractTool.stamp;//����ͼƬΪӡ��ͼƬ
			//setDefaultCursor(Toolkit.getDefaultToolkit().createCustomCursor(image,
				//	new Point(image.getWidth()/2,image.getHeight()/2), "mycursor"));
			AffineTransform old = g2.getTransform();//����2D����任
			g2.rotate(Math.PI/angle++,e.getX(),e.getY());//û����ͼƬ���Math.PI/angle++����
			if(max){//���ͼƬΪ�����������Сһ������
				image = ImageListFactory.getThumbnail(image, (multiple)*image.getWidth()/8, (multiple--)*image.getHeight()/8);
				if(0 == multiple)
					max = false;
			}
			else{//��ͼƬΪ��Сʱ���Ŵ�һ������
				image = ImageListFactory.getThumbnail(image, (++multiple)*image.getWidth()/8,(multiple)*image.getHeight()/8);
				if(7 == multiple)
					max = true;
			}
			if(stampAngle==angle) {
				angle = 1;}//��λ��ת����
			g2.drawImage(image,e.getX()-image.getWidth()/2,e.getY()-image.getHeight()/2,image.getWidth(),image.getHeight(),null);	
			g2.setTransform(old);//���þõ�2D����任����ֹ��Ӱ����һ������ͼ
			getCanvas().repaint();
		}
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		count = 1;//��ʼ��
		angle = 1;
		super.mouseReleased(e);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		if(AbstractTool.stamp != null){
			g = getCanvas().getImg().getGraphics();
			g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);//�������ʾ��
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, AbstractTool.stampOpactity));
			//if(AbstractTool.stamp !=null){
			//setFirstLast();
			image = AbstractTool.stamp;
			//image = ImageListFactory.getThumbnail(AbstractTool.stamp,first*AbstractTool.stamp.getWidth()/last,first*AbstractTool.stamp.getHeight()/last);
			g2.drawImage(image,e.getX()-image.getWidth()/2,e.getY()-image.getHeight()/2,image.getWidth(),image.getHeight(),null);
			getCanvas().repaint();
		}
	}
}
