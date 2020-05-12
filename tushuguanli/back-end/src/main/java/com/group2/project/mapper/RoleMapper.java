package com.group2.project.mapper;

import com.group2.project.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper {
    @Select("SELECT * FROM ROLE")
    public Role[] getAllRole();

    @Select("SELECT * FROM ROLE WHERE r_id = #{r_id}")
    public Role getRoleById(Integer r_id);

    @Select("SELECT r_id FROM ROLE WHERE rolename = #{rolename}")
    public Integer getIdByName(String rolename);

    @Delete("DELETE FROM role WHERE u_id=#{u_id}")
    public void deleteByUid(Integer u_id);
//    @Select("SELECT * FROM user")
//    public User[] getAllUser();
}
