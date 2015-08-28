package mainface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import tool.AbstractTool;
import tool.DrawTool.*;
import twaver.TWaverUtil;
import util.FontFactory;
import util.ImageListFactory;
import util.Info;

public class Open implements Info{
	private boolean load = false;//������Դ�Ƿ����
	/**
	 �������������swing�߳�
	 */
	//private static MainFace frame1;
	//private static int width1;
	//private static int height1;
	private static void run(final MainFace frame,final int width, final int height){
		
		SwingUtilities.invokeLater(new Runnable() {	
			@Override
			public void run() {
				frame.setSize(width,height);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setUndecorated(true);//ȡ������װ��
				frame.setLayout(null);
				frame.setMinimumSize(new Dimension(MINWIDTH, MINHEIGHT));
				frame.setVisible(true);
				try {
					frame.setIconImage(ImageIO.read(new File("icon/icon1.png")));
					frame.setTitle("ColoringPad v1.0");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		/*frame1 = Frame;
		width1 = width;
		height1 = height;*/
	}
		public static void initSource(){//������Դ
			long t = System.currentTimeMillis()/1000;
		WelcomePage wp = new WelcomePage();
		wp.setVisible(true);
		wp.setInfo("��ȡ�����ļ�....");
		initTextureImg();
		wp.setInfo("��������ģ��");
		initTextureListMode();
		wp.setInfo("��ȡ�����ļ�...");
		initFont();
		wp.setInfo("��ȡӡ���ļ�");
		initStampImg();
		wp.setInfo("����ӡ��ģ��");
		initStampMode();
		wp.setInfo("���콥��ģ��");
		initTextureGradientMode();
		System.out.println("��ʱ:"+(System.currentTimeMillis()/1000 - t));
		wp.setInfo("��ȡ���,���ڽ���");
		try {
			TimeUnit.MILLISECONDS.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wp.setVisible(false);
		try {
			run(new MainFace(), DEFAULTWIDTH, DEFAULTHEIGHT);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	private static void initTextureImg(){//��������ͼƬ
		File f = new File("media/texture");//����������ͼƬ���ļ���
		List<BufferedImage> img = new ArrayList<BufferedImage>();//��������ͼƬ��List
		String[] name = new String[100];//����ÿ������ͼƬ��·������
		int i ;
		for(i =0;i<f.list().length;i++){
				try {
					img.add( ImageIO.read(new File("media/texture/"+f.list()[i]))  );//����ͼƬ(File��list���������г���Ŀ¼������Ҫ�ֶ�����)
					name[i] = "media/texture/"+f.list()[i];//����ͼƬ·��
				} catch (IOException e) {
					System.out.println("�ļ�����:" +"media/texture/"+f.list()[i]);
				}

		}
		//��ʼ��������list
		ImageListFactory.Textureimg = img;//���浽ImageListFactory
		ImageListFactory.TextureName = name;
	}
	private static void initTextureListMode(){//��ʼ���������˵��б��mode
		DefaultListModel model = new DefaultListModel();
		BufferedImage bi = null;
		for(int j = 0;j<ImageListFactory.Textureimg.size();j++){
			bi = ImageListFactory.Textureimg.get(j);//�������ͼƬ
			model.addElement(new ImageIcon(ImageListFactory.getThumbnail(bi,55,55)));//��model���ͼƬ����СΪ 55*55
		}
		ImageListFactory.TextureModel = model;//����model
	}
	private static void initFont(){//���������ļ�
		File font = new File("Font");//��ȡ�����ļ���
		FontFactory.fontName = font.list();//������������·��
		Map<String,Font> ma =  new HashMap<String, Font>();
		for(String temp : FontFactory.fontName){//ѭ�����������ļ�
			Font fo = null;
			File file;
			FileInputStream fi;
			file = new File("Font/"+temp);
			try {
				fi = new FileInputStream(file);
				fo = Font.createFont(Font.TRUETYPE_FONT,fi);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(IOException e){
				
			}
			catch(FontFormatException e){
				
			}
			ma.put(temp, fo);
			FontFactory.fontMap = ma;
			
		}
	}
	private static void initStampImg(){//����ӡ��ͼƬ
		File f = new File("media/draw");
		String[] count = f.list();//�ļ����µ��ļ�
		String[] folder = new String[50];//�ļ����µ��ļ�������
		String[] name;//ÿ���ļ��е��µ��ļ�
		File temp;
		int i = 0;
		HashMap<String,String[]> FileName = new HashMap<String, String[]>();
		for(String t : count){
			temp = new File("media/draw/"+t);
			if(!temp.isFile()){//������ļ����򱣴��ļ�������
				folder[i++] = "media/draw/"+t;//�ļ���
				name = temp.list();//�ļ����ڵ��ļ�
				FileName.put("media/draw/"+t, name);
				System.out.println(t+"   "+name.length);
			}
		}
		ImageListFactory.StampFolderCount = i;
		ImageListFactory.StampFolderName = folder;
		ImageListFactory.StampFileName = FileName;
	}
	@SuppressWarnings("unchecked")
	private static void initStampMode(){//��ʼ��ӡ��ģ��
		DefaultListModel model = new DefaultListModel();
		Image bi = null;
		HashMap<String,DefaultListModel> list = new HashMap<String,DefaultListModel>();
		int i;
		String abs = new File(".").getAbsolutePath().replace(".","").replace("\\", "/");//��ó��������ļ��еľ���·��
		for(i  = 0;i<ImageListFactory.StampFolderCount;i++){
			model = new DefaultListModel();
			for(String temp : ImageListFactory.StampFileName.get(ImageListFactory.StampFolderName[i])){
				try {
					
					bi = TWaverUtil.getImage("file:/"+abs +ImageListFactory.StampFolderName[i]+"/"+temp);
					//ͨ��TwaverUtil������ͼƬ�������Ч��
					model.addElement(new ImageIcon(ImageListFactory.getThumbnail(bi,55,55)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}//�ڶ���for
			list.put(ImageListFactory.StampFolderName[i],model);//�����ļ��� ֵ���ļ����ڵ�ͼƬ·��
		}//��һ��for
		ImageListFactory.StampModel = list;//����
	}
	private static void initTextureGradientMode(){
		BufferedImage temp;
		DefaultListModel mode = new DefaultListModel();
		LinearGradientPaint imagePaint = null;//������ɫ����ģʽ
		float[] po = new float[]{0.0f,1.0f/6,2.0f/6f,3.0f/6,4.0f/6,5.0f/6,1.0f};//�������������ɫ����ĸ�������
		ArrayList<Color[]> colorList = new ArrayList<Color[]>();//��ɫ����
		int i = 0;
		Color[] one = null;
		while(i<200){
			i++;
			one = new Color[]{new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255)),
					new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255)),
					new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255)),
					new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255)),
					new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255)),
					new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255)),
					new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255))};
			//�������7����ɫ����������ɫ������
			imagePaint = new LinearGradientPaint(0,0,25,25,	po,one
					);//�������������ɫ����
			
			temp = new BufferedImage(25,25,BufferedImage.TYPE_INT_RGB);//����һ����С25*25��ͼƬ��������佥��ģ���е�list
			Graphics gra = temp.getGraphics();
			Graphics2D g2 = (Graphics2D)gra;
			g2.setPaint(imagePaint);
			gra.fillRect(0, 0, 25, 25);
			mode.addElement(new ImageIcon(temp));
			colorList.add(one);
		}
		ImageListFactory.gradientMode = mode;
		ImageListFactory.gradientColor = colorList;
	}
}
