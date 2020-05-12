package com.group2.project.mapper;

import com.group2.project.entity.Reserve;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReserveMapper {
    @Select("SELECT * FROM reserve WHERE u_id=#{u_id} AND ISBN=#{isbn}")
    public Reserve[] find(Reserve reserve);

    @Select("SELECT * FROM reserve WHERE u_id=#{u_id}")
    public Reserve[] getByUid(Integer u_id);

    @Select("SELECT * FROM reserve WHERE isbn=#{isbn}")
    public Reserve[] getByIsbn(String isbn);

    @Insert("INSERT INTO reserve VALUES(#{u_id},#{isbn},#{reservetime},#{deadline})")
    public void insertOne(Reserve reserve);

    @Update("UPDATE reserve SET deadline=#{deadline} WHERE u_id=#{u_id} AND isbn= #{isbn}")
    public void update(Reserve reserve);
}
