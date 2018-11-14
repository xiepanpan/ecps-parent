package com.xiepanpan.ecps.dao;


import com.xiepanpan.ecps.model.EbItem;
import com.xiepanpan.ecps.model.QueryCondition;
import com.xiepanpan.ecps.model.TsPtlUser;

import java.util.List;
import java.util.Map;

/**
 * describe: 用户登录dao层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface TsPtlUserDao {

    /**
     * 根据用户名密码查询用户
     * @param map
     * @return
     */
    public TsPtlUser selectUserByUsernameAndPwd(Map<String,String> map);


}
