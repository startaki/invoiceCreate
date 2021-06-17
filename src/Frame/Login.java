package Frame;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import MemberDB.MemberDAO;

public class Login extends JFrame {

	private JLabel loginlb, passwordlb;
	private JTextField input_id;
	private JPasswordField input_password;
	private JButton loginBtn, joinBtn;
	
	private Font basicfont = new Font("���� ���", Font.PLAIN, 11);
	private String dialogfont = "<html><h1 style='font-family:Malgun Gothic; font-weight: normal; font-size: 11pt;'>";
	public static String id, password;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setTitle("����� ����� ���α׷�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(380, 270);
		setLayout(null);
		setLocationRelativeTo(null);

		loginlb = new JLabel("���̵�");
		loginlb.setFont(basicfont);
		loginlb.setBounds(40, 55, 70, 25);
		add(loginlb);

		input_id = new JTextField();
		input_id.setBounds(150, 50, 170, 35);
		add(input_id);
		
		passwordlb = new JLabel("�н�����");
		passwordlb.setFont(basicfont);
		passwordlb.setBounds(40, 105, 70, 25);
		add(passwordlb);

		input_password = new JPasswordField();
		input_password.setEchoChar('��');
		input_password.setBounds(150, 100, 170, 35);
		add(input_password);
		
		joinBtn = new JButton("ȸ������");
		joinBtn.setFont(basicfont);
		joinBtn.setBounds(100, 155, 100, 30);
		add(joinBtn);
		joinBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Join();
			}
		});
		
		loginBtn = new JButton("�α���");
		loginBtn.setFont(basicfont);
		loginBtn.setBounds(220, 155, 100, 30);
		add(loginBtn);
		loginBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				id = input_id.getText();
				password = input_password.getText();
				MemberDAO dao = MemberDAO.getInstance();
				int result = dao.findByIdAndPassword(id, password);
				if (result == 1) {
					// �α��� ���� �޽���
					JOptionPane.showMessageDialog(null, dialogfont + id + "�� �ȳ��ϼ���.");
					Invoice iv = new Invoice(id);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, dialogfont + "���̵�� ��й�ȣ�� Ȯ�����ּ���.");
				}
			}
		});
		setVisible(true);
	}
}
