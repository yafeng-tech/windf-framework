package com.windf.core.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * 自动计算一些分页信息
 */
public class Page<T> extends PageParameter implements Serializable {
	public static final Integer DEFAULT_PAGE_SIZE = 10;

	private static final long serialVersionUID = 1L;

	private List<T> data;
	private Long total;
	private Long pageIndex;
	private Integer pageSize;

	public Page() {

	}

	public Page(Long pageIndex, Integer pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public Page(PageParameter pageParameter) {
		if (!pageParameter.needNotPage()) {
			this.pageIndex = pageParameter.getIndex();
			this.pageSize = pageParameter.getSize();
		}
	}

	public Long getStartIndex() {
		if (pageIndex == null || pageSize == null) {
			return null;
		}
		
		long startIndex = (pageIndex - 1) * pageSize;
		if (total != null) {
			startIndex = startIndex > total ? total : startIndex;
		}
		
		return startIndex;
	}

	public Long getEndIndex() {
		if (pageIndex == null || pageSize == null) {
			return null;
		}

		Long endIndex = this.getStartIndex() + pageSize;
		if (total != null) {
			endIndex = endIndex > total ? total : endIndex;
		}
		return endIndex;
	}

	public Long getTotalPage() {
		if (pageIndex == null || pageSize == null || total == null) {
			return null;
		}

		Long totalPage = (total % pageSize > 0) ? (total / pageSize + 1) : (total / pageSize);
		return totalPage;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Long getTotal() {
		return total;
	}

	public Long getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Long pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
