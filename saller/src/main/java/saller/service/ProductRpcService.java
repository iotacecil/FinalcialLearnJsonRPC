package saller.service;

import api.ProductRpc;
import api.domain.ProductRpcReq;
import entity.Product;
import entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductRpcService {
    private static Logger LOG = LoggerFactory.getLogger(ProductRpcService.class);

    @Autowired
    private ProductRpc productRpc;


    /**
     * 查询全部产品 暂时不分页返回
     * @return
     */
    public List<Product> findAll(){
        ProductRpcReq req = new ProductRpcReq();
        List<String> status = new ArrayList<>();
        //只能查询销售中的
        status.add(ProductStatus.IN_SELL.name());
//        Pageable pagealbe = new PageRequest(0, 1000, Sort.Direction.DESC, "rewardRate");
        req.setStatusList(status);
        LOG.info("rpc查询全部产品 请求:{}",req);
        List<Product> result = productRpc.query(req);
        LOG.info("rpc查询全部产品 结果:{}",result);
        return result;
    }
    @PostConstruct
    public void testFindAll(){
        findAll();
    }

    public Product findOne(String id){
        LOG.info("单个产品请求:{}", id);
        Product rst = productRpc.findOne(id);
        LOG.info("单个产品 结果:{}", rst);
        return rst;

    }
    @PostConstruct
    public void testfindone(){
        findOne("001");
    }

    public static void main(String[] args) throws MalformedURLException {
        URL baseUrl = new URL("http://localhost:8081/manager/");
        String path = "/rpc/products";
        System.out.println(new URL(baseUrl, path).toString());
    }
}
