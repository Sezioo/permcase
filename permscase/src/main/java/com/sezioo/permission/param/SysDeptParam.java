package com.sezioo.permission.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysDeptParam {
	private Integer id;
	
	@NotBlank(message="部门名称不能为空")
	@Length(min=2,max=10,message="部门名称长度应在2-10个字符")
	private String name;
	
	private Integer parentId = 0;
	
	@NotNull
	private Integer seq;
	
	@Length(max=150,message="备注请控制在150个字符以内")
	private String remark;
}
