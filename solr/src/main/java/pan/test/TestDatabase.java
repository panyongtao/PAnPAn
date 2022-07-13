package pan.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pan.Application;
import pan.pojo.Item;

import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)
public class TestDatabase {
    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //核心名称
    private static final String CORE_NAME = "IK_core";
    @Test
    public void testGetById() throws Exception {
        Optional<Item> itemOptional = solrTemplate.getById(CORE_NAME, 10000L, Item.class);
        Item item = itemOptional.get();
        System.out.println(item.getId());
        System.out.println(item.getTitle());
        System.out.println(item.getPrice());
        System.out.println(item.getImage());

        Map<String, String> spec = item.getSpec();
//        Set<Map.Entry<String, String>> specEntries = spec.entrySet();
//        for (Map.Entry<String, String> specEntry : specEntries) {
//            String specName = specEntry.getKey().replace("_","%");
//            String specValue = specEntry.getValue();
//            System.out.println(URLDecoder.decode(specName, "UTF-8") + ":" + specValue);
//        }
        System.out.println(item.getBrand());
        System.out.println(item.getCategory());
        System.out.println(item.getSeller());
        System.out.println(item.getGoodsId());
        System.out.println(item.getIsDeleted());
        System.out.println(item.getLastModified());
    }
    @Test
   public void testDeleteAll() throws Exception {
        SimpleQuery query = new SimpleQuery("*:*");
        solrTemplate.delete(CORE_NAME, query);
        solrTemplate.commit(CORE_NAME);
    }
    @Test
    public void testAdd() throws Exception {
        //添加动态域内容，Solr7之后版本的动态域，不能使用中文作为拼接索引
        Map<String, String> spec = new HashMap<String, String>();
        spec.put(URLEncoder.encode("屏幕尺寸", "UTF-8"), "19寸");
        spec.put(URLEncoder.encode("内存大小", "UTF-8"), "16G");

        Item item = new Item();
        item.setId(50001L);
        item.setTitle("外星人笔记本");
        item.setPrice(19999.00);
        item.setImage("alien.jpg");
        item.setSpec(spec);
        item.setBrand("外星人");
        item.setCategory("笔记本");
        item.setSeller("外星人官方旗舰店");
        item.setGoodsId(5L);
        item.setIsDeleted(0);
        item.setLastModified(new Date());

        solrTemplate.saveBean(CORE_NAME, item);
        solrTemplate.commit(CORE_NAME);
    }
    @Test
    public void testUpdate() throws Exception {
        //添加动态域内容，Solr7之后版本的动态域，不能使用中文作为拼接索引
        Map<String, String> spec = new HashMap<String, String>();
        spec.put(URLEncoder.encode("屏幕尺寸", "UTF-8"), "19寸");
        spec.put(URLEncoder.encode("内存大小", "UTF-8"), "16G");

        Item item = new Item();
        item.setId(50001L);
        item.setTitle("外星人电视机");
        item.setPrice(19999.00);
        item.setImage("alien.jpg");
        item.setSpec(spec);
        item.setBrand("外星人");
        item.setCategory("电视机");
        item.setSeller("外星人官方旗舰店");
        item.setGoodsId(5L);
        item.setIsDeleted(0);
        item.setLastModified(new Date());

        solrTemplate.saveBean(CORE_NAME, item);
        solrTemplate.commit(CORE_NAME);
    }

    @Test
    public void testDeleteById() throws Exception {
        solrTemplate.deleteByIds(CORE_NAME, "50001");
        solrTemplate.commit(CORE_NAME);
    }
    @Test
    public void testImportAll() throws Exception {
        //查询出所有数据
        String sql = "select * from item where is_deleted = 0";
        List<Item> items = jdbcTemplate.query(sql, new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet resultSet, int i) {
                Item item = new Item();
                try {
                    item.setId(resultSet.getLong("id"));
                    item.setTitle(resultSet.getString("title"));
                    item.setPrice(resultSet.getDouble("price"));
                    item.setImage(resultSet.getString("image"));

                    Map<String, String> spec = new HashMap<String, String>();
                    String specJSON = resultSet.getString("spec");
                    Map specMap = JSON.parseObject(specJSON);
                    Set<Map.Entry<String, String>> specEntries = specMap.entrySet();
                    for (Map.Entry<String, String> specEntry : specEntries) {
                        String specName = URLEncoder.encode(specEntry.getKey(), "UTF-8");
                        String specValue = specEntry.getValue();
                        spec.put(specName, specValue);
                    }
                    item.setSpec(spec);

                    item.setBrand(resultSet.getString("brand"));
                    item.setCategory(resultSet.getString("category"));
                    item.setSeller(resultSet.getString("seller"));
                    item.setGoodsId(resultSet.getLong("goods_id"));
                    item.setIsDeleted(resultSet.getInt("is_deleted"));
                    item.setLastModified(resultSet.getDate("last_modified"));
                } catch (Exception e) { e.printStackTrace(); }
                return item;
            }
        });

        //导入所有数据
        solrTemplate.saveBeans(CORE_NAME, items);
        solrTemplate.commit(CORE_NAME);
    }
    @Test
    public  void testGetByPage() throws Exception {
        int pageCurr = 1;
        int pageSize = 20;

        SimpleQuery query = new SimpleQuery("*:*");
        query.setOffset(Long.valueOf((pageCurr - 1) * pageSize));
        query.setRows(pageSize);
        ScoredPage<Item> items = solrTemplate.queryForPage(CORE_NAME, query, Item.class);
        System.out.println("总页数：" + items.getTotalPages());
        System.out.println("总记录：" + items.getTotalElements());
        for (Item item : items) {
            System.out.println(item);
        }
    }
    @Test
    public void testGetByCriteria() throws Exception {
        int pageCurr = 1;
        int pageSize = 20;

        SimpleQuery query = new SimpleQuery("*:*");
        query.setOffset(Long.valueOf((pageCurr - 1) * pageSize));
        query.setRows(pageSize);

        Criteria criteria = new Criteria("item_title").contains("Pro");
        criteria = criteria.and("item_price").greaterThanEqual(15000);
        criteria = criteria.and("item_price").lessThanEqual(18000);
        query.addCriteria(criteria);

        ScoredPage<Item> items = solrTemplate.queryForPage(CORE_NAME, query, Item.class);
        System.out.println("总页数：" + items.getTotalPages());
        System.out.println("总记录：" + items.getTotalElements());
        for (Item item : items) {
            System.out.println(item);
        }
    }
    @Test
    public void testGetBySort() throws Exception {
        int pageCurr = 1;
        int pageSize = 20;

        SimpleQuery query = new SimpleQuery("*:*");
        query.setOffset(Long.valueOf((pageCurr - 1) * pageSize));
        query.setRows(pageSize);

        query.addSort(Sort.by(Sort.Direction.DESC, "item_goods_id"));

        ScoredPage<Item> items = solrTemplate.queryForPage(CORE_NAME, query, Item.class);
        System.out.println("总页数：" + items.getTotalPages());
        System.out.println("总记录：" + items.getTotalElements());
        for (Item item : items) {
            System.out.println(item);
        }
    }
    @Test
    void testGetByHighlight() throws Exception {
        int pageCurr = 1;
        int pageSize = 20;

        SimpleHighlightQuery query = new SimpleHighlightQuery();
        query.setOffset(Long.valueOf((pageCurr - 1) * pageSize));
        query.setRows(pageSize);

        //设置高亮选项
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);

        //设置搜索条件
        Criteria criteria = new Criteria("item_keywords").contains("华为");
        query.addCriteria(criteria);

        HighlightPage<Item> items = solrTemplate.queryForHighlightPage(CORE_NAME, query, Item.class);
        System.out.println("总页数：" + items.getTotalPages());
        System.out.println("总记录：" + items.getTotalElements());

        //获取高亮入口
        List<HighlightEntry<Item>> highlighted = items.getHighlighted();
        for (HighlightEntry<Item> itemHighlightEntry : highlighted) {
            //获取原来对象
            Item item = itemHighlightEntry.getEntity();
            //获取高亮集合
            List<HighlightEntry.Highlight> highlights = itemHighlightEntry.getHighlights();
            for (HighlightEntry.Highlight highlight : highlights) {
                //获取高亮域
                Field field = highlight.getField();
                //域可能多值
                List<String> snipplets = highlight.getSnipplets();
                for (String snipplet : snipplets) {
                    if ("item_title".equals(field.getName())) {
                        item.setTitle(snipplet);
                    }
                    if ("item_brand".equals(field.getName())) {
                        item.setBrand(snipplet);
                    }
                    if ("item_category".equals(field.getName())) {
                        item.setCategory(snipplet);
                    }
                    if ("item_seller".equals(field.getName())) {
                        item.setSeller(snipplet);
                    }
                }
            }
            //输出高亮对象
            System.out.println(item);
        }
    }


}
