package com.group2.project.mapper;

import com.group2.project.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PermissionMapper {
    @Select("SELECT * FROM permission")
    public Permission[] getAllPermission();

    @Select("SELECT * FROM permission WHERE p_id = #{p_id}")
    public Permission getPermissionByPid(Integer p_id);
}
