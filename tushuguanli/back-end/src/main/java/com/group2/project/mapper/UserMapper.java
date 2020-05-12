package com.group2.project.mapper;

import com.group2.project.entity.User;
import com.group2.project.entity.UserWithPwd;
import com.group2.project.entity.UserWithRoles;
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

    @Select("SELECT * FROM user limit #{start},#{count}")
    public User[] getUsersLimit(Map map);

    @Select("SELECT u_id FROM USER WHERE username = #{username}")
    public Integer getIdByName(String username);

    @Select("SELECT user.u_id,username,name,phone,email,rolename,mask from user,user_role,role " +
            "where user.u_id=user_role.u_id and user_role.r_id=role.r_id limit #{start},#{count}")
    public UserWithRoles[] getUserWithRole(Map map);

    @Insert("INSERT INTO user(username, password) VALUES(#{username},#{password})")
    public void insert(UserWithPwd user);

    @Delete("DELETE FROM user WHERE u_id=#{u_id}")
    public void deleteById(Integer u_id);

    @Update("UPDATE user SET phone = #{phone} , email = #{email} WHERE u_id = #{u_id}")
    public void update(User user);

    @Update("UPDATE user SET name=#{name}, phone=#{phone},email = #{email} WHERE u_id = #{u_id}")
    public void updateByUser(User user);

//
//    @Select("SELECT * FROM tb_user WHERE id = #{id}")
//    User getUserById(Integer id);
//
//    @Select("SELECT * FROM tb_user")
//    public List<User> getUserList();
//
//    @Insert("insert into tb_user(username, age, ctm) values(#{username}, #{age}, now())")
//    public int add(User user);
//
//    @Update("UPDATE tb_user SET username = #{user.username} , age = #{user.age} WHERE id = #{id}")
//    public int update(@Param("id") Integer id, @Param("user") User user);
//
//    @Delete("DELETE from tb_user where id = #{id} ")
//    public int delete(Integer id);
}
