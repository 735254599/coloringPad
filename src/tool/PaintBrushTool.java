package tool;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import canvas.Canvas;

/*
 * ��Ϳ����(һֱ����ɫ����)
 */
public class PaintBrushTool extends AbstractTool{
	private static DrawTool tool = null;
	private PaintBrushTool(Canvas canvas){
		super(canvas);
	}
	public static DrawTool getInstance(Canvas canvas) {
		if (tool == null) {
			tool = new PaintBrushTool(canvas);
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
	}
}
