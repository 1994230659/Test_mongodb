package nuc.edu.cn.utils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class CollectionOperations {
    private final MongoDatabase database;

    public CollectionOperations(MongoDatabase database) {
        this.database = database;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n请选择集合操作：");
            System.out.println("1. 查看所有集合");
            System.out.println("2. 新建集合");
            System.out.println("3. 删除集合");
            System.out.println("4. 选择集合进行操作");
            System.out.println("5. 返回上一级");
            System.out.print("请输入选项：");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    listCollections();
                    break;
                case 2:
                    createCollection(scanner);
                    break;
                case 3:
                    deleteCollection(scanner);
                    break;
                case 4:
                    selectCollection(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("无效的选项，请重新输入。");
            }
        }
    }

    // 列出所有集合
    private void listCollections() {
        System.out.println("数据库 " + database.getName() + " 中的集合：");
        for (String collectionName : database.listCollectionNames()) {
            System.out.println("- " + collectionName);
        }
    }

    // 新建集合
    private void createCollection(Scanner scanner) {
        System.out.print("请输入新集合的名称：");
        String collectionName = scanner.next();
        database.createCollection(collectionName);
        System.out.println("集合 " + collectionName + " 已创建。");
    }

    // 删除集合
    private void deleteCollection(Scanner scanner) {
        System.out.print("请输入要删除的集合名称：");
        String collectionName = scanner.next();
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.drop();
        System.out.println("集合 " + collectionName + " 已删除。");
    }

    // 选择集合进行文档操作
    private void selectCollection(Scanner scanner) {
        System.out.print("请输入要选择的集合名称：");
        String collectionName = scanner.next();
        MongoCollection<Document> selectedCollection = database.getCollection(collectionName);
        System.out.println("已选择集合: " + collectionName);

        // 将集合传递给 DocumentOperations 进行文档操作
        DocumentOperations documentOps = new DocumentOperations(selectedCollection);
        documentOps.run();
    }
}