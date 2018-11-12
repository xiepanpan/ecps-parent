package com.xiepanpan.ecps.dao;


import com.xiepanpan.ecps.model.EbConsoleLog;
import com.xiepanpan.ecps.model.EbItem;
import com.xiepanpan.ecps.model.QueryCondition;

import java.util.List;

/**
 * describe: 后台操作日志dao
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbConsoleLogDao {

    public void saveConsoleLog(EbConsoleLog ebConsoleLog);

}
