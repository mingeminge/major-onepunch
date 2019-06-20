package com.yzm.majorserver.web.mapper;

import com.yzm.majorserver.web.dataobject.CartoonCat;
import com.yzm.majorserver.web.query.CartoonQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 23:58 2019/6/19
 * ===========================
 */
@Mapper
@Repository
public interface CartoonCatMapper {

    /**
     * list
     * @param query
     * @return
     */
    List<CartoonCat> list(CartoonQuery query);

    Integer save(CartoonCat cartoonCat);

    Integer update(CartoonCat cartoonCat);

    Integer delete(@Param("id") Integer id);
}
