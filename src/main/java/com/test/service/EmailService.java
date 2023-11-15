package com.test.service;

import com.test.dto.EmailDTO;

public interface EmailService {

	void sendMail(EmailDTO dto, String message);
}
