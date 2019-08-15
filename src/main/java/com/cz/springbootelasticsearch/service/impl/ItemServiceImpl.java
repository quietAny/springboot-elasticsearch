package com.cz.springbootelasticsearch.service.impl;

import com.cz.springbootelasticsearch.dao.ItemRepository;
import com.cz.springbootelasticsearch.entity.Item;
import com.cz.springbootelasticsearch.service.ItemService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ItemServiceImpl
 * @date: 2019/8/15  11:10
 * @author: guohao
 * @Description:
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public void addItem(Item item){
        itemRepository.save(item);
    }

    @Override
    public void addItemList(List<Item> itemList) {
        itemRepository.saveAll(itemList);
    }

    @Override
    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

    @Override
    public void editItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Iterable<Item> findAllItem() {
        return itemRepository.findAll(Sort.by("price").ascending());
    }

    @Override
    public List<Item> findItemBetweenPrice(double price1, double price2) {
        return itemRepository.findByPriceBetween(price1,price2);
    }

    @Override
    public Page<Item> searchByPage(int page, int size) {
        //构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category","手机"));
        //分页
        queryBuilder.withPageable(PageRequest.of(page,size));

        Page<Item> items = itemRepository.search(queryBuilder.build());

        return items;
    }

    @Override
    public Page<Item> searchBySort(String condition) {
        //构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category","手机"));
        //排序
        queryBuilder.withSort(SortBuilders.fieldSort(condition).order(SortOrder.ASC));

        Page<Item> items = itemRepository.search(queryBuilder.build());

        return items;
    }

    @Override
    public List<StringTerms.Bucket> aggItemByCondition(String aggName,String aggfields) {
        //构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""},null));
        //1.创建一个新的聚合，聚合查询为terms，聚合名称为为aggName,聚合字段为aggfields
        queryBuilder.addAggregation(AggregationBuilders.terms(aggName).field(aggfields));
        //2.查询，需要把结果强转成AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>)itemRepository.search(queryBuilder.build());
        //3.解析
        //3.1 从结果中取出名为brands的那个集合
        //因为是利用String类型字段进行term聚合，所以结果要转为StringTerm类型
        StringTerms agg = (StringTerms)aggPage.getAggregation(aggName);
        //3.2 获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        return buckets;
    }


}
