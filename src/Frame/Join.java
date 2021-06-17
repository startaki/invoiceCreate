package Frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import MemberDB.MemberDAO;
import MemberDB.MemberDB;

public class Join extends JFrame {
	private JLabel mem_joinlb, mem_idlb, mem_passwordlb, mem_namelb, mem_emaillb, mem_phonelb, mem_postnumlb;
	private JButton findExistID, mem_addr_search_btn, joinComplete_btn, joinCancel_btn;
	private JTextField mem_id, mem_name, mem_email, mem_phone, mem_postnum, mem_addr, mem_addr_detail;
	private JPasswordField mem_password;

	private boolean findExistID_chk = false;
	private boolean addr_detail_focused = false;
	
	private Font ft_title = new Font("����", Font.BOLD, 20);
	private Font basicfont = new Font("���� ���", Font.PLAIN, 11);
	private String dialogfont = "<html><h1 style='font-family:Malgun Gothic; font-weight: normal; font-size: 11pt;'>";

	public Join() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(400, 500);
		setLocationRelativeTo(null);

		mem_joinlb = new JLabel("ȸ������");
		mem_joinlb.setFont(ft_title);
		mem_joinlb.setBounds(160, 40, 100, 20);
		add(mem_joinlb);

		// ID �Է¶�
		mem_idlb = new JLabel("���̵�");
		mem_idlb.setBounds(50, 100, 70, 20);
		mem_idlb.setFont(basicfont);
		add(mem_idlb);

		mem_id = new JTextField();
		mem_id.setBounds(140, 100, 120, 25);
		mem_id.setFont(basicfont);
		add(mem_id);

		findExistID = new JButton("�ߺ�üũ");
		findExistID.setBounds(270, 100, 79, 25);
		findExistID.setFont(basicfont);
		add(findExistID);

		findExistID.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MemberDAO dao = MemberDAO.getInstance();
				// ^[a-z0-9]{6,20}$ : 6~20���� ���� ��ҹ���, ���ڸ� ��� ������
				if (dao.findExistID(mem_id.getText())) {
					// DB���� �̹� �����ϴ� ���̵� üũ
					JOptionPane.showMessageDialog(null, dialogfont + "�̹� ������� ���̵��Դϴ�.");
				} else if (Pattern.matches("^[A-Za-z0-9]{6,20}$", mem_id.getText())) {
					JOptionPane.showMessageDialog(null, dialogfont + "��� ������ ���̵��Դϴ�.");
					findExistID_chk = true;
				} else {
					JOptionPane.showMessageDialog(null, dialogfont + "���̵�� 6~20���� ���� ��ҹ���, ���ڸ� ��� �����մϴ�.");
				}
			}
		});

		// PASSWORD �Է¶�
		mem_passwordlb = new JLabel("�н�����");
		mem_passwordlb.setBounds(50, 130, 70, 20);
		mem_passwordlb.setFont(basicfont);
		add(mem_passwordlb);

		mem_password = new JPasswordField();
		mem_password.setEchoChar('��');
		mem_password.setBounds(140, 130, 120, 25);
		mem_password.setFont(basicfont);
		add(mem_password);

		// �̸� �Է¶�
		mem_namelb = new JLabel("�̸�");
		mem_namelb.setBounds(50, 160, 70, 20);
		mem_namelb.setFont(basicfont);
		add(mem_namelb);

		mem_name = new JTextField();
		mem_name.setBounds(140, 160, 180, 25);
		mem_name.setFont(basicfont);
		add(mem_name);

		// �̸��� �Է¶�
		mem_emaillb = new JLabel("�̸���");
		mem_emaillb.setBounds(50, 190, 70, 20);
		mem_emaillb.setFont(basicfont);
		add(mem_emaillb);

		mem_email = new JTextField();
		mem_email.setBounds(140, 190, 180, 25);
		mem_email.setFont(basicfont);
		add(mem_email);

		// ��ȭ��ȣ �Է¶�
		mem_phonelb = new JLabel("��ȭ��ȣ");
		mem_phonelb.setBounds(50, 220, 70, 20);
		mem_phonelb.setFont(basicfont);
		add(mem_phonelb);

		mem_phone = new JTextField();
		mem_phone.setBounds(140, 220, 180, 25);
		mem_phone.setFont(basicfont);
		add(mem_phone);

		// �����ȣ api�� �̿��Ͽ� �Ľ��� �����ȣ�� �ּ� �Է�
		mem_postnumlb = new JLabel("�����ȣ");
		mem_postnumlb.setBounds(50, 250, 70, 20);
		mem_postnumlb.setFont(basicfont);
		add(mem_postnumlb);

		mem_postnum = new JTextField();
		mem_postnum.setBounds(140, 250, 120, 25);
		mem_postnum.setFont(basicfont);
		add(mem_postnum);

		mem_addr_search_btn = new JButton("�����ȣ");
		mem_addr_search_btn.setBounds(270, 250, 79, 25);
		mem_addr_search_btn.setFont(basicfont);
		add(mem_addr_search_btn);
		
		mem_addr_search_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Search search = new Search();
				search.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						
						mem_postnum.setText(Search.postnum);
						mem_addr.setText(Search.addr);
					}
				}); 
			}
		});
		
		mem_addr = new JTextField();
		mem_addr.setBounds(50, 280, 300, 25);
		mem_addr.setFont(basicfont);
		add(mem_addr);
		
		// ���ּ�
		mem_addr_detail = new JTextField(" ������ �� �ּҸ� �Է��ϼ���.");
		mem_addr_detail.setFont(basicfont);
		mem_addr_detail.setForeground(Color.GRAY);
		mem_addr_detail.setBounds(50, 310, 300, 25);
		add(mem_addr_detail);

		mem_addr_detail.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (addr_detail_focused == false) {
					mem_addr_detail.setText("");
					mem_addr_detail.setForeground(Color.BLACK);
					addr_detail_focused = true;
				}
			}

			public void focusLost(FocusEvent e) {

			}
		});

		joinComplete_btn = new JButton("ȸ�����ԿϷ�");
		joinComplete_btn.setFont(basicfont);
		joinComplete_btn.setBounds(150, 400, 120, 25);
		add(joinComplete_btn);

		joinComplete_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MemberDB member = new MemberDB();
				if (mem_id.getText().equals("") || mem_password.getText().equals("") || mem_name.getText().equals("")
						|| mem_phone.getText().equals("")) {
					JOptionPane.showMessageDialog(null, dialogfont + "���� ���� �Է��Ͽ��ּ���.");
				} else if (findExistID_chk == false) {
					JOptionPane.showMessageDialog(null, dialogfont + "���̵� �ߺ�üũ�� ���ּ���.");
				} else if (!Pattern.matches(
						"^([\\w\\.\\_\\-])*[a-zA-Z0-9]+([\\w\\.\\_\\-])*([a-zA-Z0-9])+([\\w\\.\\_\\-])+@([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,8}$",
						mem_email.getText())) {
					JOptionPane.showMessageDialog(null, dialogfont + "�ùٸ� �̸��� ������ �Է����ּ���. (�� : aa@aa.aa)");
				} else {
					member.setId(mem_id.getText());
					member.setPassword(mem_password.getText());
					member.setName(mem_name.getText());
					member.setEmail(mem_email.getText());
					member.setPhone(mem_phone.getText().replaceAll("[^0-9]", ""));
					member.setPostnum(mem_postnum.getText());

					MemberDAO dao = MemberDAO.getInstance();
					dao.member_save(member);
					JOptionPane.showMessageDialog(null, dialogfont + "ȸ�������� �Ϸ�Ǿ����ϴ�.");
					dispose();
				}
			}
		});

		joinCancel_btn = new JButton("���");
		joinCancel_btn.setFont(basicfont);
		joinCancel_btn.setBounds(280, 400, 70, 25);
		add(joinCancel_btn);
		joinCancel_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		setVisible(true);
		// ȸ�����ԿϷ� �׼�
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
