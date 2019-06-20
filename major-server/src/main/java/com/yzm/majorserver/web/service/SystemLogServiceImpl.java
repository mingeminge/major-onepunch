package com.yzm.majorserver.web.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzm.majorserver.common.vo.TableResult;
import com.yzm.majorserver.web.dataobject.SystemLogDO;
import com.yzm.majorserver.web.mapper.SystemLogMapper;
import com.yzm.majorserver.web.query.SystemLogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 11:14 2019/6/8
 * ===========================
 */
@Service
public class SystemLogServiceImpl {

    private SystemLogMapper systemLogMapper;

    @Autowired
    public SystemLogServiceImpl(SystemLogMapper systemLogMapper) {
        this.systemLogMapper = systemLogMapper;
    }


    public TableResult findAll(SystemLogQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SystemLogDO> all = systemLogMapper.findAll(query);
        PageInfo<SystemLogDO> info = new PageInfo<>(all);
        return TableResult.success(info.getTotal(), all);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Integer id) {
        return systemLogMapper.deleteLog(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer saveLog(SystemLogDO log) {
        return systemLogMapper.saveLog(log);
    }
}
