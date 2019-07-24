package com.xijn.squirrel.admin.dao;

import com.xijn.squirrel.admin.core.model.SquirrelUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ISquirrelUserDao {

  public int add(SquirrelUser squirrelUser);

  public int update(SquirrelUser squirrelUser);

  public int delete(@Param("id") int id);

  public SquirrelUser findByUserName(@Param("userName") String userName);

  public SquirrelUser findById(@Param("id") int id);

  public List<SquirrelUser> loalAll();

  public List<SquirrelUser> pageList(@Param("offset") int offset,
                                      @Param("pageSize") int pageSize,
                                      @Param("userName") String userName,
                                      @Param("groupName") String groupName,
                                      @Param("excludeGroupName") String excludeGroupName,
                                      @Param("type") int type);

  public int pageListCount(@Param("offset") int offset,
                            @Param("pageSize") int pageSize,
                            @Param("userName") String userName,
                            @Param("groupName") String groupName,
                            @Param("excludeGroupName") String excludeGroupName,
                            @Param("type") int type);
}