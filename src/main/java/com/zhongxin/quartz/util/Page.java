package com.zhongxin.quartz.util;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @Description: 分页数据包装类
 * @version v1.0
 */
public class Page implements Serializable{
	private static final long serialVersionUID = 1L;
	private int currentPage=1;  //当前页数
	private int totalPage;		//总页数
	private int totalNumber;    //总记录数
	private List list;			//数据集
	private PageParam param;
	private int number = 10;	//每页多少条
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	public Page() {
		super();
	}
	//根据总记录数自动计算出总页数
	public Page(int totalNumber) {
		super();
		this.totalNumber = totalNumber;
		 totalPage = totalNumber % number == 0 ? totalNumber
				/ number : totalNumber / number+ 1;
	}
	public Page(int totalNumber,int num){
		super();
		this.totalNumber = totalNumber;
		 totalPage = totalNumber % num == 0 ? totalNumber
				/ num : totalNumber / num+ 1;
	}
	public Page(int totalNumber, PageParam baseParam, int pageSize) {
		super();
		this.param = baseParam;
		if (totalNumber > 0) {
			this.totalNumber = totalNumber;
			totalPage = totalNumber % pageSize == 0 ? totalNumber/ pageSize : totalNumber / pageSize + 1;
			this.currentPage = baseParam.getCurrentPage();
			Integer beginLine = (currentPage - 1) * pageSize;
			int t = beginLine + pageSize;
			Integer endLine = t > totalNumber ? totalNumber : t;
			this.param.setBeginLine(beginLine);
			this.param.setEndLine(endLine);
		} else {
			this.param.setBeginLine(0);
			this.param.setEndLine(0);
		}
	}
	//根据总记录数自动计算出总页数
	/*public Page(int totalNumber,int currentPage) {
		super();
		this.totalNumber = totalNumber;
		 totalPage = totalNumber % number == 0 ? totalNumber
				/ number : totalNumber / number+ 1;
		 this.currentPage = currentPage;
	}*/
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public PageParam getParam() {
		return param;
	}
	public void setParam(PageParam param) {
		this.param = param;
	}
	/**
	 * 获取查询数据时分页的起始值
	 * @return
	 */
	public int getPageBeginData()
	{
		return (this.getCurrentPage()-1)*this.getNumber();
	}
	
	/**
	 * 获取查询数据时分页的结束值
	 * @return
	 */
	public int getPageEndData()
	{
		return ((this.getCurrentPage()-1)*this.getNumber()) + this.getNumber();
	}
		
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
