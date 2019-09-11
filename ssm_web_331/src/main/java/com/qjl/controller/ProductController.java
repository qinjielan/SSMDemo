package com.qjl.controller;

import com.github.pagehelper.PageInfo;
import com.qjl.domain.Product;
import com.qjl.service.ProductService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/product")
@Secured({"ROLE_ADMIN","ROLE_USER"})
public class ProductController {

    @Resource
    ProductService productService;

    @RequestMapping("/findAll")
    public ModelAndView findAll(
            @RequestParam(value = "currPage",required = false, defaultValue = "1") Integer currPage,
            @RequestParam(value = "pageSize",required = false, defaultValue = "5") Integer pageSize){
        PageInfo<Product> pageInfo = productService.findByPageHelper(currPage, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.setViewName("product-list");
        return modelAndView;
    }

    @RequestMapping("/save")
    public String save(Product product){
        productService.save(product);
        return "redirect:/product/findAll";
    }

    @RequestMapping("/showUI")
    public ModelAndView showUI(Integer id){
        Product product = this.productService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product",product);
        modelAndView.setViewName("product-show");
        return modelAndView;
    }

    @RequestMapping("/updateUI")
    public ModelAndView updateUI(Integer id){
        Product product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", product);
        modelAndView.setViewName("product-update");
        return  modelAndView;
    }

    @RequestMapping("/update")
    public String update(Product product){
        productService.update(product);
        return "redirect:/product/findAll";
    }

    @RequestMapping("/delOne")
    public String delOne(Integer id){
        productService.delOne(id);
        return "redirect:/product/findAll";
    }

    @RequestMapping("/delMany")
    public String delMany(Integer[] ids){
        productService.delMany(ids);
        return "redirect:/product/findAll";
    }
}
