package com.yzm.majorserver.web.mapper;

import com.yzm.majorserver.web.dataobject.CartoonContent;
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
public interface CartoonContentMapper {

    /**
     * @param catId
     * @return
     */
    List<CartoonContent> list(@Param("catId") Integer catId);

    /**
     * @param catId
     * @param contentList
     * @return
     */
    Integer save(@Param("catId") Integer catId,@Param("contentList") List<CartoonContent>contentList);

    Integer update(CartoonContent cartoonContent);

    Integer delete(@Param("id") Integer id);
}
