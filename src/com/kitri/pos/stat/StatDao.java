package com.kitri.pos.stat;

import java.sql.*;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.kitri.pos.stat.PosDto;
import com.kitri.pos.db.DBManager;

/*
	StatDao : ��� DB ���� �޼ҵ� ����
*/

public class StatDao {

	// DB����� ����(����)�� ���� ��ü
	Connection conn = null;

	// �������� ����ϴ� state��ü
	PreparedStatement ps = null;

	Statement st = null;
	ResultSet rs = null;

	// ������ ��� (1��) ���� PosDto ��ü
	PosDto posDto = null;

	// [�޼ҵ�]

	//////////////////////////////// ��ǰ�� ��� ////////////////////////////////
	// <��ǰ�� ���⳻�� select> �޼ҵ�
	// : �Һз�, ��, �� �Է¹޾� �����հ� ��ŷ������ ��ȸ
	public Vector<PosDto> findProductSell(String minor_level, String year, String month) {

		// ������ ��� (���� ��) ���� PosDto ��ü
		Vector<PosDto> list = new Vector<PosDto>();

		int date = Integer.parseInt(year.concat(month).concat("01"));
		try {
			// DB ����
			conn = DBManager.getConnection();

			// ������ ����
			String query = "select row_number() over(order by p.price*v.sc desc) as �������, p.product_code as ��ǰ�ڵ�, p.minor_level as ��ǰ�з�, p.product_name as ��ǰ��, p.price as �ǸŰ�, p.purchase as ���԰�, v.sc as �Ǹż���,  p.price*v.sc as �����հ�, p.company as ������\r\n"
					+ "from products p, (select product_code, sum(sell_count) sc\r\n"
					+ "                                 from history_detail\r\n"
					+ "                                 where to_char(sell_date,'yyyymm') = to_char(to_date(?),'yyyymm')\r\n"
					+ "                                 group by product_code) v\r\n"
					+ "where p.product_code = v.product_code\r\n" + "and p.minor_level = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, date);
			ps.setString(2, minor_level);

			// ������ ����
			rs = ps.executeQuery();

			// ��� ����
			while (rs.next()) {
				posDto = new PosDto();

				posDto.setRanking(rs.getInt(1));
				posDto.setProductCode(rs.getString(2));
				posDto.setMinorLevel(rs.getString(3));
				posDto.setProductName(rs.getString(4));
				posDto.setPrice(rs.getInt(5));
				posDto.setPurchase(rs.getInt(6));
				posDto.setSellCount(rs.getInt(7));
				posDto.setStatTotalPrice(rs.getInt(8));
				posDto.setCompany(rs.getString(9));

				list.add(posDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBManager.dbClose(rs, ps, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// ��� ����
		return list;

	}

	// <��ǰ�� ���⳻�� BEST 5 select> �޼ҵ�
	// : �Һз�, ��, �� �Է¹޾� �����հ� ��ŷ ���� 5������ ��ȸ
	public Vector<PosDto> findProductSellBestFive(String minor_level, String year, String month) {
		// ������ ��� (���� ��) ���� PosDto ��ü
		Vector<PosDto> list = new Vector<PosDto>();

		// String���� �Է��� ��¥�� '���' ���ļ� int�� ��ȯ
		int date = Integer.parseInt(year.concat(month).concat("01"));
		try {
			// DB ����
			conn = DBManager.getConnection();

			// ������ ����
			String query = "select vr.�������, vr.��ǰ�ڵ�, vr.��ǰ�з�, vr.��ǰ��, vr.�ǸŰ�, vr.���԰�, vr.�Ǹż���, vr.�����հ�, vr.������\r\n"
					+ "from (select row_number() over(order by p.price*v.sc desc) as �������, p.product_code as ��ǰ�ڵ�, p.minor_level as ��ǰ�з�, p.product_name as ��ǰ��, p.price as �ǸŰ�, p.purchase as ���԰�, v.sc as �Ǹż���,  p.price*v.sc as �����հ�, p.company as ������\r\n"
					+ "        from products p, (select product_code, sum(sell_count) sc\r\n"
					+ "                                 from history_detail\r\n"
					+ "                                 where to_char(sell_date,'yyyymm') = to_char(to_date(?),'yyyymm')\r\n"
					+ "                                 group by product_code) v\r\n"
					+ "        where p.product_code = v.product_code\r\n" + "        and p.minor_level = ?) vr\r\n"
					+ "where ������� < 6";
			ps = conn.prepareStatement(query);
			ps.setInt(1, date);
			ps.setString(2, minor_level);

			// ������ ����
			rs = ps.executeQuery();

			// ��� ����
			while (rs.next()) {
				posDto = new PosDto();

				posDto.setRanking(rs.getInt(1));
				posDto.setProductCode(rs.getString(2));
				posDto.setMinorLevel(rs.getString(3));
				posDto.setProductName(rs.getString(4));
				posDto.setPrice(rs.getInt(5));
				posDto.setPurchase(rs.getInt(6));
				posDto.setSellCount(rs.getInt(7));
				posDto.setStatTotalPrice(rs.getInt(8));
				posDto.setCompany(rs.getString(9));

				list.add(posDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// DB ���� ����
				DBManager.dbClose(rs, ps, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// ��� ����
		return list;
	}

	//////////////////////////////// �Ⱓ�� ��� ////////////////////////////////
	// <������ ���⳻�� select> �޼ҵ�
	// : �� �Է¹޾� ��ȸ
	
	public Vector<PosDto> findYearSell(int startYear, int endYear) {
		// ������ ��� (���� ��) ���� PosDto ��ü
		Vector<PosDto> list = new Vector<PosDto>();

		try {
			// DB ����
			conn = DBManager.getConnection();

			// ������ ����
			String query = "select h.hy as ����⵵, sum(h.htp) as �����հ�, sum(h.hb) as �ΰ���, sum(h.hcp) as ���ݸ���, sum(h.hcdp) as ī�����, count(*) as ����\r\n"
					+ "from (select total_price htp, to_char(sell_date, 'yyyy') hy, total_price*0.1 hb, cash_price hcp, card_price hcdp\r\n"
					+ "        from history) h\r\n" + "where hy between ? and ?\r\n" + "group by hy\r\n"
					+ "order by hy";
			ps = conn.prepareStatement(query);
			ps.setInt(1, startYear);
			ps.setInt(2, endYear);

			// ������ ����
			rs = ps.executeQuery();

			// ��� ����
			while (rs.next()) {
				posDto = new PosDto();

				posDto.setSellDate(Integer.toString(rs.getInt(1)));
				posDto.setStatTotalPrice(rs.getInt(2));
				posDto.setTotalTax(rs.getInt(3));
				posDto.setCashPrice(rs.getInt(4));
				posDto.setCardPrice(rs.getInt(5));
				posDto.setCustomerCount(rs.getInt(6));

				list.add(posDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// DB ���� ����
				DBManager.dbClose(rs, ps, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// ��� ����
		return list;
	}

	// <���� ���⳻�� select> �޼ҵ�
	// : �� �Է¹޾� ��ȸ
	public Vector<PosDto> findMonthSell(int year) {
		// ������ ��� (���� ��) ���� PosDto ��ü
		Vector<PosDto> list = new Vector<PosDto>();

		try {
			// DB ����
			conn = DBManager.getConnection();

			// ������ ����
			String query = "select h.hy as ������, sum(h.htp) as �����հ�, sum(h.hb) as �ΰ���, sum(h.hcp) as ���ݸ���, sum(h.hcdp) as ī�����, count(*) as ����\r\n"
					+ "from (select total_price htp, to_char(sell_date, 'yyyymm') hy, total_price*0.1 hb, cash_price hcp, card_price hcdp\r\n"
					+ "        from history\r\n" + "        where to_char(sell_date,'yyyy')=?) h\r\n"
					+ "group by hy\r\n" + "order by hy";
			ps = conn.prepareStatement(query);
			ps.setInt(1, year);

			// ������ ����
			rs = ps.executeQuery();

			// ��� ����
			while (rs.next()) {
				posDto = new PosDto();

				posDto.setSellDate(Integer.toString(rs.getInt(1)));
				posDto.setStatTotalPrice(rs.getInt(2));
				posDto.setTotalTax(rs.getInt(3));
				posDto.setCashPrice(rs.getInt(4));
				posDto.setCardPrice(rs.getInt(5));
				posDto.setCustomerCount(rs.getInt(6));

				list.add(posDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// DB ���� ����
				DBManager.dbClose(rs, ps, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// ��� ����
		return list;
	}

	// <�Ϻ� ���⳻�� select> �޼ҵ�
	// : ��, ��, �� �Է¹޾� ��ȸ
	public PosDto findDaySell(String year, String month, String day) {
	
		// String���� �Է��� ��¥�� '�����' ���ļ� int�� ��ȯ
		int date = Integer.parseInt(year.concat(month).concat(day));
		try {
			// DB ����
			conn = DBManager.getConnection();    
			posDto = new PosDto();
			// ������ ����
			String query = "select h.hy as ��������, sum(h.htp) as �����հ�, sum(h.hb) as �ΰ���, sum(h.hcp) as ���ݸ���, sum(h.hcdp) as ī�����, count(*) as ����\r\n" + 
					"from (select total_price htp, to_char(sell_date, 'yyyymmdd') hy, total_price*0.1 hb, cash_price hcp, card_price hcdp\r\n" + 
					"        from history\r\n" + 
					"        where to_char(sell_date,'yyyymmdd')= to_date(?)) h\r\n" + 
					"group by hy\r\n" + 
					"order by hy";                              
			ps = conn.prepareStatement(query);
			ps.setInt(1, date);
						
			// ������ ����
			rs = ps.executeQuery();                    
			
			// ��� ����
			while(rs.next()) {					
				posDto.setSellDate(Integer.toString(rs.getInt(1)));
				posDto.setStatTotalPrice(rs.getInt(2));
				posDto.setTotalTax(rs.getInt(3));
				posDto.setCashPrice(rs.getInt(4));
				posDto.setCardPrice(rs.getInt(5));
				posDto.setCustomerCount(rs.getInt(6));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// DB ���� ����
				DBManager.dbClose(rs, ps, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// ��� ����
		return posDto;
	}
	
	// ���̺� �� ��� ����� (ȭ��ܿ�����)
	public static void clearRows(int rowSize, DefaultTableModel dtm) {
		if (rowSize > 0) {
			for (int i = rowSize - 1; i >= 0; i--) {
				dtm.removeRow(i);
			}
		}
	}
	
}
