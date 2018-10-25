package com.sezioo.permission.beans;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

public class PageQuery {

	@Getter
	@Setter
	@Min(value = 1,message="当前页码不合法")
	private int pageNo;
	
	@Getter
	@Setter
	@Min(value = 1,message="每页展示数量不合法")
	private int pageSize;
	
	@Setter
	private int offset;
	
	public int getOffset() {
		return (pageNo - 1) * pageSize;
	}
}
