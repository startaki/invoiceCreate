package Frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Infomation extends JFrame{
	private JLabel info, info1, info2, info3, url_lb;
	private String url = "https://github.com/startaki/invoiceCreate";
	private Font basicfont = new Font("맑은 고딕", Font.PLAIN, 12);
	public Infomation() {
		super(" 정보");
		setSize(350, 225);
		setLayout(null);
		setLocationRelativeTo(null);
		setBackground(new Color(255, 255, 255));
		
		info = new JLabel("운송장 입출력 프로그램", SwingConstants.CENTER);
		info.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		info.setBounds(-4, 0, 350, 75);
		add(info);
		
		info1 = new JLabel("<html>버전정보 : v1.0</html>");
		info1.setFont(basicfont);
		info1.setBounds(10, 75, 350, 25);
		add(info1);
		
		
		info2 = new JLabel("<html>만든이 : 강성엽</html>");
		info2.setFont(basicfont);
		info2.setBounds(10, 100, 350, 25);
		add(info2);
		
		info3 = new JLabel("<html>Github 주소 : </html>");
		info3.setFont(basicfont);
		info3.setBounds(10, 125, 80, 25);
		add(info3);
		
		url_lb = new JLabel("<html><FONT color=\"#000099\"><U>" + url + "</U></FONT></html>");
		url_lb.setFont(basicfont);
		url_lb.setCursor(new Cursor(Cursor.HAND_CURSOR));
		url_lb.setBounds(90, 125, 350, 25);
		url_lb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
                try {
                	Desktop.getDesktop().browse(new URI(url));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
		});
		
		add(url_lb);
		setVisible(true);
		this.addWindowFocusListener(new WindowAdapter() {
			public void windowLostFocus(WindowEvent e) {
				dispose();
			}
		});
	}
	
	public static void main(String[] args) {
		new Infomation();
		
	}
}
