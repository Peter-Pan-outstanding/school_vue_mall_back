package wtc.mall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import wtc.mall.common.Result;
import wtc.mall.util.JwtUtil;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")){
            response.setStatus(200);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error(401,"无令牌")));
            return false;
        }


        try {
            Claims claims = JwtUtil.paresToken(token.replace("Bearer", ""));
            request.setAttribute("username",claims.getSubject());
            request.setAttribute("role",claims.get("role"));
        } catch (Exception e) {
            response.setStatus(200);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error(401,"令牌有误")));
            return false;
        }

        return true;
    }
}
