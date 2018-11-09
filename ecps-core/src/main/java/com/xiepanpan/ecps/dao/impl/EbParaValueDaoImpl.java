package com.xiepanpan.ecps.dao.impl;

import com.xiepanpan.ecps.dao.EbParaValueDao;
import com.xiepanpan.ecps.model.EbParaValue;
import com.xiepanpan.ecps.model.QueryCondition;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * describe:
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Repository
public class EbParaValueDaoImpl extends SqlSessionDaoSupport implements EbParaValueDao {

    String ns="com.xiepanpan.ecps.mapper.EbParaValueMapper.";


    @Override
    public void saveParaValue(List<EbParaValue> ebParaValueList, Long itemId) {
        SqlSession sqlSession = this.getSqlSession();
        for (EbParaValue ebParaValue : ebParaValueList){
            ebParaValue.setItemId(itemId);
            sqlSession.insert(ns+"insert",ebParaValue);
        }
    }
}
