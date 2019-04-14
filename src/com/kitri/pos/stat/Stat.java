package com.kitri.pos.stat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

/*
	Stat : ��� �ӽ� ���� ������
*/

public class Stat extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField notice;
	private JPanel pMonitor;

	// ī�巹�̾ƿ� �гο� ���� �ٸ� Ŭ������ �г� 4�� ����
	ViewStatProduct v1 = new ViewStatProduct(); // ��ǰ�� �г�
	ViewStatYear v2 = new ViewStatYear(); // ������ �г�
	ViewStatMonth v3 = new ViewStatMonth(); // ���� �г�
	ViewStatDay v4 = new ViewStatDay(); // �Ϻ� �г�

	// ī�巹�̾ƿ��� ȭ�� ��ȯ �޼ҵ带 �ٸ� Ŭ�������� ���� ����, �������� ����
	CardLayout card = new CardLayout();

	// ������ ��ư 4��
	private JButton sBtnProduct;
	private JButton sBtnYear;
	private JButton sBtnMonth;
	private JButton sBtnDay;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stat frame = new Stat();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Stat() {
		setFont(new Font("���� ���", Font.BOLD, 20));
		setTitle("Force.pos");
//		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1326, 753);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel pStatusBar = new JPanel();
		pStatusBar.setBackground(new Color(0, 0, 128));
		pStatusBar.setBounds(0, 0, 1308, 51);
		contentPane.add(pStatusBar);
		pStatusBar.setLayout(null);

		JLabel titleLabel = new JLabel("Force. pos");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBackground(new Color(0, 0, 128));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("���� ���", Font.BOLD, 25));
		titleLabel.setBounds(14, 8, 241, 31);
		pStatusBar.add(titleLabel);

		notice = new JTextField();
		notice.setFont(new Font("���� ���", Font.PLAIN, 20));
		notice.setText("\uC0C1\uD488\uBA85(..)\uB294 \uC720\uD1B5\uAE30\uD55C\uC774 \uC9C0\uB0AC\uC2B5\uB2C8\uB2E4.");
		notice.setHorizontalAlignment(SwingConstants.CENTER);
		notice.setBounds(258, 8, 726, 31);
		pStatusBar.add(notice);
		notice.setColumns(10);

		JLabel dateLabel = new JLabel("2019-04-01 \uC624\uD6C4 5:01");
		dateLabel.setBackground(new Color(0, 0, 128));
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setForeground(new Color(255, 255, 255));
		dateLabel.setFont(new Font("���� ���", Font.PLAIN, 20));
		dateLabel.setBounds(1016, 8, 278, 31);
		pStatusBar.add(dateLabel);

		JPanel pMainBtn = new JPanel();
		pMainBtn.setBackground(new Color(255, 255, 255));
		pMainBtn.setBounds(0, 585, 1144, 120);
		contentPane.add(pMainBtn);
		pMainBtn.setLayout(null);

		JLabel idLabel = new JLabel("\uAD00\uB9AC\uC790");
		idLabel.setBackground(new Color(105, 105, 105));
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		idLabel.setFont(new Font("���� ���", Font.BOLD, 20));
		idLabel.setBounds(860, 0, 201, 120);
		pMainBtn.add(idLabel);

		JButton mBtnInven = new JButton("\uC7AC\uACE0");
		mBtnInven.setBackground(new Color(28, 94, 94));
		mBtnInven.setForeground(new Color(255, 255, 255));
		mBtnInven.setFont(new Font("���� ���", Font.BOLD, 20));
		mBtnInven.setBounds(0, 0, 157, 120);
		pMainBtn.add(mBtnInven);

		JButton mBtnSale = new JButton("\uD310\uB9E4");
		mBtnSale.setBackground(new Color(99, 166, 166));
		mBtnSale.setFont(new Font("���� ���", Font.BOLD, 20));
		mBtnSale.setForeground(new Color(255, 255, 255));
		mBtnSale.setBounds(156, 0, 157, 120);
		pMainBtn.add(mBtnSale);

		JButton mBtnCalc = new JButton("\uC815\uC0B0");
		mBtnCalc.setBackground(new Color(28, 94, 94));
		mBtnCalc.setForeground(new Color(255, 255, 255));
		mBtnCalc.setFont(new Font("���� ���", Font.BOLD, 20));
		mBtnCalc.setBounds(313, 0, 157, 120);
		pMainBtn.add(mBtnCalc);

		JButton mBtnStat = new JButton("\uD1B5\uACC4");
		mBtnStat.setBackground(new Color(99, 166, 166));
		mBtnStat.setFont(new Font("���� ���", Font.BOLD, 20));
		mBtnStat.setForeground(new Color(255, 255, 255));
		mBtnStat.setBounds(470, 0, 157, 120);
		pMainBtn.add(mBtnStat);

		JButton mBtnAccount = new JButton("\uACC4\uC815");
		mBtnAccount.setBackground(new Color(28, 94, 94));
		mBtnAccount.setForeground(new Color(255, 255, 255));
		mBtnAccount.setFont(new Font("���� ���", Font.BOLD, 20));
		mBtnAccount.setBounds(626, 0, 157, 120);
		pMainBtn.add(mBtnAccount);

		JPanel pSellFunction = new JPanel();
		pSellFunction.setBackground(new Color(0, 0, 128));
		pSellFunction.setBounds(1144, 50, 164, 655);
		contentPane.add(pSellFunction);
		pSellFunction.setLayout(null);

		sBtnProduct = new JButton("��ǰ��");
		sBtnProduct.setForeground(new Color(255, 255, 255));
		sBtnProduct.setBackground(new Color(0, 0, 128));
		sBtnProduct.setFont(new Font("���� ���", Font.BOLD, 20));
		sBtnProduct.setBounds(0, 0, 164, 120);
		pSellFunction.add(sBtnProduct);

		sBtnYear = new JButton("������");
		sBtnYear.setBackground(new Color(100, 149, 237));
		sBtnYear.setForeground(new Color(255, 255, 255));
		sBtnYear.setFont(new Font("���� ���", Font.BOLD, 20));
		sBtnYear.setBounds(0, 130, 164, 120);
		pSellFunction.add(sBtnYear);

		sBtnMonth = new JButton("����");
		sBtnMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		sBtnMonth.setBackground(new Color(0, 0, 128));
		sBtnMonth.setForeground(new Color(255, 255, 255));
		sBtnMonth.setFont(new Font("���� ���", Font.BOLD, 20));
		sBtnMonth.setBounds(0, 260, 164, 120);
		pSellFunction.add(sBtnMonth);

		sBtnDay = new JButton("�Ϻ�");
		sBtnDay.setBackground(new Color(100, 149, 237));
		sBtnDay.setForeground(new Color(255, 255, 255));
		sBtnDay.setFont(new Font("���� ���", Font.BOLD, 20));
		sBtnDay.setBounds(0, 390, 164, 120);
		pSellFunction.add(sBtnDay);

		pMonitor = new JPanel();
		pMonitor.setBackground(new Color(255, 255, 255));
		pMonitor.setBounds(0, 50, 1144, 535);
		contentPane.add(pMonitor);

		/////////////////////////////////////////////////////////////////////////////////
		// �г��� ���̾ƿ��� ī�巹�̾ƿ����� �����ϱ�
		pMonitor.setLayout(card);
		// �гο� v1~v4�� ���̸鼭 ���ڿ� �̸� ����
		pMonitor.add("viewStatProduct", v1);
		pMonitor.add("viewStatYear", v2);
		pMonitor.add("viewStatMonth", v3);
		pMonitor.add("viewStatDay", v4);

		// ó�� ������ ����Ʈ �г� ���� (��ǰ�� ���)
		card.show(pMonitor, "viewStatProduct");

		// ������ ��ư���� �̺�Ʈ ���
		sBtnProduct.addActionListener(this);
		sBtnYear.addActionListener(this);
		sBtnMonth.addActionListener(this);
		sBtnDay.addActionListener(this);

	}

	// �׼Ǹ����� �������̽� �޼ҵ� override
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
		if (ob == sBtnProduct) {
			card.show(pMonitor, "viewStatProduct");
		} else if (ob == sBtnYear) {
			card.show(pMonitor, "viewStatYear");
		} else if (ob == sBtnMonth) {
			card.show(pMonitor, "viewStatMonth");
		} else if (ob == sBtnDay) {
			card.show(pMonitor, "viewStatDay");
		}

	}

	// <���̺� ���� ��� ����> �޼ҵ�
	public static void tableCellCenter(JTable t) {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // ����Ʈ���̺��������� ����
		dtcr.setHorizontalAlignment(SwingConstants.CENTER); // �������� ���������� CENTER��

		TableColumnModel tcm = t.getColumnModel(); // ������ ���̺��� �÷����� ������

		// ��ü ���� ����
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
	}

}
