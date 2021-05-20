package com.aihouse.aihousecore.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.lang.reflect.Field;
import java.util.*;

/**
 * solr工具类，提供添加数据到Solr,删除Solr数据和查询Solr数据
 */
public class SolrTool {

    /**
     * 根据记录ID删除Solr中数据
     * @param id
     * @param client
     * @param coreName
     */
    public static void delete(String id,SolrClient client,String coreName)
    {
        try
        {
            client.deleteById(coreName,id.toString());
            UpdateResponse up = client.commit(coreName);
            System.out.println("0000000000000000"+up);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 更新Solr中的数据，数据模型Map中必须包含id
     * @param map  数据
     * @param client solr客户端
     * @param coreName core名称
     */
    public static void update(Map map,SolrClient client,String coreName)
    {
        try
        {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", map.get("id"));
            for(Object o:map.keySet())
            {
                String key = (String)o;
                if(!"id".equals(key))
                {
                    doc.addField(key,  map.get(key));
                }
            }
            UpdateResponse resp = client.add(coreName,doc);
            client.commit(coreName);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 添加数据对象Map到Solr
     * 如果Solr中没有Field域，自动创建这些Field
     * @param map 数据
     * @param client 客户端
     * @param coreName core 名称
     */
    public static void addMap(Map map,SolrClient client,String coreName)
    {
        try
        {
            SolrInputDocument doc = new SolrInputDocument();
            Set<String> keys = map.keySet();
            for(String k:keys)
            {
                doc.addField(k, map.get(k));
            }

            client.add(coreName,doc);
            UpdateResponse up = client.commit(coreName);
            System.out.println("11111111111111111111"+up);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 查询Solr中符合条件的数据并高亮显示
     * @param clz　查询的Solr记录转化为对象的类型
     * @param q　查询条件
     * @param client　solr客户端
     * @param coreName　core名称
     * @param <T>　对象类型
     * @return
     */
    public static  <T> ResultInfo<T> queryList(Class<T> clz, SolrQuery q,SolrClient client,String coreName)
    {
        try
        {
            QueryResponse response = client.query(coreName,q);
            SolrDocumentList list = response.getResults();
            List<T> lst = getBeans(clz, list);
            ResultInfo<T> result = new ResultInfo<T>();
            result.setList(lst);
            result.setTotal(list.getNumFound());
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *将查询的SolrDocument转换为对象
     * @param clazz
     * @param solrDocumentList
     * @param <T>
     * @return
     * @throws Exception
     */
    public static final <T> List<T> getBeans(Class<T> clazz,
                                     SolrDocumentList solrDocumentList) throws Exception {

        List<T> list=new ArrayList<>();
        //反射出实例
        T t = clazz.newInstance();
        //获取所有属性
        Field[] fields = clazz.getDeclaredFields();
        for(SolrDocument doc:solrDocumentList) {
            T t1 = clazz.newInstance();
            for (Field field : fields) {
                // 如果注解为默认的 采用此属性的name来从solr中获取值
                Object val = doc.get(field.getName());
                if(val != null) {
                    BeanUtils.setProperty(t1, field.getName(),
                            doc.get(field.getName()));
                }
            }
            list.add(t1);
        }
        return list;
    }
}
