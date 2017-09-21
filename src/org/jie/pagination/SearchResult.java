package org.jie.pagination;

import java.util.List;

public class SearchResult<T> {

	private Pagination page;
	private List<T> results;

	public Pagination getPage() {
		return page;
	}

	public void setPage(Pagination page) {
		this.page = page;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

}
