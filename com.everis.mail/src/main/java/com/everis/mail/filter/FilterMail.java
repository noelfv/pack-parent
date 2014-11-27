package com.everis.mail.filter;

import com.everis.mail.MailException;
import com.everis.mail.Message;

public interface FilterMail {

	void send(Message message) throws MailException;
}
