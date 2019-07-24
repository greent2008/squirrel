package com.xijn.squirrel.admin.dao;

import com.xijn.squirrel.admin.core.model.SquirrelBiz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ISquirrelBizDao {

    public int add(SquirrelBiz squirrelBiz);

    public int update(SquirrelBiz squirrelBiz);

    public int delete(@Param("id") int id);

    public List<SquirrelBiz> loadAll();

    public List<SquirrelBiz> pageList(@Param("offset") int offset,
                                      @Param("pagesize") int pagesize,
                                      @Param("bizName") String bizName);

    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("bizName") String bizName);

    public SquirrelBiz load(@Param("id") int id);
}
