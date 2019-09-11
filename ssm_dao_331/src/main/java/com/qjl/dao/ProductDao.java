package com.qjl.dao;

import com.qjl.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductDao {

    @Select("select * from product")
    List<Product> findAll();

    @Insert("insert into product values(#{id} , #{productNum},#{productName},#{cityName},#{departureTime}, #{productPrice},#{productDesc}, #{productStatus})")
    @SelectKey(keyProperty = "id",keyColumn = "id",before = true,resultType = Integer.class,
            statement = "select product_seq.nextval from dual")
    void save(Product product);

    @Select("select p.*,to_char(departureTime,'yyyy-mm-dd hh24:mi') departureTimeStr from product p where id = #{id}")
    Product findById(Integer id);

    @Update("update product set productNum=#{productNum},productName=#{productName},cityName=#{cityName},departureTime=#{departureTime}, productPrice=#{productPrice},productDesc=#{productDesc}, productStatus=#{productStatus} where id = #{id}")
    void update(Product product);

    @Delete("delete from product where id = #{id}")
    void delById(Integer id);
}
