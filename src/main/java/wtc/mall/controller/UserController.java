package wtc.mall.controller;

import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import wtc.mall.common.Result;
import wtc.mall.pojo.User;
import wtc.mall.service.UserService;
import wtc.mall.util.JwtUtil;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {
    // what I need is only `register, update, delete, login`
    // The delete interfaces in this project all use soft deletion.

    @Resource
    private UserService service;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        User userInDB = service.login(user);
        if (userInDB == null) {
            return Result.error(401, "登录失败,账号或密码有误");
        } else {
            userInDB.setPassword(null);
            String token = JwtUtil.generateToken(user.getUsername(),userInDB.getRole());
            HashMap<String, Object> map = new HashMap<>();
            map.put("user", userInDB);
            map.put("token",token);
            return Result.success(map);
        }
    }

    @PostMapping("/register")
    public Result<?> register (@RequestBody User user) {
        boolean register = service.register(user);
        if (register) {
            return Result.success();
        } else {
            return Result.error(409, "用户名重复");
        }
    }

    @PutMapping("/update")
    public Result<?> update (@RequestBody User user) {

        if (user.getPassword() != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }

        boolean b = service.updateById(user);
        if (b) {
            return Result.success();
        } else {
            return Result.error(400, "更新失败，请稍后再试");
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete (@PathVariable Integer id) {
        boolean b = service.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error(400, "删除失败，请稍后再试");
        }
    }


}
