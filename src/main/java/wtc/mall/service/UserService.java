package wtc.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wtc.mall.pojo.User;

public interface UserService extends IService<User> {

    User login(User user);

    boolean register (User user);

}
