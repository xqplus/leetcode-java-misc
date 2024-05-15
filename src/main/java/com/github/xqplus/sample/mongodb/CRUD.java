package com.github.xqplus.sample.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

public class CRUD {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase dbInventory = mongoClient.getDatabase("inventory");
        MongoCollection<Document> collection = dbInventory.getCollection("col");
        InsertOneResult insertOneResult = collection.insertOne(new Document("name", "zs"));
    }
}
