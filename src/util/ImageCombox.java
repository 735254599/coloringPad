package util;

import java.awt.Color;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class ImageCombox extends JComboBox{
	public ImageCombox(){
		setFont(FontFactory.getSongFont(15));
		setSize(180,30);//��С��λ��
		setOpaque(false);
		setBackground(Color.WHITE);
		setUI(new BasicComboBoxUI() {//��дUI��
            public void installUI(JComponent comboBox) {
                super.installUI(comboBox);
                //listBox.setForeground(Color.WHITE);
                //listBox.setSize(170, getHeight());
                listBox.setOpaque(false);
                listBox.setSelectionBackground(new Color(0,0,0,0));
                listBox.setSelectionForeground(Color.BLACK);
            }
             
            @Override
			protected ComboBoxEditor createEditor() {
				// TODO Auto-generated method stub
				return super.createEditor();
			}

			/**
             * �÷��������ұߵİ�ť
             */
            protected JButton createArrowButton() {
                //return super.createArrowButton();
            	ImageButton a = new ImageButton("icon/down1.png");//�������µļ�ͷ��ͼƬ
            	//a.setLocation(a.getLocation().x, 10);
            	//a.setLayout(null);
            	return a;
            }
        });
	}
}
