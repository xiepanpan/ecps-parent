package com.xiepanpan.ecps.model;

import lombok.Data;

import java.util.List;

/**
 * describe: 分页相关实体类
 *
 * @author xiepanpan
 * @date 2018/11/05
 */
@Data
public class Page {

    /**
     * 当前页码
     */
    private int pageNo=1;

    /**
     * 每页记录数
     */
    private int pageSize=5;

    /**
     * 指定的查询条件下的总记录数
     */
    private int totalCount = 0;

    /**
     * 总页数
     */
    private int totalPage= 1;

    /**
     * 开始行号
     */
    private int startNum = 0;

    /**
     * 结束行号
     */
    private int endNum = 0;

    /**
     * 查询的结果集
     */
    private List<?> list;

    public int getStartNum() {
        return (pageNo-1)*pageSize;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getEndNum() {
        return pageNo*pageSize+1;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public int getTotalPage() {
        totalPage = totalCount/pageSize;
        if (totalCount==0||totalCount%pageSize !=0) {
            totalPage++;
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
