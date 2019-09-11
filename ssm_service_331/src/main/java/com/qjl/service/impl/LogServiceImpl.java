package com.qjl.service.impl;

import com.qjl.dao.LogDao;
import com.qjl.domain.SysLog;
import com.qjl.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogDao logDao;

    @Override
    public void save(SysLog log) {
        logDao.save(log);
    }

    @Override
    public List<SysLog> findAll() {
        return logDao.findAll();
    }
}
