package com.xhg.ops.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Name: 基础dao
 * @Description: TODO
 * @Copyright: Copyright (c) 2018   
 * @Author chenxiaojun  
 * @Create Date 2018年7月2日  
 * @Version 1.0.0
 */
public interface BaseDao<T,PK extends Serializable> {  
      
	/**
	 * 新增实体 
	 * chenxiaojun 2018年7月2日  
	 * @param entity
	 * @return 影响记录条数 
	 */
    public abstract int insert(T entity);  
      
    /**
     * 修改一个实体对象（UPDATE一条记录）  
     * chenxiaojun 2018年7月2日  
     * @param entity  实体对象
     * @return  修改的对象个数，正常情况=1 
     */
    public abstract int update(T entity);    
        
    /**
     * 修改符合条件的记录  
     * 此方法的另一个用途是把一条记录的个别字段的值修改为新值（定值），此时要把条件设置为该记录的主键
     * chenxiaojun 2018年7月2日  
     * @param param  用于产生SQL的参数值，包括WHERE条件、目标字段和新值等  
     * @return  修改的记录个数，用于判断修改是否成功  
     */
    public abstract int updateColumnsByPK(Map<String, Object> param);    
        
    /**
     * 按主键删除记录  
     * chenxiaojun 2018年7月2日  
     * @param primaryKey  主键对象
     * @return  删除的对象个数，正常情况=1  
     */
    public abstract int delete(PK primaryKey);    
    
    /**
     * 删除符合条件的记录  (此方法一定要慎用，如果条件设置不当，可能会删除有用的记录！)
     * chenxiaojun 2018年7月2日  
     * @param param  用于产生SQL的参数值，包括WHERE条件（其他参数内容不起作用）  
     * @return  删除的对象个数，正常情况=1  
     */
    public abstract int deleteBy(Map<String, Object> param);     
        
    /**
     * 查询整表总记录数
     * chenxiaojun 2018年7月2日  
     * @return 整表总记录数
     */
    public abstract int count();    
        
    /**
     * 查询符合条件的记录数  
     * chenxiaojun 2018年7月2日  
     * @param param  查询条件参数，包括WHERE条件（其他参数内容不起作用）。此参数设置为null，则相当于count()  
     * @return
     */
    public abstract int countBy(Map<String, Object> param);    
    
    /**
     * 按主键取记录 
     * chenxiaojun 2018年7月2日  
     * @param primaryKey  主键值  
     * @return  记录实体对象，如果没有符合主键条件的记录，则返回null 
     */
    public abstract T queryByPK(PK primaryKey);    
    
    /**
     * 取全部记录  
     * chenxiaojun 2018年7月2日  
     * @return 全部记录实体对象的List  
     */
    public abstract List<T> queryAll();  

    /**
     * 获取单个记录
     * chenxiaojun 2018年7月2日  
     * @param param 查询条件参数，包括WHERE条件（其他参数内容不起作用），按id排序。此参数设置为null，获取表第一条记录
     * @return   单个实体
     */
    public abstract T querySingleBy(Map<String, Object> param);
        
    /**
     * 按条件查询记录
     * chenxiaojun 2018年7月2日  
     * @param param 查询条件参数，包括WHERE条件、分页条件、排序条件
     * @return  符合条件记录的实体对象的List 
     */
    public abstract List<T> queryEntitysBy(Map<String, Object> param);     
        
    /**
	 * 批量插入
	 * chenxiaojun 2018年7月2日  
	 * @param list   entity列表
	 * @return
	 */
    public abstract int insertBatch(final List<T> list);    
        
    /**
	 * 批量修改
	 * chenxiaojun 2018年7月2日  
	 * @param list   entity列表
	 * @return
	 */ 
    public abstract int updateBatch(final List<T> list);    
        
    /**
	 * 批量删除
	 * chenxiaojun 2018年7月2日  
	 * @param list   主键列表
	 * @return
	 */
    public abstract int deleteBatch(final List<PK> list);    
}  
