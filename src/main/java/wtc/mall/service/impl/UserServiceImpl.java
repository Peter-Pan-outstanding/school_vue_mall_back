package wtc.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wtc.mall.mapper.UserMapper;
import wtc.mall.pojo.User;
import wtc.mall.service.UserService;

@Service
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements UserService{

    @Resource
    private UserMapper mapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User login(User user) {

        User userInDB = mapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()));
        if (userInDB == null) {
            return null;
        }
        if (encoder.matches(user.getPassword(), userInDB.getPassword())) {
            return userInDB;
        } else {
            return null;
        }
    }

    @Override
    public boolean register(User user) {
        // Directly attempt insertion and then catch exceptions

        try {
            user.setPassword(encoder.encode(user.getPassword()));
            mapper.insert(user);
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }


}
