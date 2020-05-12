package com.group2.project.mapper;

import com.group2.project.entity.BorrowReturn;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BorrowReturnMapper {
    @Select("SELECT * FROM borrow_return WHERE b_id=#{b_id} AND rtime is null")
    public BorrowReturn[] getBorrowNotReturn (Integer b_id);

    @Select("SELECT * FROM borrow_return WHERE u_id=#{u_id} ORDER BY rtime")
    public BorrowReturn[] getBorrowReturn (Integer u_id);

    @Update("UPDATE borrow_return SET rtime=#{rtime} WHERE br_id=#{br_id}")
    public void updateRtime(BorrowReturn borrowReturn);

    @Insert("INSERT INTO borrow_return(u_id,b_id,btime) VALUES(#{u_id},#{b_id},#{btime})")
    public void borrow(BorrowReturn borrowReturn);
}
