package com.qjl.dao;

import com.qjl.domain.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LogDao {
    @Insert("insert into sys_log values(log_seq.nextval,#{visitTime},#{username},#{ip},#{method})")
    void save(SysLog log);

    @Select("select * from sys_log")
    List<SysLog> findAll();
}
