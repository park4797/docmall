package com.test.service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.test.dto.EmailDTO;

import lombok.RequiredArgsConstructor;

// 현재는 mapper 인터페이스를 참조 하지 않는다.
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	// JavaMailSenderImpl 의 부모인 JavaMailSender interface를 참조
	// 주입. email-config.xml 파일의 bean으로 주입.
	private final JavaMailSender mailSender;
	
	//	public EmailServiceImpl(JavaMailSender mailSender) {
	//		this.mailSender = mailSender;
	//	}

	@Override
	public void sendMail(EmailDTO dto, String message) {
		// 메일구성정보를 담당하는 객체(받는사람, 보내는 사람, 받는사람 메일주소, 본문내용
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		try {
			// 받는사람의 메일주소 설정
			mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(dto.getReceiverMail()));
			
			// 보내는 사람(메일, 이름)
			mimeMessage.addFrom(new InternetAddress[] {new InternetAddress(dto.getSenderMail(), dto.getSenderName())});
			
			// 메일제목
			mimeMessage.setSubject(dto.getSubject(), "utf-8");
			
			// 본문
			mimeMessage.setText(message, "utf-8");
			
			mailSender.send(mimeMessage); // 위 구문들을 전송, 메세지 전송
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	
	
}
