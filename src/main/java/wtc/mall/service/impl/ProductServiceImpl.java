package wtc.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wtc.mall.mapper.ProductMapper;
import wtc.mall.pojo.Product;
import wtc.mall.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Resource
    private ProductMapper mapper;

    @Value("${image.upload.dir}")
    private String imageUploadDir;


    @Override
    public String saveImage(MultipartFile image) {
        String filename = Math.random()+image.getOriginalFilename();
        String filePath = imageUploadDir + File.separator + filename;
        Path path = Path.of(filePath);

        try {
            Files.copy(image.getInputStream(),path);
            return "http://localhost:8080/mall/images/"+filename;
        } catch (IOException ignored) {

        }
        return "";
    }
}
