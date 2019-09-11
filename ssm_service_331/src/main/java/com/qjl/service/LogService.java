package com.qjl.service;

import com.qjl.domain.SysLog;

import java.util.List;

public interface LogService {
    void save(SysLog log);

    List<SysLog> findAll();
}
