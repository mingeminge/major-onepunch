package com.yzm.majorserver.web.service;

import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.web.dataobject.AuthorityDO;
import com.yzm.majorserver.core.exception.MajorException;
import com.yzm.majorserver.web.mapper.AuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:46 2019/6/9
 * ===========================
 */
@Service
public class AuthorityServiceImpl {


    private AuthorityMapper authorityMapper;

    @Autowired
    public AuthorityServiceImpl(AuthorityMapper authorityMapper) {
        this.authorityMapper = authorityMapper;
    }

    public AuthorityDO findById(Integer id) {
        return authorityMapper.findById(id);
    }

    public List<AuthorityDO> findAll() {
        return authorityMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(AuthorityDO authorityDO) {
        if (authorityDO.getParentId() == null) {
            authorityDO.setParentId(0);
        }
        Integer save = authorityMapper.save(authorityDO);
        if (null == save || save == 0) {
            throw new MajorException(ResultEnum.FAILED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(AuthorityDO authorityDO) {
        Integer update = authorityMapper.update(authorityDO);
        if (null == update || update == 0) {
            throw new MajorException(ResultEnum.FAILED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        Integer delete = authorityMapper.delete(id);
        if (null == delete || delete == 0) {
            throw new MajorException(ResultEnum.FAILED);
        }
    }
}
