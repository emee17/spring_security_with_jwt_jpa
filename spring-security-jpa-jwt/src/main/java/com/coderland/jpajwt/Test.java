package com.coderland.jpajwt;

import java.util.HashMap;
import java.util.Map;

import com.coderland.jpajwt.utility.ApiPermissions;
import com.coderland.jpajwt.utility.ApiRole;

public class Test {

	public static void main(String[] args) {
		
		Map<ApiPermissions, String> map = new HashMap<>();

		ApiPermissions coderRead = ApiPermissions.CODER_READ;
		map.put(coderRead, coderRead.getPermissions());

		map.forEach((k, v) -> System.out.println(k + " : " + v));
		
		
		System.out.println(ApiRole.ADMIN.name());
	}

}
