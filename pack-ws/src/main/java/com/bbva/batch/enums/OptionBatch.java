package com.bbva.batch.enums;

public enum OptionBatch {

	BATCH_JNDI("jndi"),
	BATCH_ID_JOB("idJob");
	
	private String option;
	
	OptionBatch(String option) {
		this.option = option;
	}
	
	public String option() {
		return this.option;
	}
}
