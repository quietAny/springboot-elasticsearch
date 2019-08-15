package com.cz.springbootelasticsearch.service;

import com.cz.springbootelasticsearch.entity.Item;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @ClassName: ItemService
 * @date: 2019/8/15  11:10
 * @author: guohao
 * @Description:
 */
public interface ItemService {
    void addItem(Item item);

    void addItemList(List<Item> itemList);

    void deleteItem(Item item);

    void editItem(Item item);

    Iterable<Item> findAllItem();

    List<Item> findItemBetweenPrice(double price1, double price2);

    Page<Item> searchByPage(int page,int size);

    Page<Item> searchBySort(String condition);

    List<StringTerms.Bucket> aggItemByCondition(String aggName, String aggfields);
}
