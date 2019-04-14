package com.kitri.pos.stat;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.TextAnchor;

import com.kitri.pos.stat.PosDto;

public class ViewStatYearService implements ActionListener, ItemListener {

	private ViewStatYear vy;

	private Vector<PosDto> results;  //���ǿ� ���� �˻� ���
	
	private String statType = "�����հ�";        //������ư �� (����Ʈ�� "�����հ�")

	// [������]
	public ViewStatYearService(ViewStatYear vy) {
		this.vy = vy;
		
		setChart(statType, results);
	}

	// [ActionListener override/��ȸ��ư]
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();

		if (ob == vy.btnSearch) {
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
		StatDao.clearRows(vy.tmodel.getRowCount(), vy.tmodel);

		// �޺��ڽ��� ���� ������
		// (���۳⵵)
		int startYear = (int) vy.comboStartYear.getItemAt(vy.comboStartYear.getSelectedIndex());
		int endYear = (int) vy.comboEndYear.getItemAt(vy.comboEndYear.getSelectedIndex());

		StatDao statDao = new StatDao(); // Dao ��ü
		results = new Vector<PosDto>(); // ���� ���

		// select ��� ����
		results = statDao.findYearSell(startYear, endYear); // DB select ��� ���� ����

		if (results.isEmpty()) { // ��ȸ ��� ������, �˸�â ����
			JOptionPane.showMessageDialog(null, "��ȸ�� �����Ͱ� �����ϴ�.");
		} else { // ��ȸ ��� ������, ��� ���̱�

			// ���̺� �� ����
			int size = results.size();

			for (int i = 0; i < size; i++) {
				Vector<String> rows = new Vector<String>(); // ��

				rows.addElement(results.get(i).getSellDate());
				rows.addElement(Integer.toString(results.get(i).getStatTotalPrice()));
				rows.addElement(Integer.toString(results.get(i).getTotalTax()));
				rows.addElement(Integer.toString(results.get(i).getCashPrice()));
				rows.addElement(Integer.toString(results.get(i).getCardPrice()));
				rows.addElement(Integer.toString(results.get(i).getCustomerCount()));
				vy.tmodel.addRow(rows);
			}

			// ��� ���̺� ����
			vy.spShowTable.setViewportView(vy.tableResult);

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
		dataset = getGraphDataset(type, results);

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
		renderer.setBaseItemLabelPaint(Color.white);         //���� �۾� ��
		renderer.setSeriesPaint(0, new Color(152, 84, 147));  //���� ��

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
		chart.getPlot().setBackgroundPaint(Color.DARK_GRAY );

		// #������ ��Ʈ�� ��Ʈ �г� ����#
		ChartPanel chartp = new ChartPanel(chart);
		chartp.setSize(554, 451); // ������ ���� �ʼ�!

		switch (type) {
		case "�����հ�":
			vy.pShowGraph.add("graphTotal", chartp);
			vy.graphCard.show(vy.pShowGraph, "graphTotal");
			break;
		case "������":
			renderer.setSeriesPaint(0, new Color(75, 84, 147));
			vy.pShowGraph.add("graphRealTotal", chartp);
			vy.graphCard.show(vy.pShowGraph, "graphRealTotal");
			break;
		case "����":
			renderer.setSeriesPaint(0, new Color(217, 79, 99));
			vy.pShowGraph.add("graphCash", chartp);
			vy.graphCard.show(vy.pShowGraph, "graphCash");
			break;
		default:
			renderer.setSeriesPaint(0, new Color(80, 107, 102));
			vy.pShowGraph.add("graphCard", chartp);
			vy.graphCard.show(vy.pShowGraph, "graphCard");
		}
	
		vy.pShowGraph.setVisible(true);
		
	}

	// <Ÿ��(�����հ�, ������, ����, ī��)�� ���� �׷��� �����ͼ� ����> �޼ҵ�
	public static DefaultCategoryDataset getGraphDataset(String type, Vector<PosDto> results) {

		DefaultCategoryDataset dataset = null;

		if (results != null) { // ��ȸ ��� ���� ���� �׷��� �� ����
			System.out.println("�׷��� �� ����!!!!!");
			Vector<String> date = new Vector<String>(); // ��¥ (�� ��)
			Vector<Integer> values = new Vector<Integer>(); // �з��� �� (�� ��)

			dataset = new DefaultCategoryDataset();

			int size = results.size();

			switch (type) {
			case "�����հ�":
				for (int i = 0; i < size; i++) {
					date.addElement(results.get(i).getSellDate()); // 20190407
					values.addElement(results.get(i).getStatTotalPrice()); // �����հ� (int)

					// ��, ����, ī�װ� ����
					dataset.addValue(values.get(i), type, date.get(i));
				}
				break;
			case "������":
				for (int i = 0; i < size; i++) {
					date.addElement(results.get(i).getSellDate()); // 2019
					values.addElement((int) (results.get(i).getStatTotalPrice() * 0.4)); // ������(�����հ�*0.4) (int)

					dataset.addValue(values.get(i), type, date.get(i));
				}
				break;
			case "����":
				for (int i = 0; i < size; i++) {
					date.addElement(results.get(i).getSellDate()); // 2019
					values.addElement(results.get(i).getCashPrice()); // ���ݸ��� (int)

					dataset.addValue(values.get(i), type, date.get(i));
				}
				break;
			default:
				for (int i = 0; i < size; i++) {
					date.addElement(results.get(i).getSellDate()); // 2019
					values.addElement(results.get(i).getCardPrice()); // ī����� (int)

					dataset.addValue(values.get(i), type, date.get(i));
				}
			}

		}
		return dataset;
	}

}
