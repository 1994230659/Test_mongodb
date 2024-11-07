package nuc.edu.cn.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import java.util.Scanner;

public class DatabaseOperations {
    private final MongoClient mongoClient;

    public DatabaseOperations(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n请选择数据库操作：");
            System.out.println("1. 查看所有数据库");
            System.out.println("2. 新建数据库");
            System.out.println("3. 删除数据库");
            System.out.println("4. 选择数据库进行操作");
            System.out.println("5. 退出");
            System.out.print("请输入选项：");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    listDatabases();
                    break;
                case 2:
                    createDatabase(scanner);
                    break;
                case 3:
                    deleteDatabase(scanner);
                    break;
                case 4:
                    selectDatabase(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("无效的选项，请重新输入。");
            }
        }
    }

    // 列出所有数据库
    private void listDatabases() {
        MongoIterable<String> databases = mongoClient.listDatabaseNames();
        System.out.println("可用的数据库列表：");
        for (String dbName : databases) {
            System.out.println("- " + dbName);
        }
    }

    // 新建数据库
    private void createDatabase(Scanner scanner) {
        System.out.print("请输入新数据库的名称：");
        String dbName = scanner.next();
        MongoDatabase newDatabase = mongoClient.getDatabase(dbName);
        System.out.print("请输入新集合的名称：");
        String collectionName = scanner.next();
        newDatabase.createCollection(collectionName);
        System.out.println("数据库 " + dbName + " 和集合 " + collectionName + " 已创建。");
    }

    // 删除数据库
    private void deleteDatabase(Scanner scanner) {
        System.out.print("请输入要删除的数据库名称：");
        String dbName = scanner.next();
        MongoDatabase db = mongoClient.getDatabase(dbName);
        db.drop();
        System.out.println("数据库 " + dbName + " 已删除。");
    }

    // 选择数据库进行操作
    private void selectDatabase(Scanner scanner) {
        System.out.print("请输入要选择的数据库名称：");
        String dbName = scanner.next();
        MongoDatabase selectedDb = mongoClient.getDatabase(dbName);
        System.out.println("已选择数据库: " + dbName);

        // 将数据库传递给 CollectionOperations 进行集合操作
        CollectionOperations collectionOps = new CollectionOperations(selectedDb);
        collectionOps.run();
    }
}