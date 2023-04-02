package com.atguigu.model.system;

import com.atguigu.model.base.BaseEntity;
import lombok.Data;


@Data
public class SysRoleMenuDTO extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String menuId;
	private String roleName;
}

