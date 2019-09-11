package com.qjl.dao;

import com.qjl.domain.Order;
import com.qjl.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderDao {

    @Select("select * from orders")
    @Results({
            @Result(property = "product", column = "productId", javaType = Product.class,
                    one = @One(select = "com.qjl.dao.ProductDao.findById") )
    })
    List<Order> findAll();

    @Insert("insert into orders values(order_seq.nextval ,#{orderNum}, #{orderTime}  ,#{peopleCount},#{orderDesc}, #{payType} , #{orderStatus} , #{product.id})")
    void save(Order order);
}
