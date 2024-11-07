package nuc.edu.cn.utils;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DocumentOperations {
    private final MongoCollection<Document> collection;

    public DocumentOperations(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n请选择文档操作：");
            System.out.println("1. 查询所有文档");
            System.out.println("2. 插入文档");
            System.out.println("3. 更新文档");
            System.out.println("4. 删除文档");
            System.out.println("5. 返回上一级");
            System.out.print("请输入选项：");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    findDocuments();
                    break;
                case 2:
                    addDocument(scanner);
                    break;
                case 3:
                    updateDocument(scanner);
                    break;
                case 4:
                    deleteDocument(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("无效的选项，请重新输入。");
            }
        }
    }

    // 查询所有文档
    private void findDocuments() {
        System.out.println("查询所有文档：");
        List<Document> documents = collection.find().into(new ArrayList<>());
        for (Document doc : documents) {
            System.out.println(doc.toJson());
        }
    }

    // 插入文档
    private void addDocument(Scanner scanner) {
        System.out.print("请输入文档的 _id: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符
        System.out.print("请输入文档的 name: ");
        String name = scanner.nextLine();
        System.out.print("请输入文档的 age: ");
        int age = scanner.nextInt();
        Document document = new Document("_id", id)
                .append("name", name)
                .append("age", age);
        collection.insertOne(document);
        System.out.println("插入文档: " + document.toJson());
    }

    private void updateDocument(Scanner scanner) {
        System.out.print("请输入要更新的文档的 _id: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符
        System.out.print("请输入新的 name: ");
        String newName = scanner.nextLine();
        System.out.print("请输入新的 age: ");
        int newAge = scanner.nextInt();
        Document update = new Document("$set", new Document("name", newName).append("age", newAge));
        collection.updateOne(new Document("_id", id), update);
        System.out.println("更新文档 _id: " + id + " with " + update.toJson());
    }

    private void deleteDocument(Scanner scanner) {
        System.out.print("请输入要删除的文档的 _id: ");
        int id = scanner.nextInt();
        collection.deleteOne(new Document("_id", id));
        System.out.println("删除文档 _id: " + id);
    }
}