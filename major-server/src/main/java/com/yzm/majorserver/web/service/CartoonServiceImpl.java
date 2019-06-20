package com.yzm.majorserver.web.service;

import com.yzm.majorserver.web.dataobject.CartoonCat;
import com.yzm.majorserver.web.dataobject.CartoonContent;
import com.yzm.majorserver.web.mapper.CartoonCatMapper;
import com.yzm.majorserver.web.mapper.CartoonContentMapper;
import com.yzm.majorserver.web.query.CartoonQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 00:20 2019/6/20
 * ===========================
 */
@Service
public class CartoonServiceImpl {
    private CartoonCatMapper catMapper;

    private CartoonContentMapper contentMapper;

    @Autowired
    public CartoonServiceImpl(CartoonCatMapper catMapper, CartoonContentMapper contentMapper) {
        this.catMapper = catMapper;
        this.contentMapper = contentMapper;
    }

    public List<CartoonCat> list(CartoonQuery query) {
        return catMapper.list(query);
    }

    public List<CartoonContent> findByCatId(Integer catId) {
        return contentMapper.list(catId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer upload(MultipartFile file) {
        return null;
    }

    public Integer delete(Integer catId) {
        return null;
    }
}
