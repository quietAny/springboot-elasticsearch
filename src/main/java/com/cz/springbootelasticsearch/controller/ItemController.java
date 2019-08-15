package com.cz.springbootelasticsearch.controller;

import com.cz.springbootelasticsearch.entity.Item;
import com.cz.springbootelasticsearch.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ItemController
 * @date: 2019/8/15  10:26
 * @author: guohao
 * @Description:
 */
@RestController
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 查询所有
     */
    @GetMapping(value = "/items")
    public void findAllItem(){
        Iterable<Item> list = itemService.findAllItem();

        for (Item item:list) {
            System.out.println(item);
        }
    }

    /**
     * 分页查询
     * @param page
     * @param size
     */
    @GetMapping(value = "/items/{page}/{size}")
    public void findItemByPage(@PathVariable("page")int page,@PathVariable("size")int size){

        Page<Item> items = itemService.searchByPage(page,size);

        long total = items.getTotalElements();
        System.out.println("总条数=" + total);
        System.out.println("总页数=" + items.getTotalPages());
        System.out.println("当前页：" + items.getNumber());
        System.out.println("每页大小：" + items.getSize());
        for (Item item : items) {
            System.out.println(item);
        }
    }

    /**
     * 排序查找
     * @param condition
     */
    @GetMapping(value = "/items/{condition}")
    public void findItemBySort(@PathVariable("condition")String condition){

        Page<Item> items = itemService.searchBySort(condition);

        for (Item item : items) {
            System.out.println(item);
        }
    }

    /**
     * 聚合
     * @param aggName
     * @param aggfields
     */
    @PutMapping(value = "/items/agg/{aggName}/{aggfields}")
    public void aggItemByCondition(@PathVariable("aggName")String aggName,@PathVariable()String aggfields){
        List<StringTerms.Bucket> buckets  = itemService.aggItemByCondition(aggName,aggfields);

        for (StringTerms.Bucket bucket : buckets){
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
        }
    }

    /**
     * 根据价格区间查询item
     * @param price1
     * @param price2
     */
    @GetMapping(value = "/items/between/{price1}/{price2}")
    public void findItemBetweenPrice(@PathVariable("price1")double price1,@PathVariable("price2")double price2){
        List<Item> list = itemService.findItemBetweenPrice(price1,price2);

        for (Item item: list) {
            System.out.println(item);
        }
    }
    /**
     * 增加
     * @param item
     */
    @PostMapping(value = "/item")
    public void addItem(Item item){
        item = new Item();
        item.setId(4L);
        item.setTitle("三星MATE20");
        item.setCategory("手机");
        item.setBrand("三星");
        item.setPrice(3100.00);
        item.setImages("http://image.baidu.com/13123.jpg");
        itemService.addItem(item);
    }

    /**
     * 批量增加
     * @param
     */
    @PostMapping(value = "/items")
    public void addItemList( ){
        List<Item> itemList = new ArrayList<>();

        Item item1 = new Item();
        item1.setId(5L);
        item1.setTitle("1+7Pro");
        item1.setCategory("手机");
        item1.setBrand("1+");
        item1.setPrice(2800.00);
        item1.setImages("http://image.baidu.com/13123.jpg");

        itemList.add(item1);

        Item item2 = new Item();
        item2.setId(6L);
        item2.setTitle("红米K20Pro");
        item2.setCategory("手机");
        item2.setBrand("红米");
        item2.setPrice(2500.00);
        item2.setImages("http://image.baidu.com/13123.jpg");

        itemList.add(item2);
        itemService.addItemList(itemList);
    }

    /**
     * 修改：elasticsearch没有修改，它的原理就是先删除再新增，和新增用同一个接口，区别是id
     * @param item
     */
    @PutMapping(value = "/items")
    public void editItem(Item item){
        item = new Item();
        item.setId(5L);
        item.setTitle("1+7Pro");
        item.setCategory("手机");
        item.setBrand("一加");
        item.setPrice(2800.00);
        item.setImages("http://image.baidu.com/13123.jpg");
        itemService.editItem(item);
    }

    /**
     * 删除
     * @param item
     */
    @DeleteMapping(value = "/items")
    public void delete(Item item){
        item = new Item();
        item.setId(5L);

        itemService.deleteItem(item);
    }
}
