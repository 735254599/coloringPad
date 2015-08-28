package canvas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import tool.AbstractTool;

import mainface.MainFace;


/*
 * ��ͼ��ʵ����
 */


public class Service {
	/**
	 * ��������Զ���ͼ��
	 * 
	 * @param path
	 *            String ͼ��·��
	 * @return Cursor;
	 */
	public static List<BufferedImage> canvasImg = new ArrayList<BufferedImage>();
	public static int count = 0;//�����˶����Ż���ͼƬ
	public static int action = 1;
	private ImageFileChooser fileChooser;
	public static Cursor createCursor(String path) {
		Image cursorImage = Toolkit.getDefaultToolkit().createImage(path);
		return Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,
				new Point(10,10), "mycursor");
	}
	public void repaint(Graphics g, BufferedImage bufferedImage) {
		int drawWidth = bufferedImage.getWidth();
		int drawHeight = bufferedImage.getHeight();
		// �ѻ����ͼƬ�滭����
		g.drawImage(bufferedImage, 0, 0, drawWidth, drawHeight, null);
	}
	public Dimension getScreenSize() {
		Toolkit dt = Toolkit.getDefaultToolkit();
		return dt.getScreenSize();
	}
	public void initDrawSpace(Canvas canvas) {
		// ��ȡ��ͼ����
		Graphics g = canvas.getImg().getGraphics();
		// ��ȡ�����Ĵ�С
		Dimension d = canvas.getPreferredSize();
		// ��ȡ��
		int drawWidth = (int) d.getWidth();
		// ��ȡ��
		int drawHeight = (int) d.getHeight();
		// �滭��
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, drawWidth, drawHeight);
	}
	public void save(boolean b, final Canvas canvas) {
		new Thread(new Runnable() {		
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					 fileChooser = new ImageFileChooser();
			 } catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
				if (fileChooser.showSaveDialog(canvas) == ImageFileChooser.APPROVE_OPTION) {
					// ��ȡ��ǰ·��
					File currentDirectory = fileChooser.getCurrentDirectory();
					// ��ȡ�ļ���
					String fileName = fileChooser.getSelectedFile().getName();
					// ��ȡ��׺��
					String suf = fileChooser.getSuf();
					// ��ϱ���·��
					String savePath = currentDirectory + "\\" + fileName + "."
							+ suf;
					try {
						// ��ͼƬд������·��
						ImageIO.write(canvas.getImg(), suf, new File(
								savePath));
					} catch (java.io.IOException ie) {
						ie.printStackTrace();
					}
				}
			}
		}).start();
			
	}

	/**
	 * ��ͼƬ
	 * 
	 * @param frame
	 *            ImageFrame
	 * @return void
	 */
	public void open(final Canvas canvas) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				 try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						 fileChooser = new ImageFileChooser();
				 } catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException | UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						}
				// save(false, canvas);
						if (fileChooser.showOpenDialog(canvas) == ImageFileChooser.APPROVE_OPTION) {
							// ��ȡѡ����ļ�
								File file = fileChooser.getSelectedFile();
							// ���õ�ǰ�ļ���
								fileChooser.setCurrentDirectory(file);
							BufferedImage image = null;
							try {
									// ���ļ���ȡͼƬ
								image = ImageIO.read(file);
								} catch (java.io.IOException e) {
									return;
								}
								// ����
								int width = image.getWidth();
								int height = image.getHeight();
								AbstractTool.drawWidth = width;
								AbstractTool.drawHeight = height;
								// ����һ��MyImage
								BufferedImage myImage = new BufferedImage(width, height,
										BufferedImage.TYPE_INT_RGB);
								// �Ѷ�ȡ����ͼƬ����myImage����
								myImage.getGraphics().drawImage(image, 0, 0, width, height, null);
								canvas.setImg(myImage);
								// repaint
								canvas.repaint();
								// ��������viewport
							}
			}
		}).start();
	}	
}
