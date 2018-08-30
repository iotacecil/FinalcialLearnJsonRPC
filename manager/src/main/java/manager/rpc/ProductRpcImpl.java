package manager.rpc;

import api.ProductRpc;
import api.domain.ProductRpcReq;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import entity.Product;
import manager.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AutoJsonRpcServiceImpl
@Service
public class ProductRpcImpl implements ProductRpc {
    private static Logger LOG = LoggerFactory.getLogger(ProductRpcImpl.class);
    @Autowired
    private ProductService productService;

    @Override
    public List<Product> query(ProductRpcReq req) {
        LOG.info("查询多个产品：{}",req );
        Pageable pageable = new PageRequest(0,1000, Sort.Direction.DESC,"rewardRate");
        Page<Product> page = productService.query(req.getIdList(), req.getMinRewardRate(),
                req.getMaxRewardRate(), req.getStatusList(), pageable);
        LOG.info("查询多个结果：{}",page );
        return page.getContent();
    }

    @Override
    public Product findOne(String id) {
        LOG.info("请求id:{}",id);
        Product rst = productService.findOne(id);
        LOG.info("结果id:{}",rst);
        return rst;
    }
}
