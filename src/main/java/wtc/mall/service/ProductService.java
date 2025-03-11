package wtc.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import wtc.mall.pojo.Product;

public interface ProductService extends IService<Product> {

    String saveImage(MultipartFile image);

}
