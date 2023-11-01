package com.test.service;

import com.test.domain.AdminVO;

public interface AdminService {

	AdminVO admin_ok(String admin_id);
	
	void loginTime(String admin_id);
}
