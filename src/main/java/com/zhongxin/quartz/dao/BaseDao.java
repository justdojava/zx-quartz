package com.zhongxin.quartz.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

public class BaseDao {
	
	@Resource
	private SqlSessionTemplate sessionTemplate;
	
	protected SqlSessionTemplate getTemplate(){
		return this.sessionTemplate;
	}
	
	public int getPageNum(int pageNum,int pageSize){
		int num = pageSize*(pageNum-1);
		return num;
	}

}
