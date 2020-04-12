package cn.windf.core.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * 自动计算一些分页信息
 */
public class Page<T> extends PageParameter implements Serializable {
	/**
	 * 默认的分页
	 */
	public static final Integer DEFAULT_PAGE_SIZE = 10;

	private static final long serialVersionUID = 1L;

	private List<T> data;
	private Long total;
	private Long pageIndex;
	private Integer pageSize;

	public Page() {
		this.pageIndex = 1L;
		this.pageSize = DEFAULT_PAGE_SIZE;
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

	/**
	 * 获取起始索引
	 * @return 索引值
	 */
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

	/**
	 * 获取结束索引
	 * @return 索引值
	 */
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

	/**
	 * 获取总页数
	 * @return
	 */
	public Long getTotalPage() {
		if (pageIndex == null || pageSize == null || total == null) {
			return null;
		}

		return (total % pageSize > 0) ? (total / pageSize + 1) : (total / pageSize);
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
