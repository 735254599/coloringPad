package menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import tool.AbstractTool;

import canvas.Service;

import mainface.MainFace;
/*
 * ���˵��ϸ�λ��
 */
public class BackButton extends FunctionButton{
	private MainFace frame1;
	public BackButton(String path,MainFace frame)throws IOException{
		super(path);
		this.frame1 = frame;
		setToolTipText("����");
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
	}
}
