package menu;

import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JDialog;

import com.sun.awt.AWTUtilities;

import mainface.MainFace;
//import menu.PullDownPanel.InnerPanel;
import util.ImageButton;
import util.Info;

public class MenuContainer extends JDialog{
	private static int x,y,x1,y1,x2,y2,x_,y_,x1_,y1_,x2_,y2_;
	private ImageButton close = null;
	Point nextPoint = null;//�����϶�����ʱ��ǰ��������
	Point frontPoint = null;
	Point temp = null;
	private int  front_next = 1;//��0������קʱ��ǰһ���㣬��1�����һ����
	private boolean justMoveFromLeft = false,justMoveFromRight = false;
	private boolean justMoveFromTop = false,justMoveFromBottom = false;
	//private InnerPanel ip = null;
	private MainFace frame;
	private ImageButton button1;
	private MM mm = new MM();
	class MM extends MouseAdapter{//�����ƶ��¼�
		 @Override
		public void mousePressed(MouseEvent e) {
			x  = e.getXOnScreen();
			x_ = e.getXOnScreen();
			y  = e.getYOnScreen();
			y_ = e.getYOnScreen();
			x2 = getLocation().x;
			y2 = getLocation().y;
			frontPoint = new Point(x, y);//��¼���µĵĵ�һ����
			nextPoint = new Point(x, y);
			temp = new Point(x, y);
			
		}
		public void mouseDragged(MouseEvent e) {
			x1 = x1_ = e.getXOnScreen();
			y1 = y1_ = e.getYOnScreen();
			if(front_next++ %2 == 0){
				frontPoint = new Point(temp.x, temp.y);
				temp = new Point(nextPoint.x,nextPoint.y);
			}
			nextPoint = new Point(x1, y1);//��¼��һ����
			
			if(getLocationOnScreen().x <= 0){//�����������ʱ
				
				if(getMouseDirection(frontPoint, nextPoint, "x")==1)
					setLocation(1, y1-y+y2);
				if(getMouseDirection(frontPoint, nextPoint, "x")==0)//�����˶�
					setLocation(0, y1-y+y2);
				justMoveFromLeft = true;
			}
			else if((getLocationOnScreen().x + getWidth())>= Info.MAXWIDTH){//�������ұ�ʱ
				
				if(getMouseDirection(frontPoint, nextPoint, "x")==0)//�����˶�
					setLocation(Info.MAXWIDTH - getWidth() -1, y1-y+y2);
				if(getMouseDirection(frontPoint, nextPoint, "x")==1)//�����˶�
					setLocation(Info.MAXWIDTH - getWidth(), y1-y+y2);
				justMoveFromRight = true;
			}
			else if(getLocationOnScreen().y <=0){//�������Ϸ�
				if(getMouseDirection(frontPoint, nextPoint, "y")==3)//����
					setLocation(x1-x+x2, 0);
				if(getMouseDirection(frontPoint, nextPoint, "y")==2)
					setLocation(x1-x+x2,1);
				justMoveFromTop = true;
			}
			else if(getLocationOnScreen().y + getHeight() >= Info.MAXHEIGHT){//�������·�
				if(getMouseDirection(frontPoint, nextPoint, "y")==3)//����
					setLocation(x1-x+x2, Info.MAXHEIGHT - getHeight() -1);
				if(getMouseDirection(frontPoint, nextPoint, "y")==2)
					setLocation(x1-x+x2,Info.MAXHEIGHT - getHeight());
				justMoveFromBottom= true;
			}
			else{	//û����Ļ�߿��˶�ʱ
				if(justMoveFromLeft)
					setLocation(1, y1-y+y2);
				else
					setLocation(x1-x+x2, y1-y+y2);	
				if(justMoveFromRight)
					setLocation(Info.MAXWIDTH - getWidth() -1, y1-y+y2);
				else
					setLocation(x1-x+x2, y1-y+y2);
				if(justMoveFromTop)
					setLocation(x1-x+x2,1);
				else
					setLocation(x1-x+x2, y1-y+y2);
				if(justMoveFromBottom)
					setLocation(x1-x+x2,Info.MAXHEIGHT - getHeight() -1);
				else
					setLocation(x1-x+x2, y1-y+y2);
				justMoveFromLeft = false;
				justMoveFromRight = false;
				justMoveFromTop = false;
				justMoveFromBottom = false;
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			front_next = 1;
		}
	}
	public MenuContainer(MainFace frame, ImageButton button){
		this.frame = frame;
		this.button1 = button;
		setUndecorated(true);
		AWTUtilities.setWindowOpaque(this,false);
		setLayout(null);
		setAlwaysOnTop(true);
		addWindowMoveListener();
		frame.addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if(button1.getCount()==0){
					setVisible(false);
					return;
					}
				if(e.getNewState() == Frame.ICONIFIED)
					setVisible(false);
				if(e.getNewState() == Frame.NORMAL)
					setVisible(true);
			}
		});
	}
	private void addWindowMoveListener(){

		addMouseListener(mm);//�����ƶ��¼�
		addMouseMotionListener(mm);//��Ӽ���
	}
	private int getMouseDirection(Point front,Point next,String dir){
		int direction = -1;
	try{
		switch(dir){
			case "x":case"X":{
				if( (next.x - front.x) <0)
					direction = 0;//�����ƶ�
				else
					direction = 1;//����
				break;
			}
			case "y":case"Y":{
				if((next.y - front.y) >0)
					direction = 2;
				else
					direction = 3;
				break;
			}
		default: System.err.println("dir�����������Ӧ��Ϊx ��   y");
		}
	}
		catch(NullPointerException e){
			throw new NullPointerException("���� front �� last Ϊ��");
		}
		return direction;
	}
	public void removeMM(){
		removeMouseListener(mm);
		removeMouseMotionListener(mm);
	}
}
