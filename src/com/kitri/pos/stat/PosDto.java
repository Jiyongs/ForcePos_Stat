package com.kitri.pos.stat;

public class PosDto {

	// �ʵ� ����
	private String company; // ������
	private String productCode; // ��ǰ�ڵ�
	private String productName; // ��ǰ�̸�
	private String levelCode; // �з��ڵ�
	private String minorLevel; // �Һз�
	private int price; // ����
	private int purchase; // ���԰�
	private int volume; // ����
	// ===================================//
	private String sellId; // �Ǹž��̵�
	private String sellDate; // �Ǹ�����
	private int sellCount; // �Ǹż���
	private int comsCalc; // ���ݰ����ݾ�
	private int cashPrice;
	private String payment;
	private int cardPrice; // ī������ݾ�
	private String inDate; // �԰�¥
	private int totalPrice;
	// =====================================//
	private int ranking; // �������
	private int statTotalPrice; // �����հ�
	private int totalTax; // �ΰ����հ�
	private int customerCount; // ����

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getMinorLevel() {
		return minorLevel;
	}

	public void setMinorLevel(String minorLevel) {
		this.minorLevel = minorLevel;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPurchase() {
		return purchase;
	}

	public void setPurchase(int purchase) {
		this.purchase = purchase;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String getSellId() {
		return sellId;
	}

	public void setSellId(String sellId) {
		this.sellId = sellId;
	}

	public String getSellDate() {
		return sellDate;
	}

	public void setSellDate(String sellDate) {
		this.sellDate = sellDate;
	}

	public int getSellCount() {
		return sellCount;
	}

	public void setSellCount(int sellCount) {
		this.sellCount = sellCount;
	}

	public int getComsCalc() {
		return comsCalc;
	}

	public void setComsCalc(int comsCalc) {
		this.comsCalc = comsCalc;
	}

	public int getCashPrice() {
		return cashPrice;
	}

	public void setCashPrice(int cashPrice) {
		this.cashPrice = cashPrice;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public int getCardPrice() {
		return cardPrice;
	}

	public void setCardPrice(int cardPrice) {
		this.cardPrice = cardPrice;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getStatTotalPrice() {
		return statTotalPrice;
	}

	public void setStatTotalPrice(int statTotalPrice) {
		this.statTotalPrice = statTotalPrice;
	}

	public int getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(int totalTax) {
		this.totalTax = totalTax;
	}

	public int getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(int customerCount) {
		this.customerCount = customerCount;
	}
}
