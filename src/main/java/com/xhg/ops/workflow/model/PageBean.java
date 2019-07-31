package com.xhg.ops.workflow.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 分頁对象
 */
@ToString
@Data
public class PageBean<T> {
	private Integer pageNo = 1; // 当前页
	private Integer pageSize = 10; // 每页大小
	private Long totalCount = 0L; // 查询总数

	private List<T> list;

	// 获取分页的开始下标
	public Integer getStartIndex() {
		if (pageNo <= 1) {
			pageNo = 1;
		}
		return (pageNo - 1) * this.pageSize;
	}

	public PageBean(Long totalCount, List<T> list) {
		this.totalCount = totalCount;
		this.list = list;
	}

	public PageBean() {

	}

	public Integer getPageNo() {
		if (pageNo <= 1) {
			pageNo = 1;
		}
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
}
