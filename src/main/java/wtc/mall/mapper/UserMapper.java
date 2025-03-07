package wtc.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wtc.mall.pojo.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
