package org.example;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;

import java.util.List;


public class RedisTemplate {

    private static final String PRIMARY_KEY = "Product";
    private static final String SECONDARY_KEY = "ProductIdSet";


    public String save(Product product) {
        BoundValueOperations<String, Object> boundValueOps = RedisConfig.redisTemplate.boundValueOps(getPrimaryKey
                (product.getId()));
        boundValueOps.set(product);
        BoundSetOperations<String, Object> boundSetOps = RedisConfig.redisTemplate.boundSetOps(SECONDARY_KEY);
        boundSetOps.add(String.valueOf(product.getId()));
        return "successfully";
    }

    public List<Object> findAll() {
        return RedisConfig.redisTemplate.opsForHash().values(PRIMARY_KEY);
    }

    public Object findProductById(int id) {
        BoundValueOperations<String, Object> boundValueOps = RedisConfig.redisTemplate.boundValueOps
                (getPrimaryKey(id));
        System.out.println("called findProductById() from DB");
        return boundValueOps.get();
    }

    public String deleteProductById(int id) {
        RedisConfig.redisTemplate.delete(getPrimaryKey(id));
        BoundSetOperations<String, Object> boundSetOps = RedisConfig.redisTemplate.boundSetOps(SECONDARY_KEY);
        boundSetOps.getOperations().boundSetOps(SECONDARY_KEY).remove(String.valueOf(id));
        return "Product Deleted Successfully";
    }

    private String getPrimaryKey(int id) {
        return PRIMARY_KEY + ":" + id;

    }
}


