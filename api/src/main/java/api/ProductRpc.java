package api;

import com.googlecode.jsonrpc4j.JsonRpcService;
import api.domain.ProductRpcReq;
import entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@JsonRpcService("rpc/products")
public interface ProductRpc {
    /**
     * 查询多个产品
     * @param req
     * @return
     */
    List<Product> query(ProductRpcReq req);

    /**
     * 查单个产品
     * @param id
     * @return
     */
    Product findOne(String id);
}
