package com.xiepanpan.ecps.exception;

/**
 * @program: ecps-parent
 * @description: 库存不足异常
 * @author: xiepanpan
 * @create: 2018-11-24 22:12
 **/
public class EbStockException extends RuntimeException {
    public EbStockException() {
        super();
    }

    public EbStockException(String message) {
        super(message);
    }

    public EbStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public EbStockException(Throwable cause) {
        super(cause);
    }

    protected EbStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
