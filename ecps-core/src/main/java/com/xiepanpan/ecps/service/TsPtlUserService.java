package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbFeature;
import com.xiepanpan.ecps.model.TsPtlUser;

import java.util.List;
import java.util.Map;

/**
 * describe: 用户登录服务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface TsPtlUserService {

    /**
     * 根据用户名密码查询用户信息
     * @param map
     * @return
     */
    public TsPtlUser selectUserByUsernameAndPwd(Map<String,String> map);

}
