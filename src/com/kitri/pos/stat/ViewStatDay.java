package com.kitri.pos.stat;

import java.awt.*;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*
	ViewStatDay : �Ϻ� ��� �г�
*/

public class ViewStatDay extends JPanel {

	JComboBox comboYear;
	JComboBox comboMonth;
	JComboBox comboDay;

	JButton btnSearch;

	JScrollPane spShowTable;

	JTable tableResult;
	DefaultTableModel tmodel;
	
	JRadioButton rdBtnTotalPrice;
	JRadioButton rdBtnNetIncome;
	JRadioButton rdBtnCash;
	JRadioButton rdBtnCard;

	JPanel pShowGraph;
	CardLayout graphCard = new CardLayout(); //�׷����� ī�� ���̾ƿ�

	ViewStatDayService vds;

	public ViewStatDay() {
		setSize(new Dimension(1144, 535));
		setLayout(null);

		// #�˻����� �г�#
		JPanel pSetSearch = new JPanel();
		pSetSearch.setBounds(12, 27, 1120, 37);
		add(pSetSearch);
		pSetSearch.setLayout(null);

		JLabel lbShowDate = new JLabel("��ȸ���� :");
		lbShowDate.setBounds(12, 0, 101, 37);
		pSetSearch.add(lbShowDate);
		lbShowDate.setFont(new Font("���� ���", Font.PLAIN, 20));

		// [�޺��ڽ�]
		// �޺��ڽ� ���� �� ����
		Vector<Integer> YearValues = new Vector<Integer>(); // �⵵ ������ ����
		// �� ������ ����
		String MonthValues[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		Vector<Integer> DayValues = new Vector<Integer>(); // �� ������ ����
		// sYearValues�� ���� �⵵ - 1990����� �ֱ�
		Calendar oCalendar = Calendar.getInstance(); // ���� ��¥/�ð� ���� ���� ���� ���
		// ���� ��¥
		int toyear = oCalendar.get(Calendar.YEAR);

		for (int i = toyear; i >= 1990; i--) {
			YearValues.add(i);
		}
		for (int i = 1; i < 32; i++) {
			DayValues.add(i);
		}

		// �޺��ڽ� ����
		comboYear = new JComboBox(YearValues);
		comboYear.setBounds(111, 0, 114, 37);
		pSetSearch.add(comboYear);

		JLabel lbYear = new JLabel("��");
		lbYear.setBounds(227, 2, 38, 32);
		pSetSearch.add(lbYear);
		lbYear.setFont(new Font("���� ���", Font.PLAIN, 20));

		comboMonth = new JComboBox(MonthValues);
		comboMonth.setBounds(261, 0, 114, 37);
		pSetSearch.add(comboMonth);

		JLabel lbMonth = new JLabel("��");
		lbMonth.setBounds(375, 0, 38, 32);
		pSetSearch.add(lbMonth);
		lbMonth.setFont(new Font("���� ���", Font.PLAIN, 20));

		comboDay = new JComboBox(DayValues);
		comboDay.setBounds(405, 0, 114, 37);
		pSetSearch.add(comboDay);

		JLabel lbDay = new JLabel("��");
		lbDay.setBounds(524, -2, 38, 32);
		pSetSearch.add(lbDay);
		lbDay.setFont(new Font("���� ���", Font.PLAIN, 20));

		btnSearch = new JButton("��ȸ");
		btnSearch.setBounds(552, 0, 101, 37);
		pSetSearch.add(btnSearch);
		btnSearch.setFont(new Font("���� ���", Font.BOLD, 20));

		// #���̺� ��ũ�� �г�#
		spShowTable = new JScrollPane();
		spShowTable.setBounds(12, 74, 694, 58);
		add(spShowTable);

		// [���̺�]
		// ���̺� �� ����
		Vector<String> col = new Vector<String>(); // ��
		col.add("���⳯¥");
		col.add("�����հ�");
		col.add("�ΰ���");
		col.add("���ݸ���");
		col.add("ī�����");
		col.add("����");
		tmodel = new DefaultTableModel(col, 0);

		// ���̺� ���̱�
		tableResult = new JTable(tmodel);
		tableResult.setRowMargin(10);
		tableResult.setRowHeight(30);
		// ���̺� �� ��� ����
		Stat.tableCellCenter(tableResult);

		spShowTable.setViewportView(tableResult);

		// #�׷��� �г�#
		pShowGraph = new JPanel();
		pShowGraph.setLayout(graphCard);
		pShowGraph.setBounds(12, 157, 1120, 368);
		add(pShowGraph);

		// #�⺻ �г�#
		// [���� ��ư]
		ButtonGroup bgp = new ButtonGroup(); // ��ư�׷� ����

		rdBtnCard = new JRadioButton("ī��");
		rdBtnCard.setFont(new Font("���� ���", Font.PLAIN, 20));
		rdBtnCard.setBounds(1071, 128, 65, 23);
		add(rdBtnCard);

		rdBtnCash = new JRadioButton("����");
		rdBtnCash.setFont(new Font("���� ���", Font.PLAIN, 20));
		rdBtnCash.setBounds(1002, 128, 65, 23);
		add(rdBtnCash);

		rdBtnNetIncome = new JRadioButton("������");
		rdBtnNetIncome.setFont(new Font("���� ���", Font.PLAIN, 20));
		rdBtnNetIncome.setBounds(913, 128, 85, 23);
		add(rdBtnNetIncome);

		rdBtnTotalPrice = new JRadioButton("�����հ�");
		rdBtnTotalPrice.setSelected(true);
		rdBtnTotalPrice.setFont(new Font("���� ���", Font.PLAIN, 20));
		rdBtnTotalPrice.setBounds(804, 128, 105, 23);
		add(rdBtnTotalPrice);

		// ��ư �׷쿡 ������ư ���
		bgp.add(rdBtnTotalPrice);
		bgp.add(rdBtnCash);
		bgp.add(rdBtnNetIncome);
		bgp.add(rdBtnCard);

		// #�̺�Ʈ ���#
		vds = new ViewStatDayService(this);
		btnSearch.addActionListener(vds);
		
		rdBtnTotalPrice.addItemListener(vds);
		rdBtnNetIncome.addItemListener(vds);
		rdBtnCash.addItemListener(vds);
		rdBtnCard.addItemListener(vds);

	}
}
