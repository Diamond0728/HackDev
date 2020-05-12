package com.group2.project.mapper;

import com.group2.project.entity.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface BookMapper {
    @Select("SELECT * FROM book")
    public Book[] getAll();

    @Select("SELECT * FROM book WHERE b_id=#{b_id}")
    public Book getOne(Integer b_id);

    @Insert("INSERT INTO book (b_id,isbn,location) VALUES(#{b_id},#{isbn},#{location})")
    public void insert(Book book);

    @Update("UPDATE book SET status=#{status} WHERE b_id=#{b_id}")
    public void updateStatus(Book book);

    @Select("SELECT * FROM book WHERE isbn=#{isbn}")
    public Book[] selectByIsbn(Book book);
}
