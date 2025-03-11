package wtc.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wtc.mall.pojo.Product;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
