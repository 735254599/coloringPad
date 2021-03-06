package test;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

public class both3 extends Frame {
	Image orig, croppedAndScaled;
	URL url ;
	public both3() {
		super("Combining Filters");

		try {
			url = new URL("file:c:1.jpg");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			orig = createImage((ImageProducer)url.getContent());

			MediaTracker mt = new MediaTracker(this);
			mt.addImage(orig, 0);
			mt.waitForID(0);
			}
		catch(Exception e) {
			e.printStackTrace();
			}
		FilteredImageSource fis = 
				new FilteredImageSource(orig.getSource(),
						new CropImageFilter(25,200,155,125));

		fis = new FilteredImageSource(
				createImage(fis).getSource(),
				new ReplicateScaleFilter(310,250));

		fis = new FilteredImageSource(
				createImage(fis).getSource(),
				new PropertiesReportingFilter());

		croppedAndScaled = createImage(fis);
		}
	public void update(Graphics g) {
		paint(g);
		}
	public void paint(Graphics g) {
		Insets i = getInsets();
		int ow = orig.getWidth(this); // ow = Original Width

		g.drawImage(orig, i.left, i.top, this);
		g.drawImage(croppedAndScaled, 
				i.left + ow, i.top + 50, this);
		}
	public static void main(String args[]) {
		final Frame f = new both3();
		f.setBounds(100,100,650,380);
		f.setVisible(true);

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				f.dispose();
				System.exit(0);
				}
			});
		}
}
class PropertiesReportingFilter extends ImageFilter {
	public void setProperties(Hashtable ht) {
		super.setProperties(ht);

		System.out.println(ht);
		}
}

