package com.group2.project.mapper;

import com.group2.project.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRoleMapper {
    @Select("SELECT * FROM user_role")
    public UserRole[] getAllUerRole();

    @Select("SELECT * FROM user_role WHERE u_id = #{u_id}")
    public UserRole[] getUserRoleByUid(Integer u_id);

    @Select("SELECT * FROM user_role WHERE r_id = #{r_id}")
    public UserRole[] getUserRoleByPid(Integer p_id);

    @Insert("INSERT INTO user_role(u_id, r_id) VALUES(#{u_id},#{r_id})")
    public void insert(UserRole userRole);

    @Delete("DELETE FROM user_role WHERE u_id=#{u_id}")
    public void deleteAllByUid(Integer u_id);
}
