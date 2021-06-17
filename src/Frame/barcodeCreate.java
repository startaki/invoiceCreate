package Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.krysalis.barcode4j.BarcodeClassResolver;
import org.krysalis.barcode4j.DefaultBarcodeClassResolver;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider;

/**
 * JPanel의 canvas에 바코드를 그리기
 * 
 */
public class barcodeCreate extends JPanel {
	/**
	 * 바코드 생성전 데이터 초기화
	 * @param g
	 */
	private void init (Graphics g) {
		/* 바코드 타입 
		 * "codabar", "code39", "postnet", "intl2of5", "ean-128"
		 * "royal-mail-cbc", "ean-13", "itf-14", "datamatrix", "code128"
		 * "pdf417", "upc-a", "upc-e", "usps4cb", "ean-8", "ean-13" */
		
		String barcodeType = "code128";

		/* 바코드 데이터 */
		String barcodeData = Invoice.getInvoicenumber.toString();

		/* 좌표 */
		int x = 320;
		int y = 40;
		
		/* canvas에 표현될 바코드의 scale */
		int scaleX = 10;
		int scaleY = 11;

		try {
			createBarcode1(g, barcodeType, barcodeData, x, y, scaleX, scaleY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		init(g);
	}
	
	/**
	 * 바코드 생성
	 * 
	 * @param barcodeType
	 * @param barcodeData
	 * @param scaleY 
	 * @param scaleX 
	 * @param dpi
	 */
	private void createBarcode1(Graphics g, String barcodeType, String barcodeData, int x, int y,  int scaleX, int scaleY) throws Exception {
		AbstractBarcodeBean bean1 = null;
		
		BarcodeClassResolver resolver = new DefaultBarcodeClassResolver();
		Class clazz = resolver.resolveBean(barcodeType);
		bean1 = (AbstractBarcodeBean) clazz.newInstance();
		bean1.doQuietZone(true);

		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(x, y); 			/* 좌표 지정 */
		g2d.scale(scaleX, scaleY); 		/* 크기 지정 */

		Java2DCanvasProvider j2dp = new Java2DCanvasProvider(g2d, 0);
		bean1.generateBarcode(j2dp, barcodeData);
	}
}