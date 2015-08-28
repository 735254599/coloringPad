package tool;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.FontFactory;

import canvas.Canvas;

public class PencilTool extends AbstractTool{
	//private  BufferedImage img = null;
	int i = 0;

	private static DrawTool tool = null;
	//Path2D path = new Path2D.Double();
	private PencilTool(Canvas canvas) {
		super(canvas);
	}
	public static DrawTool getInstance(Canvas canvas) {
		if (tool == null) {
			tool = new PencilTool(canvas);
		}
		return tool;
	}
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		// ��ȡͼƬ��Graphics����
		
		Graphics g = getCanvas().getImg().getGraphics();//���ͼƬ����ͼ����
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);//�������ʾ��
		g2.setStroke(new BasicStroke(AbstractTool.Stroke,AbstractTool.LineStyle,BasicStroke.JOIN_ROUND));//���ݲ˵���ֵ�����û��ʴ�С�ͷ��
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, AbstractTool.brushOpactity));//��͸����
		
		if(AbstractTool.color != null){//�����ɫ��Ϊ��
			g2.setColor(AbstractTool.color);
		}
		else if(AbstractTool.isColorRandom == true){//���������ɫ
			if(i++ %30 ==0){
				tempColor = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
			}
			g2.setColor(tempColor);
		}
		else if(AbstractTool.texture != null){//������Ϊ��
			g2.setPaint(AbstractTool.texture);
		}
		else
			g2.setPaint(AbstractTool.gradient);
		//g2.drawLine(e.getX(), e.getY(),e.getX(), e.getY());
		g2.drawLine(getPressX(), getPressY(), e.getX(), e.getY());
		//���ߣ�����Ϊ�ϴε�����������ε�
		setPressX(e.getX());//�����ϴλ��ߵ�����Ϊ��ε�
		setPressY(e.getY());
		getCanvas().repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		Graphics g = getCanvas().getImg().getGraphics();
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);//�������ʾ��
		if(AbstractTool.color != null){
			g2.setColor(AbstractTool.color);
		}
		else if(AbstractTool.isColorRandom == true){
			g2.setColor(new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
		}
		else if(AbstractTool.texture != null){
			g2.setPaint(AbstractTool.texture);
		}
		else
			g2.setPaint(AbstractTool.gradient);
		g2.setStroke(new BasicStroke(AbstractTool.Stroke,AbstractTool.LineStyle,BasicStroke.JOIN_ROUND));
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , AbstractTool.brushOpactity));
		g2.drawLine(e.getX(),e.getY(), e.getX(), e.getY());	
		//���µ����£�ֻ��Ҫ������ǰ�㵽��ǰ����߼���
		getCanvas().repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//path.reset();
		super.mouseReleased(e);
	}

}
