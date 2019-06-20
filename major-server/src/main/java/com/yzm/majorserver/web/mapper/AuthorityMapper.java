package com.yzm.majorserver.web.mapper;

import com.yzm.majorserver.web.dataobject.AuthorityDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:23 2019/6/9
 * ===========================
 */
@Mapper
@Repository
public interface AuthorityMapper {

    /**
     * 添加
     * @param authorityDO
     * @return
     */
    Integer save(AuthorityDO authorityDO);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer delete(@Param("id") Integer id);

    /**
     * 修改
     * @param authorityDO
     * @return
     */
    Integer update(AuthorityDO authorityDO);

    /**
     * 查询
     * @return
     */
    List<AuthorityDO> findAll();

    /**
     * 通过id查询
     * @param id
     * @return
     */
    AuthorityDO findById(@Param("id")Integer id);
}
