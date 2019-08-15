package com.cz.springbootelasticsearch.dao;

import com.cz.springbootelasticsearch.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

    public List<Item> findByTitleLike(String title);

    public List<Item> findByPriceBetween(double price1,double price2);
}
