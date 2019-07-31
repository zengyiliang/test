package com.xhg.ops.common;

import java.io.Serializable;
import java.util.List;

//CommonDao<SystemUser,Integer>
public interface CommonDao<T,PK extends Serializable> {

    public abstract int deleteByPrimaryKey(PK primaryKey);

    public abstract int insert(T record);

    public abstract int insertSelective(T record);

    public abstract T selectByPrimaryKey(PK primaryKey);

    public abstract int updateByPrimaryKeySelective(T record);

    public abstract int updateByPrimaryKey(T record);

    public abstract int queryAllCount(T record);

    public abstract List<T> queryAllList(T record);

    public abstract int insertBatch(final List<T> list);

    public abstract int updateBatch(final List<T> list);

    public abstract int deleteBatch(final List<PK> list);

    public abstract List<T> queryAll();
}
