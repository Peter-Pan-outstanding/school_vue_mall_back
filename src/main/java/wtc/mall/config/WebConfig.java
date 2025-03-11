package wtc.mall.config;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import wtc.mall.interceptor.RoleInterceptor;
import wtc.mall.interceptor.TokenInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Resource
    private TokenInterceptor tokenInterceptor;

    @Resource
    private RoleInterceptor roleInterceptor;

    // 允许跨域请求
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    // 验证是否登录
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/product/**")
                .excludePathPatterns("/product/page/**")
                .addPathPatterns("/user/update")
                .addPathPatterns("user/delete/**");
        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/product/**")
                .excludePathPatterns("/product/page/**");
    }


    @Value("${image.upload.dir}")
    private String imageUploadDir;

    // 映射图片路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("mall/images/**")
                .addResourceLocations("file:"+imageUploadDir);
    }


}
