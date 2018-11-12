package manager.controller;


import entity.Product;
import entity.enums.ProductStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import util.RestUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
//随机端口
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    private static RestTemplate rest  =new RestTemplate();

    @Value("http://localhost:${local.server.port}/manager")
    private String baseUrl;

    //正常的测试用例
    private static List<Product> normals = new ArrayList<>();
    //异常测试用例
    private static List<Product> exceptions = new ArrayList<>();

    @BeforeClass
    public static void init(){
        Product p1 = new Product("T001", "灵活宝1号", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(3.42));
        Product p2 = new Product("T002", "活期盈-金色人生", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(0), BigDecimal.valueOf(3.28));
        Product p3 = new Product("T003", "朝朝盈-聚财", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(3.86));
        normals.add(p1);
        normals.add(p2);
        normals.add(p3);
        //异常
        Product e1 = new Product(null, "编号空", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(3.42));
        Product e2 = new Product("E002", "收益>30", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(0), BigDecimal.valueOf(31));
        Product e3 = new Product("E003", "投资步长是小数", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(100), BigDecimal.valueOf(1.01), BigDecimal.valueOf(3.86));
        exceptions.add(e1);
        exceptions.add(e2);
        exceptions.add(e3);
//        ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
//            @Override
//            public boolean hasError(ClientHttpResponse response) throws IOException {
//                return false;
//            }
//
//            @Override
//            public void handleError(ClientHttpResponse response) throws IOException {
//
//            }
//        }
    }



    @Test
    public void create(){
        normals.forEach(product -> {
            Product rst = RestUtil.postJSON(rest, baseUrl + "/products", product, Product.class);
            Assert.notNull(rst.getCreateAt(),"插入失败" );
        });

    }
    @Test
    public void createError(){

        exceptions.forEach(product -> {
            Product rst = RestUtil.postJSON(rest, baseUrl + "/products", product, Product.class);
            Assert.notNull(rst.getCreateAt(),"插入失败" );
        });
    }
}
