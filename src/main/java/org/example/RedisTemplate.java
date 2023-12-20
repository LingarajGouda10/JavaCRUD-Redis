package org.example;
import java.util.List;
import java.util.Map;

public class RedisTemplate {

    private static final String HASH_KEY = "Product";



    public String save(Product product) {
            RedisConfig.redisTemplate.opsForHash().put(HASH_KEY,product.getId(),product);
            return "successfully";
        }

        public List<Object> findAll() {
            return RedisConfig.redisTemplate.opsForHash().values(HASH_KEY);
        }

        public Object findProductById(int id) {
            System.out.println("called findProductById() from DB");
            return  RedisConfig.redisTemplate.opsForHash().get(HASH_KEY, id);
        }

        public String deleteProductById(int id) {
            RedisConfig.redisTemplate.opsForHash().delete(HASH_KEY, id);
            return "Product Deleted Successfully";
        }

    }


