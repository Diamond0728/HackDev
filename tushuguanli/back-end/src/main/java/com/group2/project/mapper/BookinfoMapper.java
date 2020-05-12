package com.group2.project.mapper;

import com.group2.project.entity.Bookinfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface BookinfoMapper {
    @Select("SELECT * FROM bookinfo")
    public Bookinfo[] getAll();

    @Select("SELECT * FROM bookinfo WHERE ISBN = #{isbn} LIMIT 1")
    public Bookinfo getOneByIsbn(String isbn);

    @Select("SELECT * FROM bookinfo WHERE b_name LIKE #{b_name}")
    public Bookinfo[] getByName(String b_name);


    @Insert("INSERT INTO bookinfo (isbn,b_name,author,publish,pubdate,agent_id) VALUES(#{isbn},#{b_name},#{author},#{publish},#{pubdate},#{agent_id})")
    public void insert(Bookinfo bookinfo);

    @Select("SELECT * FROM bookinfo limit #{start},#{count}")
    public Bookinfo[] getByPage(@Param("start") Integer Start,@Param("count") Integer count);
}
