package com.ken.material.common.page;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public class PageRequest implements Serializable {

	private static final long serialVersionUID = -3930180379790344299L;

	/** 默认页码 */
	private static final int DEFAULT_PAGE_NUMBER = 1;

	/** 默认每页记录数 */
	private static final int DEFAULT_PAGE_SIZE = 10;

	/** 最大每页记录数 */
	private static final int MAX_PAGE_SIZE = 1000;

	/** 页码 */
	private Integer page = DEFAULT_PAGE_NUMBER;

	/** 每页记录数 */
	private Integer rows = DEFAULT_PAGE_SIZE;

	private Integer draw;

	/** 起始记录 */
	private int start = DEFAULT_PAGE_NUMBER;

	/** 每页记录数 */
	private int length = DEFAULT_PAGE_SIZE;

	/**
	 * 初始化一个新创建的Pageable对象
	 */
	public PageRequest() {
	}

	/**
	 * 初始化一个新创建的Pageable对象
	 *
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            每页记录数
	 */
	public PageRequest(Integer pageNumber, Integer pageSize) {
		if (pageNumber != null && pageNumber >= 1) {
			this.page = pageNumber;
		}
		if (pageSize != null && pageSize >= 1 && pageSize <= MAX_PAGE_SIZE) {
			this.rows = pageSize;
		}
	}

	/**
	 * 获取页码
	 * 
	 * @return 页码
	 */
	public int getPage() {
		if (page == null) {
			return (int)Math.floor(start/length) + 1;
		}
		return page;
	}

	/**
	 * 设置页码
	 * 
	 * @param pageNumber
	 *            页码
	 */
	public void setPage(int pageNumber) {
		if (pageNumber < 1) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
		this.page = pageNumber;
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return 每页记录数
	 */
	public int getRows() {
		if (rows < 1) {
			return DEFAULT_PAGE_SIZE;
		}
		return rows;
	}

	/**
	 * 设置每页记录数
	 * 
	 * @param pageSize
	 *            每页记录数
	 */
	public void setRows(int pageSize) {
		if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.rows = pageSize;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		if (start < 1) {
			start = DEFAULT_PAGE_NUMBER;
		}
		this.start = start;
		this.page = (int)Math.floor(start/length) + 1;
	}

	public int getLength() {
		if (length < 1) {
			length = DEFAULT_PAGE_SIZE;
		}
		return length;
	}

	public void setLength(int pageSize) {
		if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.length = pageSize;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PageRequest)) {
			return false;
		}
		PageRequest that = (PageRequest) o;
		return Objects.equals(page, that.page) &&
				Objects.equals(rows, that.rows);
	}

	@Override
	public int hashCode() {
		return Objects.hash(page, rows);
	}
}