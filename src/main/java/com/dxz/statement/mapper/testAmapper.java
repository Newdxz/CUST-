package com.dxz.statement.mapper;

import com.dxz.statement.POJO.testA;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface testAmapper {
    /**
     * 查找所有
     * @return
     */
    List<testA> selectAll();

}
