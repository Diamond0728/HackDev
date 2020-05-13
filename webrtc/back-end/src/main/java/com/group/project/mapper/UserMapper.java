package com.group.project.mapper;

import com.group.project.entity.User;
import com.group.project.entity.UserWithPwd;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface UserMapper {
    @Select("SELECT * FROM user")
    public User[] getAllUser();

    @Select("SELECT * FROM user WHERE username = #{username}")
    public User getOneByUsername(String username);

    @Select("SELECT * FROM user WHERE username = #{username}")
    public UserWithPwd getOneWithPwdByUsername(String username);

    @Select("SELECT * FROM user WHERE u_id = #{u_id}")
    public User getOneByUid(Integer u_id);

    @Select("SELECT u_id FROM USER WHERE username = #{username}")
    public Integer getIdByName(String username);



    @Insert("INSERT INTO user(username, password) VALUES(#{username},#{password})")
    public void insert(UserWithPwd user);

    @Delete("DELETE FROM user WHERE u_id=#{u_id}")
    public void deleteById(Integer u_id);


    @Update("UPDATE user SET name=#{name}, phone=#{phone},email = #{email}, signature=#{signature},avatar=#{avatar} WHERE u_id = #{u_id}")
    public void updateByUser(User user);
}
