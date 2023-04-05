package com.atguigu.model.system;

import lombok.Data;


@Data
public class ProdInfo {

	private static final long serialVersionUID = 1L;

	//角色名称
	private String prodName;

	public ProdInfo(String prodName) {
		this.prodName = prodName;
	}
}

