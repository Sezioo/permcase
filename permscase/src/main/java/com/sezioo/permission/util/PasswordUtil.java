package com.sezioo.permission.util;

import java.util.Date;
import java.util.Random;

public class PasswordUtil {
	
	public static final String[] word = {
			"a","b","c","d","e","f","g",
			"h","j","k","m","n","p","q",
			"r","s","t","u","v","w","x","y","z",
			"A","B","C","D","E","F","G",
			"H","J","K","M","N","P","Q",
			"R","S","T","U","V","W","X","Y","Z"
	};
	
	public static final int[] num = {
			2,3,4,5,6,7,8,9
	};
	
	public static String generatePassword() {
		boolean flag = false;
		StringBuilder password = new StringBuilder();
		Random random = new Random(new Date().getTime());
		int length = random.nextInt(3) + 8;
		for(int i=0;i<length;i++) {
			if(flag) {
				password.append(num[random.nextInt(num.length)]);
			}else {
				password.append(word[random.nextInt(word.length)]);
			}
			flag = !flag;
		}
		return password.toString();
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(generatePassword());
		Thread.sleep(100);
		System.out.println(generatePassword());
		Thread.sleep(100);
		System.out.println(generatePassword());
	}
}
