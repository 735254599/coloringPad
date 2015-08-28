package tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import canvas.Canvas;

/*
 * ��ë����(���´ε����켣����εĲ�ͬ���������ܶ�ֱ��)
 */
public class FurTool extends AbstractTool{
	private static DrawTool tool = null;
	private ArrayList<Point> path = new ArrayList<Point>();
	private int deameter = 4;
	private int spread = 500;
	private FurTool(Canvas canvas){
		super(canvas);
	}
	public static DrawTool getInstance(Canvas canvas) {
		if (tool == null) {
			tool = new FurTool(canvas);
		}
		return tool;
	}
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		// ��ȡͼƬ��Graphics����
		Graphics g = getCanvas().getImg().getGraphics();
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);//�������ʾ��
		g2.setStroke(new BasicStroke(AbstractTool.Stroke));
		if(AbstractTool.color != null){//�����ɫ��Ϊ��
			g2.setColor(AbstractTool.color);
		}
		else if(AbstractTool.isColorRandom == true){//���������ɫ
			tempColor = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
			g2.setColor(tempColor);
		}
		else if(AbstractTool.texture != null){//������Ϊ��
			g2.setPaint(AbstractTool.texture);
		}
		else
			g2.setPaint(AbstractTool.gradient);
		g2.drawLine(getPressX(), getPressY(), e.getX(), e.getY());
		path.add(new Point(e.getX(),e.getY()));
		for(int i = 0; i<path.size();i++){
			int dx = path.get(i).x - path.get(path.size()-1).x;
			int dy = path.get(i).y - path.get(path.size()-1).y;
			int d = dx * dx + dy*dy;
			if(d<spread){
				g2.drawLine(e.getX() + (dx * deameter),e.getY()+(dy * deameter),
						e.getX() - (dx * deameter), e.getY() - (dy * deameter));
			}
		}
		setPressX(e.getX());
		setPressY(e.getY());
		getCanvas().repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		path.clear();
		super.mouseReleased(e);
	}
	
}
