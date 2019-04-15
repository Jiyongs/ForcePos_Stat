package com.kitri.pos.stat;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.*;

/*
 	ViewStatYear : ������ ��� �г�
 */

public class ViewStatYear extends JPanel {

	JComboBox comboStartYear;
	JComboBox comboEndYear;
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
	
	ViewStatYearService vys;

	public ViewStatYear() {
		setLayout(null);
		setSize(new Dimension(1144, 535));

		// #�˻����� �г�#
		JPanel pSetSearch = new JPanel();
		pSetSearch.setLayout(null);
		pSetSearch.setBounds(12, 27, 780, 37);
		add(pSetSearch);

		JLabel lbShowDate = new JLabel("��ȸ�⵵ :");
		lbShowDate.setFont(new Font("���� ���", Font.PLAIN, 20));
		lbShowDate.setBounds(12, 0, 101, 37);
		pSetSearch.add(lbShowDate);

		btnSearch = new JButton("��ȸ");
		btnSearch.setBorder(UIManager.getBorder("Button.border"));
		btnSearch.setFont(new Font("���� ���", Font.BOLD, 20));
		btnSearch.setBounds(379, 0, 101, 37);
		pSetSearch.add(btnSearch);
		
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
		comboStartYear = new JComboBox<Integer>(YearValues); //�������� �ִ� �޺��ڽ�
		comboStartYear.setBounds(113, 0, 114, 37);
		pSetSearch.add(comboStartYear);
		
		JLabel label = new JLabel("-");
		label.setFont(new Font("���� ���", Font.PLAIN, 20));
		label.setBounds(236, 2, 15, 32);
		pSetSearch.add(label);
		
		// ���⵵ �޺��ڽ�
		comboEndYear = new JComboBox<Integer>(YearValues); //�������� �ִ� �޺��ڽ�
		comboEndYear.setBounds(251, 0, 114, 37);
		pSetSearch.add(comboEndYear);

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
		pShowGraph.setVisible(false);
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
		bgp.add(rdBtnNetIncome);
		bgp.add(rdBtnCash);
		bgp.add(rdBtnCard);

		// #�̺�Ʈ ���#
		vys = new ViewStatYearService(this);
		
		btnSearch.addActionListener(vys);
		rdBtnTotalPrice.addItemListener(vys);
		rdBtnNetIncome.addItemListener(vys);
		rdBtnCash.addItemListener(vys);
		rdBtnCard.addItemListener(vys);
		
		

	}
}
