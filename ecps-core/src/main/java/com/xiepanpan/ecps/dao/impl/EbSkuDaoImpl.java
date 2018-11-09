package com.xiepanpan.ecps.dao.impl;

import com.xiepanpan.ecps.dao.EbParaValueDao;
import com.xiepanpan.ecps.dao.EbSkuDao;
import com.xiepanpan.ecps.model.EbParaValue;
import com.xiepanpan.ecps.model.EbSku;
import com.xiepanpan.ecps.model.EbSpecValue;
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
public class EbSkuDaoImpl extends SqlSessionDaoSupport implements EbSkuDao {

    String ns="com.xiepanpan.ecps.mapper.EbSkuMapper.";
    String ns1="com.xiepanpan.ecps.mapper.EbSpecValueMapper.";

    @Override
    public void saveSku(List<EbSku> ebSkuList, Long itemId) {
        SqlSession sqlSession = this.getSqlSession();
        for (EbSku ebSku :ebSkuList) {
            ebSku.setItemId(itemId);
            //插入数据 并返回主键
            sqlSession.insert(ns+"insert",ebSku);
            List<EbSpecValue> ebSpecValueList = ebSku.getEbSpecValueList();
            for (EbSpecValue ebSpecValue :ebSpecValueList) {
                ebSpecValue.setSkuId(ebSku.getSkuId());
                sqlSession.insert(ns1+"insert",ebSpecValue);
            }

        }
    }
}
