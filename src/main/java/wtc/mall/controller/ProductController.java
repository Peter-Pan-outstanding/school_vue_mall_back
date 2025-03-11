package wtc.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wtc.mall.common.Result;
import wtc.mall.pojo.Product;
import wtc.mall.service.ProductService;

@RestController
@RequestMapping("/product")
// 所有的用户都可以 /page
// 但是只有登录过的role为merchant 的用户才能访问delete save update
public class ProductController {

    @Resource
    private ProductService service;

    // 。GET /page 分页查询@RequestParam int current, @RequestParam int size,@RequestBody(required = false) 【条件】
    // 这个接口同时处理商家的分页查询通过user_id 区分商家
    @GetMapping("/page")
    public Result<?> page(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String userId
    ){
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            wrapper.eq(Product::getUserId, userId);
        }

        Page<Product> page = service.page(new Page<>(current, size),wrapper);
        return Result.success(page);
    }

    // @RequestPart in Spring Boot is used to handle multipart requests,
    // typically when uploading files along with other data.
    @PostMapping("/save")
    public Result<?> save(@RequestPart("product") String productJson,
                          @RequestPart("image") MultipartFile image) {
        try {
            // 解析 JSON
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(productJson, Product.class);

            // 处理图片
            String picUrl = service.saveImage(image);
            product.setImageUrl(picUrl);

            // 存入数据库
            service.save(product);

            return Result.success();
        } catch (Exception e) {
            return Result.error(400, "上传失败");
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable int id){
        service.removeById(id);
//        service.removeFile() // TODO
        return Result.success();
    }

//    @PutMapping("/update")
//    public Result<?> update(){
//
//    }


}
