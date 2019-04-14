package com.kitri.pos.stat;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import com.kitri.pos.stat.PosDto;

public class ViewStatDayService implements ActionListener, ItemListener {

	private Vector<PosDto> results; // ���ǿ� ���� �˻� ��� (�׷�����)

	private String statType = "�����հ�"; // ������ư �� (����Ʈ�� "�����հ�")

	private ViewStatDay vd;

	// [������]
	public ViewStatDayService(ViewStatDay vd) {
		this.vd = vd;

		setChart(statType, results);
	}

	// [ActionListener override/��ȸ��ư]
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();

		if (ob == vd.btnSearch) {
			search();
		}
	}

	// [ItemListener override/������ư]
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object ob = e.getSource();
		JRadioButton rb = (JRadioButton) ob;
		statType = rb.getText();

		// ���õ� ���� ��ư�� �̸� �� ��, �´� ��Ʈ ����
		if (statType.equals("�����հ�")) {
			setChart(statType, results); // ��Ʈ ���� & pShowGraph�г��� ī�� ���̾ƿ����� show()
		} else if (statType.equals("������")) {
			setChart(statType, results);
		} else if (statType.equals("����")) {
			setChart(statType, results);
		} else if (statType.equals("ī��")) {
			setChart(statType, results);
		}

	}

	// [��� �޼ҵ�]
	// <��ȸ ��ư Ŭ��> �̺�Ʈ
	public void search() {

		// ���̺� �� ȭ�� ����
		StatDao.clearRows(vd.tmodel.getRowCount(), vd.tmodel);

		// �޺��ڽ��� ���� ������
		// (�⵵, ��, ��)
		String year = vd.comboYear.getItemAt(vd.comboYear.getSelectedIndex()).toString();
		String month = vd.comboMonth.getItemAt(vd.comboMonth.getSelectedIndex()).toString();
		String day = vd.comboDay.getItemAt(vd.comboDay.getSelectedIndex()).toString();
		if (Integer.parseInt(day) < 10) {
			day = "0".concat(day);
		}

		StatDao statDao = new StatDao(); // Dao ��ü
		Vector<String> rows = new Vector<String>(); // ��

		results = new Vector<PosDto>(); // �׷����� ����

		// select ��� ����
		PosDto result = statDao.findDaySell(year, month, day); // DB select ��� ���� ����
		results.add(result);

		if (result.getSellDate() == null) { // ��ȸ ��� ������, �˸�â ����
			JOptionPane.showMessageDialog(null, "��ȸ�� �����Ͱ� �����ϴ�.");
		} else { // ��ȸ ��� ������, ��� ���̱�

			// ���̺� �� ����
			rows.addElement(result.getSellDate());
			rows.addElement(Integer.toString(result.getStatTotalPrice()));
			rows.addElement(Integer.toString(result.getTotalTax()));
			rows.addElement(Integer.toString(result.getCashPrice()));
			rows.addElement(Integer.toString(result.getCardPrice()));
			rows.addElement(Integer.toString(result.getCustomerCount()));

			vd.tmodel.addRow(rows);

			// ��� ���̺� ����
			vd.spShowTable.setViewportView(vd.tableResult);

			// ����Ʈ �׷����� �����հ赵 ���� �����
			setChart(statType, results);
		}

	}

	// <������ư ���� ���� ���� �׷��� ����> �̺�Ʈ
	// option : 1 - �����հ� / 2 - ������ / 3 - ���ݸ��� / 4 - ī�����
	public void setChart(String type, Vector<PosDto> results) {

		// #��Ʈ ����#
		// [������ ����]
		DefaultCategoryDataset dataset;

		// [������ ����]
		// type(��� �з�)�� ���� �ٸ��� ���õ�
		dataset = ViewStatYearService.getGraphDataset(type, results);

		// [������]
		// ������ ����
		final BarRenderer renderer = new BarRenderer();

		// ���� �ɼ� ����
		final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
		final ItemLabelPosition p_center = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
		final ItemLabelPosition p_below = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT);
		Font f = new Font("�������", Font.BOLD, 16);
		Font axisF = new Font("�������", Font.BOLD, 16);

		// ������ ����
		renderer.setBaseItemLabelGenerator(generator);
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBasePositiveItemLabelPosition(p_center);
		renderer.setBaseItemLabelFont(f);
		renderer.setBaseItemLabelPaint(Color.white); // ���� �۾� ��
		renderer.setSeriesPaint(0, new Color(152, 84, 147)); // ���� ��

		// [plot]
		// plot ����
		final CategoryPlot plot = new CategoryPlot();

		// plot �� ������ ����
		plot.setDataset(dataset);
		plot.setRenderer(renderer);

		// plot �⺻ ����
		plot.setOrientation(PlotOrientation.VERTICAL); // �׷��� ǥ�� ����
		plot.setRangeGridlinesVisible(true); // X�� ���̵� ���� ǥ�ÿ���
		plot.setDomainGridlinesVisible(true); // Y�� ���̵� ���� ǥ�ÿ���

		// X�� ����
		plot.setDomainAxis(new CategoryAxis()); // X�� ���� ����
		plot.getDomainAxis().setTickLabelFont(axisF); // X�� ���ݶ� ��Ʈ ����
		plot.getDomainAxis().setTickLabelPaint(Color.WHITE); // X�� ���ݶ� ��Ʈ ���� ����
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD); // ī�װ� �� ��ġ ����

		// Y�� ����
		plot.setRangeAxis(new NumberAxis()); // Y�� ���� ����
		plot.getRangeAxis().setTickLabelFont(axisF); // Y�� ���ݶ� ��Ʈ ����
		plot.getRangeAxis().setTickLabelPaint(Color.WHITE); // X�� ���ݶ� ��Ʈ ���� ����

		// ���õ� plot�� �������� chart ����
		JFreeChart chart = new JFreeChart(plot);
		chart.setBackgroundPaint(Color.DARK_GRAY);
		chart.getPlot().setBackgroundPaint(Color.DARK_GRAY);

		// #������ ��Ʈ�� ��Ʈ �г� ����#
		ChartPanel chartp = new ChartPanel(chart);
		chartp.setSize(1120, 368); // ������ ���� �ʼ�!

		switch (type) {
		case "�����հ�":
			vd.pShowGraph.add("graphTotal", chartp);
			vd.graphCard.show(vd.pShowGraph, "graphTotal");
			break;
		case "������":
			renderer.setSeriesPaint(0, new Color(75, 84, 147));
			vd.pShowGraph.add("graphRealTotal", chartp);
			vd.graphCard.show(vd.pShowGraph, "graphRealTotal");
			break;
		case "����":
			renderer.setSeriesPaint(0, new Color(217, 79, 99));
			vd.pShowGraph.add("graphCash", chartp);
			vd.graphCard.show(vd.pShowGraph, "graphCash");
			break;
		default:
			renderer.setSeriesPaint(0, new Color(80, 107, 102));
			vd.pShowGraph.add("graphCard", chartp);
			vd.graphCard.show(vd.pShowGraph, "graphCard");
		}

		vd.pShowGraph.setVisible(true);

	}

}
