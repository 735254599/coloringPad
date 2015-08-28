package tool;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import canvas.Canvas;

/*
 * ���𹤾�(��������������)
 */
public class ChromeTool extends AbstractTool{
	private static DrawTool tool = null;
	private ArrayList<Point> path = new ArrayList<Point>();
	public static int spread = 30*100;
	private ChromeTool(Canvas canvas){
		super(canvas);
	}
	public static DrawTool getInstance(Canvas canvas) {
		if (tool == null) {
			tool = new ChromeTool(canvas);
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
		g2.setStroke(new BasicStroke(AbstractTool.Stroke,AbstractTool.LineStyle,BasicStroke.JOIN_ROUND));
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, AbstractTool.brushOpactity));
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
		g2.drawLine(getPressX(), getPressY(), e.getX(), e.getY());
		path.add(new Point(e.getX(),e.getY()));
		for(int i = Math.max(0, path.size()-100);i<path.size();i++){//���ƶ��˳���100�����ص�ʱ
			double dx = path.get(i).x - path.get(path.size()-1).x;
			double dy = path.get(i).y - path.get(path.size()-1).y;//��ǰ�㵽���һ����Ĳ�ֵ
			double d = dx*dx + dy*dy;//�����ĳ���
			if(d<spread){//�������ĳ���С�����õ�ֵ
				g2.draw(new Line2D.Double(path.get(path.size()-1).x + (dx * 0.2),path.get(path.size()-1).y + (dy * 0.2),
											path.get(i).x - (dx*0.2),path.get(i).y - (dy * 0.2)));
			}//��һ�����һ���㵽��ǰ����ߣ�dx * 0.2  ��  dy * 0.2 Ϊ ����ߵ����ߵĿհס������ ���������������ģ���ôdx*0.2Ϊ����dy * 0.2 Ϊ��
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
