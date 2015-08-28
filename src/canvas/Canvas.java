package canvas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.LinearGradientPaint;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import tool.AbstractTool;
import tool.DrawTool;
import tool.ToolFactory;
import util.ImageCosur;

import javax.imageio.ImageIO;
import javax.swing.*;




import mainface.MainFace;
/*
 * ����ǻ����࣬�ڴ˻�ͼ
 */
public class Canvas extends JPanel{
	boolean t = true;
	private static final long serialVersionUID = 1L;
	private MainFace frame = null;
	private BufferedImage img = null;//������ͼ��bufferedImage
	private DrawTool tool = null;//��ǰ�Ĺ���
	private Graphics g;//��ͼ����
	private final int maxGraphicsCount = 50;//�����ͼ���󱣴���Ŀ
	private boolean canSave = false;//�Ƿ��ܱ�����ͼ����
	private Service service = new Service();



	ImageCosur il;
	
	public Canvas(MainFace frame,int size,int height){
		this.setFrame(frame);
		//�ڻ�����ʼ��ʱ��ʼ��ͼƬ����ͼ����g
		setSize(size,height);
		img = new SaveImage(getWidth()-1,getHeight()-1, BufferedImage.TYPE_INT_ARGB);
		g = img.getGraphics();
		setLayout(null);
		setOpaque(false);
		init();
	}


	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img){
		this.img = img;
	}
	public DrawTool getTool() {
		return tool;
	}

	public void setTool(DrawTool tool) {
		this.tool = tool;
	}
	public void init(){
		service.initDrawSpace(this);
		tool = ToolFactory.getToolInstance(this,DrawTool.PENCIL_TOOL);
		MouseMotionListener motionListener = new MouseMotionAdapter() {
			// �϶����
			public void mouseDragged(MouseEvent e) {
				tool.mouseDragged(e);
				canSave = true;
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// �ƶ����
			public void mouseMoved(MouseEvent e) {
				tool.mouseMoved(e);
			}

		};
		// ������������
		MouseListener mouseListener = new MouseAdapter() {
			// �ɿ����
			public void mouseReleased(MouseEvent e) {
				tool.mouseReleased(e);
				/*if(canSave){
					BufferedImage buImg = getImg();//ÿ���ɿ��󱣴�ͼƬ
					try {
						ImageIO.write(buImg, "jpg", new File("c:123.jpg"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(Service.count != maxGraphicsCount){
						Service.canvasImg.add(buImg);
						Service.count++;
						
					}
					else{
						Service.canvasImg.remove(0);//�������Ķ��󳬳������õ����ֵ�����list��ɾ��һ����������
						Service.canvasImg.add(buImg);
					}
				}*/
				canSave = false;
			}

			// �������
			public void mousePressed(MouseEvent e) {
				tool.mousePressed(e);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// ������
			public void mouseClicked(MouseEvent e) {
				tool.mouseClicked(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				AbstractTool.cursorIcon.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				AbstractTool.cursorIcon.setVisible(false);
				remove(AbstractTool.cursorIcon);
				repaint();
			}
			
		};

		addMouseMotionListener(motionListener);
		addMouseListener(mouseListener);
		addComponentListener(new ComponentAdapter(){

			@Override
			public void componentResized(ComponentEvent e) {
				//img.setIsSaved(false);
				SaveImage temp = null;
				temp = new SaveImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);			
				Graphics g = temp.getGraphics();
				g.drawImage(img, 0, 0,
						AbstractTool.drawWidth, AbstractTool.drawHeight, null);
				setImg(temp);
				AbstractTool.drawWidth = getWidth();
				AbstractTool.drawHeight = getHeight();
				if(AbstractTool.gradientColor!=null){
					Color[] color = AbstractTool.gradientColor;
					float[] po = new float[]{0.0f,1.0f/6,2.0f/6f,3.0f/6,4.0f/6,5.0f/6,1.0f};
					LinearGradientPaint gradient1 = new LinearGradientPaint(0,0,AbstractTool.drawWidth,AbstractTool.drawHeight,po,color);
					AbstractTool.gradient = gradient1;
				}
			}
			
		});
	}
	public void paintComponent(Graphics g) {
		// draw
		service.repaint(g, img);
	}


	public MainFace getFrame() {
		return frame;
	}


	public void setFrame(MainFace frame) {
		this.frame = frame;
	}
	public Service getService() {
		return service;
	}


	public void setService(Service service) {
		this.service = service;
	}

	@Override
	public void update(Graphics arg0) {
		//paint(arg0);
		repaint();
	}
}

