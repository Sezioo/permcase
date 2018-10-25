package com.sezioo.permission.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestVo {
	
	@NotNull
	private Integer id;
	
	@NotBlank
	private String msg;
}
