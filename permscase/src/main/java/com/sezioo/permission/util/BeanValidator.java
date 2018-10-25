package com.sezioo.permission.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections.MapUtils;

import com.google.common.collect.Lists;
import com.sezioo.permission.exception.ParamException;

public class BeanValidator {
	private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	public static <T>Map<String,String> validate(T t,Class... groups){
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<T>> validate = validator.validate(t, groups);
		Map<String,String> errors = new HashMap<>();
		if(!validate.isEmpty()) {
			Iterator<ConstraintViolation<T>> iterator = validate.iterator();
			while(iterator.hasNext()) {
				ConstraintViolation<T> violation = iterator.next();
				errors.put(violation.getPropertyPath().toString(),violation.getMessage());
			}
		}
		return errors;
	}
	
	public static Map<String,String> validateList(Collection<?> collection){
		Map<String,String> errors = new HashMap<>();
		if(!collection.isEmpty()) {
			Iterator<?> iterator = collection.iterator();
			while(iterator.hasNext()) {
				Object object = iterator.next();
				if(!(errors = BeanValidator.validate(object, new Class[0])).isEmpty()) {
					break;
				}
			}
		}
		return errors;
	}
	
	public static Map<String,String> validateObject(Object first,Object... objects){
		if(objects != null && objects.length > 0) {
			return BeanValidator.validateList(Lists.asList(first, objects));
		}else {
			return BeanValidator.validate(first, new Class[0]);
		}
	}
	
	public static void check(Object object) throws ParamException{
		Map<String,String> errors = BeanValidator.validate(object, new Class[0]);
		if(MapUtils.isNotEmpty(errors)) {
			throw new ParamException(errors.toString());
		}
	}
}
