package manager.service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import entity.Product;
import entity.enums.ProductStatus;
import manager.error.ErrorEnum;
import manager.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import javax.swing.text.html.HTMLDocument;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    private static Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repository;

    /**
     * 添加
     * @param product
     * @return
     */
    public Product addProduct(Product product){
        LOG.debug("创建产品，参数：{}",product);
        //数据校验
        checkProduct(product);
        setDefault(product);
        Product rst = repository.save(product);
        LOG.debug("创建产品，结果:{}",rst);
        return rst;
    }

    /**
     * 产品：编号 步长 收益率 校验
     * @param product
     */
    private void checkProduct(Product product) {
        Assert.notNull(product.getId(), ErrorEnum.ID_NOT_NULL.getCode());
        Assert.isTrue(BigDecimal.ZERO.compareTo(product.getRewardRate())<0&&BigDecimal.valueOf(30).compareTo(product.getRewardRate())>=0,"收益率范围错误" );
        Assert.isTrue(BigDecimal.valueOf(product.getStepAmount().longValue()).compareTo(product.getStepAmount())==0, "投资步长需为整数");
    }

    /**
     * 产品默认值：创建更新时间，步长，状态，锁定期
     * @param product
     */
    public void setDefault(Product product) {
        if(product.getCreateAt()==null){
            product.setCreateAt(new Date());
        }
        if(product.getUpdateAt()==null){
            product.setUpdateAt(new Date());
        }
        if(product.getStepAmount()==null){
            product.setStepAmount(BigDecimal.ZERO);
        }
        if(product.getLockTerm()== null){
            product.setLockTerm(0);
        }
        if(product.getStatus()==null){
            product.setStatus(ProductStatus.AUDITING.name());
        }
    }

    /**
     * 查询
     * @param id
     * @return
     */
    public Product findOne(String id){
        Assert.notNull(id,"需要产编号参数");
        LOG.debug("查询单个产品,id={}",id);
        Product product = repository.findById(id).get();
        LOG.debug("查询单个产品,结果={}",product);
        return product;
    }

    public Page<Product> query(List<String> idList,
                               BigDecimal minRewardRate, BigDecimal maxRewardRate,
                               List<String> statusList,
                               Pageable pageable){
        LOG.debug("查询产品,idList={},min={},max:{},status={},pageable={}",idList,minRewardRate,maxRewardRate,statusList,pageable);
        Specification<Product> specification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //获得列
                Expression<String> idCol = root.get("id");
                Expression<BigDecimal> rewardRateCol = root.get("rewardRate");
                Expression<String> statusCol = root.get("status");
                //断言列表
                List<Predicate> predicates = new ArrayList<>();
                if(idList!=null &&idList.size()>0){
                    predicates.add(idCol.in(idList));
                }
                if(minRewardRate!=null&&BigDecimal.ZERO.compareTo(minRewardRate)<0){
                    predicates.add(cb.ge(rewardRateCol,minRewardRate));
                }
                if(maxRewardRate!=null&&BigDecimal.ZERO.compareTo(maxRewardRate)<0){
                    predicates.add(cb.le(rewardRateCol,maxRewardRate));
                }
                if(statusList!=null&&statusList.size()>0){
                    predicates.add(statusCol.in(statusList));
                }
                query.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        };
        Page<Product> page = repository.findAll(specification, pageable);

        LOG.debug("查询产品，结果={}",page);

        return page;
    }
}
