package com.xiepanpan.ecps.utils;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class OrderFlowTaskListener implements TaskListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6702316898427627307L;

	public void notify(DelegateTask delegateTask) {
		String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
		delegateTask.setAssignee(taskDefinitionKey+"er");
	}

}
