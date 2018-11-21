package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbBrand;
import com.xiepanpan.ecps.model.EbItem;
import com.xiepanpan.ecps.utils.FMutil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * describe:
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@RunWith(value = SpringJUnit4ClassRunner.class)//使用spring和Junit整合的运行器
@ContextConfiguration(locations = {"classpath:beans.xml"})//指定spring的配置文件位置
public class EbBrandServiceImplTest {

    @Autowired
    EbBrandService ebBrandService;
    @Autowired
    EbIndexService ebIndexService;
    @Autowired
    EbItemService ebItemService;
    @Autowired
    EbRedisService ebRedisService;

    /**
     * 测试品牌保存
     */
    @Test
    public void saveBrand() {
        EbBrand ebBrand = new EbBrand();
        ebBrand.setBrandName("iphone xr");
        ebBrand.setBrandDesc("信号好");
        ebBrand.setImgs("http://");
        ebBrand.setWebsite("http://iphone");
        ebBrand.setBrandSort(1);
        ebBrandService.saveBrand(ebBrand);
    }

    @Test
    public void selectBrandAll() {
    }

    /**
     * solr导入索引测试
     */
    @Test
    public void importIndexTest() throws Exception {
        ebIndexService.importIndex();
    }

    /**
     * freemarker生成html文件测试
     */
    @Test
    public void testGenerateStaticPage() throws Exception {
        EbItem ebItem = ebItemService.selectItemDetailById((long) 3100);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("ebItem",ebItem);
        map.put("path","http://localhost:8083/ecps-portal");
        map.put("request_file_path","http://localhost:8092/ecps-file");
        FMutil.ouputFile("productDetail.ftl",ebItem.getItemId()+".html",map);
    }

    /**
     * 最小销售单元导入到redis中
     */
    @Test
    public void importEbSkuToRedis(){
        ebRedisService.importEbSkuToRedis();
    }
}