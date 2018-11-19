package com.xiepanpan.ecps.model;

/**
 * @program: ecps-parent
 * @description: 购物车实体类
 * @author: xiepanpan
 * @create: 2018-11-19 20:16
 **/
public class EbCart {

    private Long skuId;
    private Integer quantity;
    private EbSku ebSku;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public EbSku getEbSku() {
        return ebSku;
    }

    public void setEbSku(EbSku ebSku) {
        this.ebSku = ebSku;
    }
}
