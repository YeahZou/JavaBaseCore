package com.yeah.java.employment;

public class WorkOrderVo {

	private String id;
	private String reportDate;
	private Integer expiryTime;
	private String finishDate;
	
	public WorkOrderVo(String id, String reportDate, Integer expiryTime) {
		this.id = id;
		this.reportDate = reportDate;
		this.expiryTime = expiryTime;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public Integer getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(Integer expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
}
