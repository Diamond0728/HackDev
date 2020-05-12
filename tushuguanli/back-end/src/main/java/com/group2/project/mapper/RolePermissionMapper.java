package com.group2.project.mapper;

import com.group2.project.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RolePermissionMapper {
    @Select("SELECT * FROM role_permission WHERE r_id = #{r_id}")
    public RolePermission[] getRolePermissionByRid(Integer r_id);

    @Select("SELECT * FROM role_permission WHERE p_id = #{p_id}")
    public RolePermission[] getRolePermissionByPid(Integer p_id);
}
