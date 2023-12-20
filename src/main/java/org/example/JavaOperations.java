package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class JavaOperations {
    private static final Logger logger = LoggerFactory.getLogger(JavaOperations.class);
    public static void main(String [] args) throws JsonProcessingException {
        RedisConfig.redisTemplate = RedisConfig.redisTemplate();
    Scanner sc = new Scanner(System.in);
        logger.info("Welcome to Product Managment");
        while (true) {
        logger.info("1. Add Product\n" +
                "2. List all Product \n" +
                "3. List Product on ID \n" +
                "4. Delete Product based on ID \n" );

        System.out.println("Enter Choice: ");
        int ch = sc.nextInt();
        RedisTemplate redisTemplate=new RedisTemplate();
            Product product = new Product();
        switch (ch) {
            case 1:
                logger.info("enter the Id");
                int id = sc.nextInt();
                logger.info("name");
                String productName = sc.next();
                logger.info("price");
                int productPrice = sc.nextInt();
                logger.info("quantity");
                int productQuantity = sc.nextInt();
                product.setId(id);
                product.setName(productName);
                product.setPrice(productPrice);
                product.setQuantity(productQuantity);
                redisTemplate.save(product);
                break;
            case 2:
                logger.info("All product from Database");
                List<Object>products =redisTemplate.findAll();
                for (int i=0;i<products.size();i++){
                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(products.get(i));
                    System.out.println(json);
                }
                break;
                case 3:
                    logger.info("enter the ID for RequiredData");
                    int keyForData = sc.nextInt();
                    ObjectMapper mapper=new ObjectMapper();
                    String json= mapper.writeValueAsString(redisTemplate.findProductById(keyForData));
                    System.out.println(json);
                    break;
            case 4:
                  System.out.println("Enter the id to delete the product");
                  int delete = sc.nextInt();
                  mapper = new ObjectMapper();
                  json = mapper.writeValueAsString(redisTemplate.deleteProductById(delete));
                  System.out.println(json);
                  break;

            default:
                System.out.println("select above mentioned");
        }
        System.out.println("do you want to continue click 1 or stop click 0");
        int p = sc.nextInt();
        if (p == 0) {
            break;
        }
        }
    }

}
