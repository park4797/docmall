package com.test.mapper;

import com.test.domain.AdminVO;

public interface AdminMapper {
	
	AdminVO admin_ok(String admin_id);
	
	void loginTime(String admin_id);
}
