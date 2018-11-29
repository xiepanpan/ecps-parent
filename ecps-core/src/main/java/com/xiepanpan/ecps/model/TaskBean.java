package com.xiepanpan.ecps.model;

import org.activiti.engine.task.Task;

import java.util.List;

/**
 * @description: 工作流任务实体类
 *
 * @author: xiepanpan
 *
 * @create: 2018-11-29 20:20
 **/
public class TaskBean {

    private EbOrder ebOrder;
    private Task task;
    private String businessKey;
    /**
     * 做动态按钮
     */
    private List<String> outcome;
    private String income;

    public EbOrder getEbOrder() {
        return ebOrder;
    }

    public void setEbOrder(EbOrder ebOrder) {
        this.ebOrder = ebOrder;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public List<String> getOutcome() {
        return outcome;
    }

    public void setOutcome(List<String> outcome) {
        this.outcome = outcome;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
}
