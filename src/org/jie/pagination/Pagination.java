package org.jie.pagination;

public class Pagination {

	// 当前页数
	private Integer currentPage;
	// 每页记录数
	private Integer pageSize;
	// 总页数，通过计算获得
	@SuppressWarnings("unused")
	private Integer totalPage;
	// 总记录数，从数据库查询
	private Integer totalCount;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPage() {
		// 总记录对每页记录数取余，能整除则是总页数，不能整除则总页数为商+1
		return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
