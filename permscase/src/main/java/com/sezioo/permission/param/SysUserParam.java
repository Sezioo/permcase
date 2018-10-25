package com.sezioo.permission.param;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserParam {
	
	private Integer id;
	
	@NotBlank
	@Length(min=1,max=20,message="用户名长度需控制在20字以内")
    private String username;

	@NotBlank
	@Length(min=1,max=13,message="电话号码长度需控制在13字以内")
    private String telephone;

	@NotBlank
	@Length(min=5,max=50,message="邮箱长度需控制在50字以内")
    private String mail;
    
	@NotNull
    private Integer deptId;

	@NotNull
	@Min(value=0,message="请给予用户正确的状态")
	@Max(value=2,message="请给予用户正确的状态")
    private Integer status;

	@Length(max=255,message="备注长度不能超过255个字符")
    private String remark;
}
