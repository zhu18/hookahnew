package com.jusfoun.hookah.console.server.pay.schedule;


public class TradeKey {

	private String orderSn;
	private String money;
	/**收入:1；支出:0*/
	private Boolean incomeFlag;
	
	public TradeKey() {
	}

	
	public TradeKey(String orderSn, String money, Boolean incomeFlag) {
		this.orderSn = orderSn;
		this.money = money;
		this.incomeFlag = incomeFlag;
	}


	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public Boolean getIncomeFlag() {
		return incomeFlag;
	}
	public void setIncomeFlag(Boolean incomeFlag) {
		this.incomeFlag = incomeFlag;
	}


	@Override
	public String toString() {
		return "TradeKey [orderSn=" + orderSn + ", money=" + money + ", incomeFlag="+incomeFlag+"]";
	}
	
//	public static void main(String[] args) {
//		TreeMap<String, TradeKey> map1 = new TreeMap<String, TradeKey>();
//		TradeKey k1 = new TradeKey();
//		k1.setOrderSn("333");
//		TradeKey k2 = new TradeKey();
//		k2.setOrderSn("666");
//		map1.put("6", k2);
//		map1.put("3", k1);
//		
//		TreeMap<String, TradeKey> map2 = new TreeMap<String, TradeKey>();
//		TradeKey k3 = new TradeKey();
//		k3.setOrderSn("333");
//		TradeKey k4 = new TradeKey();
//		k4.setOrderSn("444");
//		map2.put("4", k4);
//		map2.put("3", k3);
//		
//		Iterator<String> it = map1.keySet().iterator();
//		while(it.hasNext()){
//			String k = it.next();
//			if(map2.containsKey(k)){
//				System.out.println(map1.get(k));
//			}
//		}
//	}
}
