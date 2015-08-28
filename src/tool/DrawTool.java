package tool;

import java.awt.event.MouseEvent;

import javax.swing.event.CaretEvent;

public interface DrawTool {
	public static final String ARROW_TOOL = "Arrow_Tool";//��ͷ����
	public static final String STREAMER_TOOL = "Streamer_TooL";//ֽ������
	public static final String CALLIGRAPHY_TOOL = "Calligraphy_Tool";//�ʼ�����
	public static final String CRAYON_TOOL = "Crayon_Tool";//���ʹ���
	public static final String PAINTBRUSH_TOOL = "PaintBrush_Tool";//��Ϳ����(һֱ����ɫ����)
	public static final String PENCIL_TOOL = "Pencil_Tool";//Ǧ�ʹ���
	public static final String SPRAYPAINT_TOOL = "SprayPaint_Tool";//���Ṥ��(���Ű��µ�ʱ����ɫ�𽥼���)
	public static final String CHROME_TOOL = "Chrome_Tool";//���𹤾�(��������������)
	public static final String FURTOOL = "Fur_Tool";//��ë����(���´ε����켣����εĲ�ͬ���������ܶ�ֱ��)
	public static final String WEB_TOOL = "Web_Tool";//��״����(��chrome���ƣ�����ֻ���ھ൱ǰ���������������)
	public static final String SPIROGRAPH_TOOL = "Spirograph_TooL";//��������
	public static final String CIRCLE_TOOl = "Circle_Tool";
	public static final String JUMP_TOOL = "Jump_Tool";
	
	public static final String STAMP_TOOL = "Stamp_Tool";//ӡ�¹���(����)
	public static final String ERASER_TOOL = "Eraser_Tool";//����
	public static final String TEXT_TOOL = "Text_Tool";//���ֹ���
	public void mouseDragged(MouseEvent e);//����϶�
	public void mouseMoved(MouseEvent e);//����ƶ�
	public void mouseReleased(MouseEvent e);//����ͷ�
	public void mousePressed(MouseEvent e);//��갴��
	public void mouseClicked(MouseEvent e);//��갴�·Ż�
}
