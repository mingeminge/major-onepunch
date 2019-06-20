package com.yzm.majorserver.web.mapper;

import com.yzm.majorserver.web.dataobject.SystemLogDO;
import com.yzm.majorserver.web.query.SystemLogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 11:05 2019/6/8
 * ===========================
 */
@Mapper
@Repository
public interface SystemLogMapper {

    /**
     * 查询
     *
     * @return
     */
    List<SystemLogDO> findAll(SystemLogQuery query);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Integer deleteLog(@Param("id") Integer id);

    /**
     * 保存日志
     *
     * @param systemLogDO
     * @return
     */
    Integer saveLog(SystemLogDO systemLogDO);
}
