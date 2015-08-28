package menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxUI;

import mainface.MainFace;
import tool.AbstractTool;
import tool.ToolFactory;
import util.FontFactory;
import util.ImageButton;
import util.ImageCombox;
import util.ImageListBox;
import util.ImageListFactory;

public class StampMenu extends Pull{
	private MainFace frame1;
	private ImageButton button;
	private JList list;
	private JComboBox stamp;
	private String select;//combox��ѡ����
	private JSlider opactity = null;//����͸����
	private JSlider gap = null;//���ü��
	private JSlider angle = null;//ÿ����ͼƬ�ĽǶ�
	private float value,value1,value2;
	public StampMenu(MainFace frame,ImageButton button,MenuContainer mc) {
		super(300,700,button,mc);
		this.frame1 = frame;
		this.button = button;
	//	this.mc = mc;
		//setSize(300, 650);
		list = new ImageListBox();
		list.setModel(ImageListFactory.StampModel.get(ImageListFactory.StampFolderName[0]));//����listĬ��ģ��ΪMap�еĵ�һ��
		
		JScrollPane scr = new JScrollPane(list);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				frame1.getCanvas().setTool(ToolFactory.getToolInstance(frame1.getCanvas(),"Stamp_Tool"));
				int index = list.locationToIndex(e.getPoint());//����������list����
				String imagePath = "media/draw/"+select+"/"+ImageListFactory.StampFileName.get("media/draw/"+select)[index];//�����������ͼƬ��	
				BufferedImage stampImage = null;
				//System.out.println(imagePath);
				try {
					stampImage = ImageIO.read(new File(imagePath));//����ͼƬ
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				AbstractTool.stamp = stampImage;//����AbstractTool��ӡ��ͼƬΪ��ѡ���
			}
			 
		});
		scr.setSize(55*5-28, 55*5);//���ù������Ĵ�С
		scr.setLocation(15, 57);//λ��
		scr.setOpaque(false);//����͸��
		stamp = getComboBox();
		//scr.setBackground(Color.WHITE);
		opactity = new SimpleSlider(100,0,20,5);
		opactity.setBounds(15,380,55*5-28,35);
		opactity.setValue((int)(AbstractTool.stampOpactity*100));
		gap = new SimpleSlider(55,5,10,5);
		gap.setBounds(15,465,55*5-28,35);
		gap.setToolTipText("����");
		gap.setValue(AbstractTool.stampGap);
		angle = new JSlider(1,8);
		angle.setBounds(15,550,55*5-28,35);
		angle.setOpaque(false);
		angle.setForeground(Color.WHITE);
		angle.setValue(AbstractTool.stampAngle);
		//angle.setMaximum(8);
		//angle.setMinimum(1);
		angle.setPaintLabels(true);
		angle.setPaintTicks(true);
		angle.setPaintTrack(true);
		angle.setMajorTickSpacing(7);
		angle.setMinorTickSpacing(1);
		opactity.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int temp = opactity.getValue();
				value = AbstractTool.stampOpactity = temp/100.0f;
				repaint();
			}
		});
		gap.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int temp = gap.getValue();
				value1 = AbstractTool.stampGap = temp;
				repaint();
			}
		});
		angle.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int temp = angle.getValue();
				value2 = AbstractTool.stampAngle = temp;
				repaint();
			}
		});
		add(opactity);
		add(gap);
		add(angle);
		add(scr);
		add(stamp);
	}
	private JComboBox getComboBox(){//����һ�����������ݵ�������
		JComboBox stamp = new ImageCombox();
		stamp.setLocation(15,10);
		//stamp = new JComboBox();
		for(int i = 0;i<ImageListFactory.StampFolderCount;i++){
			stamp.addItem(ImageListFactory.StampFolderName[i].split("/")[2]);//Ϊ�������������ݣ�ֻ�����˴��ͼƬ���ļ�������
																			//eg:  media/draw/����    ---->  ����   
		}
		select = (String) stamp.getSelectedItem();//��õ�ǰѡ����(String  ͼƬ�ĸ�Ŀ¼)
		stamp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox temp = (JComboBox)e.getSource();
				select = (String) temp.getSelectedItem();//��õ�ǰѡ����(String  ͼƬ�ĸ�Ŀ¼)
				//System.out.println(select);
				DefaultListModel model = ImageListFactory.StampModel.get("media/draw/"+select);//���������б���ѡ���ֵ������list��Mode
				list.setModel(model);
			}
		});
		return stamp;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.drawString("͸����", opactity.getLocation().x, opactity.getLocation().y-15);
		g.drawString(""+AbstractTool.stampOpactity,opactity.getWidth()-30, opactity.getLocation().y-15);
		g.drawString("���", gap.getLocation().x, gap.getLocation().y-15);
		g.drawString(""+AbstractTool.stampGap,gap.getWidth()-30, gap.getLocation().y-15);
		g.drawString("��λ����", angle.getLocation().x, angle.getLocation().y-15);
		g.drawString(""+AbstractTool.stampAngle, angle.getWidth()-30, angle.getLocation().y-15);
		g.drawString("�ɴ�С",gap.getLocation().x,getHeight()-45);
		g.drawString("��С����",gap.getWidth()-70,getHeight()-45);
		
		
	}
	
}
