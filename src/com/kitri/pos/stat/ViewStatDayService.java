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

	private Vector<PosDto> results; // 조건에 따른 검색 결과 (그래프용)

	private String statType = "매출합계"; // 라디오버튼 값 (디폴트는 "매출합계")

	private ViewStatDay vd;

	// [생성자]
	public ViewStatDayService(ViewStatDay vd) {
		this.vd = vd;

		setChart(statType, results);
	}

	// [ActionListener override/조회버튼]
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();

		if (ob == vd.btnSearch) {
			search();
		}
	}

	// [ItemListener override/라디오버튼]
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object ob = e.getSource();
		JRadioButton rb = (JRadioButton) ob;
		statType = rb.getText();

		// 선택된 라디오 버튼의 이름 비교 후, 맞는 차트 설정
		if (statType.equals("매출합계")) {
			setChart(statType, results); // 차트 생성 & pShowGraph패널의 카드 레이아웃으로 show()
		} else if (statType.equals("순매출")) {
			setChart(statType, results);
		} else if (statType.equals("현금")) {
			setChart(statType, results);
		} else if (statType.equals("카드")) {
			setChart(statType, results);
		}

	}

	// [기능 메소드]
	// <조회 버튼 클릭> 이벤트
	public void search() {

		// 테이블 행 화면 리셋
		StatDao.clearRows(vd.tmodel.getRowCount(), vd.tmodel);

		// 콤보박스의 값을 가져옴
		// (년도, 월, 일)
		String year = vd.comboYear.getItemAt(vd.comboYear.getSelectedIndex()).toString();
		String month = vd.comboMonth.getItemAt(vd.comboMonth.getSelectedIndex()).toString();
		String day = vd.comboDay.getItemAt(vd.comboDay.getSelectedIndex()).toString();
		if (Integer.parseInt(day) < 10) {
			day = "0".concat(day);
		}

		StatDao statDao = new StatDao(); // Dao 객체
		Vector<String> rows = new Vector<String>(); // 행

		results = new Vector<PosDto>(); // 그래프용 백터

		// select 결과 저장
		PosDto result = statDao.findDaySell(year, month, day); // DB select 결과 저장 변수
		results.add(result);

		if (result.getSellDate() == null) { // 조회 결과 없으면, 알림창 날림
			JOptionPane.showMessageDialog(null, "조회할 데이터가 없습니다.");
		} else { // 조회 결과 있으면, 결과 보이기

			// 테이블 행 세팅
			rows.addElement(result.getSellDate());
			rows.addElement(Integer.toString(result.getStatTotalPrice()));
			rows.addElement(Integer.toString(result.getTotalTax()));
			rows.addElement(Integer.toString(result.getCashPrice()));
			rows.addElement(Integer.toString(result.getCardPrice()));
			rows.addElement(Integer.toString(result.getCustomerCount()));

			vd.tmodel.addRow(rows);

			// 결과 테이블 띄우기
			vd.spShowTable.setViewportView(vd.tableResult);

			// 디폴트 그래프인 매출합계도 같이 띄워줌
			setChart(statType, results);
		}

	}

	// <라디오버튼 값에 따른 막대 그래프 세팅> 이벤트
	// option : 1 - 매출합계 / 2 - 순매출 / 3 - 현금매출 / 4 - 카드매출
	public void setChart(String type, Vector<PosDto> results) {

		// #차트 생성#
		// [데이터 생성]
		DefaultCategoryDataset dataset;

		// [데이터 세팅]
		// type(통계 분류)에 따라 다르게 세팅됨
		dataset = ViewStatYearService.getGraphDataset(type, results);

		// [렌더링]
		// 렌더링 생성
		final BarRenderer renderer = new BarRenderer();

		// 공통 옵션 정의
		final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
		final ItemLabelPosition p_center = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
		final ItemLabelPosition p_below = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT);
		Font f = new Font("맑은고딕", Font.BOLD, 16);
		Font axisF = new Font("맑은고딕", Font.BOLD, 16);

		// 렌더링 세팅
		renderer.setBaseItemLabelGenerator(generator);
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBasePositiveItemLabelPosition(p_center);
		renderer.setBaseItemLabelFont(f);
		renderer.setBaseItemLabelPaint(Color.white); // 막대 글씨 색
		renderer.setSeriesPaint(0, new Color(152, 84, 147)); // 막대 색

		// [plot]
		// plot 생성
		final CategoryPlot plot = new CategoryPlot();

		// plot 에 데이터 적재
		plot.setDataset(dataset);
		plot.setRenderer(renderer);

		// plot 기본 설정
		plot.setOrientation(PlotOrientation.VERTICAL); // 그래프 표시 방향
		plot.setRangeGridlinesVisible(true); // X축 가이드 라인 표시여부
		plot.setDomainGridlinesVisible(true); // Y축 가이드 라인 표시여부

		// X축 세팅
		plot.setDomainAxis(new CategoryAxis()); // X축 종류 설정
		plot.getDomainAxis().setTickLabelFont(axisF); // X축 눈금라벨 폰트 조정
		plot.getDomainAxis().setTickLabelPaint(Color.WHITE); // X축 눈금라벨 폰트 색상 조정
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD); // 카테고리 라벨 위치 조정

		// Y축 세팅
		plot.setRangeAxis(new NumberAxis()); // Y축 종류 설정
		plot.getRangeAxis().setTickLabelFont(axisF); // Y축 눈금라벨 폰트 조정
		plot.getRangeAxis().setTickLabelPaint(Color.WHITE); // X축 눈금라벨 폰트 색상 조정

		// 세팅된 plot을 바탕으로 chart 생성
		JFreeChart chart = new JFreeChart(plot);
		chart.setBackgroundPaint(Color.DARK_GRAY);
		chart.getPlot().setBackgroundPaint(Color.DARK_GRAY);

		// #생성된 차트로 차트 패널 생성#
		ChartPanel chartp = new ChartPanel(chart);
		chartp.setSize(1120, 368); // 사이즈 지정 필수!

		switch (type) {
		case "매출합계":
			vd.pShowGraph.add("graphTotal", chartp);
			vd.graphCard.show(vd.pShowGraph, "graphTotal");
			break;
		case "순매출":
			renderer.setSeriesPaint(0, new Color(75, 84, 147));
			vd.pShowGraph.add("graphRealTotal", chartp);
			vd.graphCard.show(vd.pShowGraph, "graphRealTotal");
			break;
		case "현금":
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
