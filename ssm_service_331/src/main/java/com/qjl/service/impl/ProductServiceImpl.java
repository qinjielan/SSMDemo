package com.qjl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qjl.dao.ProductDao;
import com.qjl.domain.Product;
import com.qjl.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    ProductDao productDao;

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public Product findById(Integer id) {
        return productDao.findById(id);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public void delOne(Integer id) {
        productDao.delById(id);
    }

    @Override
    public void delMany(Integer[] ids) {
        if (ids != null) {
            for (Integer id : ids) {
                productDao.delById(id);
            }
        }
    }

    @Override
    public PageInfo<Product> findByPageHelper(Integer currPage, Integer pageSize) {
        //指定分页参数
        PageHelper.startPage(currPage, pageSize);
        List<Product> productList = productDao.findAll();
        PageInfo<Product> pageInfo = new PageInfo<>(productList, 3);
        return pageInfo;
    }
}
