package com.everis.mail.filter;

import java.util.ArrayList;
import java.util.List;

import com.everis.mail.Mail;
import com.everis.mail.MailException;
import com.everis.mail.Message;

public class FilterChainMail {

	private Mail target;
	private List<FilterMail> filters;
	
	public FilterChainMail() {
		filters = new ArrayList<FilterMail>();
		target = new Mail();
	}
	
	public void send(Message message) throws MailException {
		for(FilterMail filter : filters) {
			filter.send(message);
		}
		
		target.setMessage(message);
		target.send();
	}
}
