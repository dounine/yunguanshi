package com.yunguanshi.init;

import com.yunguanshi.exception.ValidateFailureException;

public interface IData {

	/**
	 * 插入数据
	 */
	public void insert() throws ValidateFailureException;
}
