package nuc.edu.cn.client;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import nuc.edu.cn.utils.DatabaseOperations;

import java.util.Scanner;

public class Client {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";

    public static void main(String[] args) {
        MongoClient mongoClient = null;
        try {
            // 创建 MongoClient 实例
            mongoClient = MongoClients.create(CONNECTION_STRING);

            // 调用 DatabaseOperations 来进行数据库操作
            DatabaseOperations dbOps = new DatabaseOperations(mongoClient);
            dbOps.run();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mongoClient != null) {
                // 关闭客户端连接
                mongoClient.close();
            }
        }
    }
}