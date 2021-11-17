package com.self.highperformance.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.self.highperformance.goods.model.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface SkuMapper extends BaseMapper<Sku> {

    /**
     * 库存递减, innoDb是有行级锁的, 但是要确保不扣到负数
     */
    @Update("update sku set num=num-#{num} where id=#{id} and num>=#{num}")
    int dcount(@Param("id") String id, @Param("num") Integer num);

}
