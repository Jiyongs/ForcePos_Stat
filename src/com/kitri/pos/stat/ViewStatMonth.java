package com.kitri.pos.stat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.kitri.pos.setting.ViewSetting;

import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;
import java.awt.*;
import java.awt.event.ActionEvent;

/*
	ViewStatMonth : ���� ��� �г�
*/

public class ViewStatMonth extends JPanel {

	JComboBox comboYear;
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
	
	ViewStatMonthService vms;

	public ViewStatMonth() {
		setLayout(null);
		setSize(new Dimension(1144, 535));

		// #�˻����� �г�#
		JPanel pSetSearch = new JPanel();
		pSetSearch.setLayout(null);
		pSetSearch.setBounds(12, 27, 780, 37);
		add(pSetSearch);

		JLabel lbShowDate = new JLabel("��ȸ�Ⱓ :");
		lbShowDate.setFont(new Font("���� ���", Font.PLAIN, 20));
		lbShowDate.setBounds(12, 0, 101, 37);
		pSetSearch.add(lbShowDate);
		
		// [�޺��ڽ�]
		// �޺��ڽ� ���� �� ����
		Vector<Integer> YearValues = new Vector<Integer>(); // �⵵ ������ ����
		//sYearValues�� ���� �⵵ - 1990����� �ֱ�
		Calendar oCalendar = Calendar.getInstance( );  		// ���� ��¥/�ð� ���� ���� ���� ���
		// ���� ��¥
		 int toyear = oCalendar.get(Calendar.YEAR);
		 for(int i = toyear; i>= 1990; i--){
			  YearValues.add(i);
		 }  
		
		// �޺��ڽ� ����
		comboYear = new JComboBox(YearValues);
		comboYear.setBounds(111, 0, 114, 37);
		pSetSearch.add(comboYear);

		btnSearch = new JButton("��ȸ");
		btnSearch.setFont(ViewSetting.sbtnFont);
		btnSearch.setBounds(261, 0, 101, 37);
		pSetSearch.add(btnSearch);
		
		JLabel label = new JLabel("��");
		label.setFont(new Font("���� ���", Font.PLAIN, 20));
		label.setBounds(226, 2, 38, 32);
		pSetSearch.add(label);

		// #���̺� ��ũ�� �г�#
		spShowTable = new JScrollPane();
		spShowTable.setBounds(12, 74, 554, 451);
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
		pShowGraph.setBounds(578, 74, 554, 451);
		add(pShowGraph);

		// #�⺻ �г�#
		// [���� ��ư]
		ButtonGroup bgp = new ButtonGroup(); // ��ư�׷� ����

		rdBtnTotalPrice = new JRadioButton("�����հ�");
		rdBtnTotalPrice.setSelected(true);
		rdBtnTotalPrice.setFont(new Font("���� ���", Font.PLAIN, 20));
		rdBtnTotalPrice.setBounds(800, 41, 105, 23);
		add(rdBtnTotalPrice);

		rdBtnNetIncome = new JRadioButton("������");
		rdBtnNetIncome.setFont(new Font("���� ���", Font.PLAIN, 20));
		rdBtnNetIncome.setBounds(909, 41, 85, 23);
		add(rdBtnNetIncome);

		rdBtnCash = new JRadioButton("����");
		rdBtnCash.setFont(new Font("���� ���", Font.PLAIN, 20));
		rdBtnCash.setBounds(998, 41, 65, 23);
		add(rdBtnCash);

		rdBtnCard = new JRadioButton("ī��");
		rdBtnCard.setFont(new Font("���� ���", Font.PLAIN, 20));
		rdBtnCard.setBounds(1067, 41, 65, 23);
		add(rdBtnCard);

		// ��ư �׷쿡 ������ư ���
		bgp.add(rdBtnTotalPrice);
		bgp.add(rdBtnCash);
		bgp.add(rdBtnNetIncome);
		bgp.add(rdBtnCard);

		// #�̺�Ʈ ���#
		vms = new ViewStatMonthService(this);
		btnSearch.addActionListener(vms);
		
		rdBtnTotalPrice.addItemListener(vms);
		rdBtnNetIncome.addItemListener(vms);
		rdBtnCash.addItemListener(vms);
		rdBtnCard.addItemListener(vms);

	}
}
