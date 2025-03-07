package wtc.mall.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")
public class User {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String role;

    private String profilePictureUrl;

    private Boolean isActive = true;
}
