package com.xiepanpan.ecps.model;

import lombok.Data;

/**
 * describe: 查询条件实体类
 *
 * @author xiepanpan
 * @date 2018/11/05
 */
@Data
public class QueryCondition {

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 实体商品审核状态(号卡没有审核)：0：待审核；1：审核通过；2：审核未通过；
     */
    private Short auditStatus;

    /**
     * 上下架状态：0.为上架 1.为下架
     */
    private Short showStatus;

    /**
     * 商品名称、号卡卡号：文档标题可用作seo相关的title字段
     */
    private String itemName;

    /**
     * 开始行数
     */
    private Integer startNum;

    /**
     * 结束行数
     */
    private Integer endNum;

    /**
     * 当前页码
     */
    private Integer pageNo;

}
