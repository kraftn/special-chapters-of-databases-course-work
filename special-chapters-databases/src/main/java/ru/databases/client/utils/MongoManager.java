package ru.databases.client.utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import ru.databases.client.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MongoManager {
    private final MongoClient client;
    private final MongoDatabase database;
    private String user;
    private static MongoManager instance;

    private MongoManager(String user, String pwd) {
        this.user = user;

        ConnectionString connectionSting = new ConnectionString("mongodb://localhost:27017");
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);

        MongoCredential mongoCredential = MongoCredential.createCredential(user, "special_chapters_databases", pwd.toCharArray());

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionSting)
                .codecRegistry(codecRegistry)
                .credential(mongoCredential)
                .build();

        client = MongoClients.create(clientSettings);
        database = client.getDatabase("special_chapters_databases");
    }

    public static MongoManager getInstance(String user, String pwd) {
        MongoManager.close();
        instance = new MongoManager(user, pwd);
        return instance;
    }

    public static MongoManager getInstance() {
        return instance;
    }

    public static void close() {
        if (instance != null) {
            instance.client.close();
            instance = null;
        }
    }

    public String getUser() {
        return user;
    }

    private <T extends Item> MongoCollection<T> getCollection(Class<T> type) {
        String collectionName = "";
        if (type == Coin.class) {
            collectionName = "coins";
        } else if (type == Stamp.class) {
            collectionName = "stamps";
        } else if (type == Book.class) {
            collectionName = "books";
        } else if (type == Painting.class) {
            collectionName = "paintings";
        }
        return database.getCollection(collectionName, type);
    }

    public <T extends Item> List<T> getItems(Class<T> type) {
        return getCollection(type).find().into(new ArrayList<>());
    }

    private <T extends Item> List<T> getItems(Class<T> type, Bson filter) {
        return getCollection(type).find(filter).into(new ArrayList<>());
    }

    public <T extends Item> void insert(Class<T> type, T item) {
        getCollection(type).insertOne(item);
    }

    public <T extends Item> void update(Class<T> type, T item) {
        Document filter = new Document("_id", item.getId());
        getCollection(type).replaceOne(filter, item);
    }

    public <T extends Item> void delete(Class<T> type, T item) {
        Document filter = new Document("_id", item.getId());
        getCollection(type).deleteOne(filter);
    }

    public List<Item> findItemsByDate(LocalDate begin, LocalDate end) {
        List<Item> res = new ArrayList<>();
        Bson filter = Filters.and(Filters.gte("entering_date", begin),
                Filters.lte("entering_date", end));

        res.addAll(getItems(Coin.class, filter));
        res.addAll(getItems(Stamp.class, filter));
        res.addAll(getItems(Book.class, filter));
        res.addAll(getItems(Painting.class, filter));

        return res;
    }

    public List<Item> findItemsByWorth(Double start, Double end) {
        List<Item> res = new ArrayList<>();
        Bson filter = Filters.and(Filters.gte("worth", start), Filters.lte("worth", end));

        res.addAll(getItems(Coin.class, filter));
        res.addAll(getItems(Stamp.class, filter));
        res.addAll(getItems(Book.class, filter));
        res.addAll(getItems(Painting.class, filter));

        return res;
    }

    public <T extends Item> Double calculateTotalWorth(Class<T> itemClass) {
        Double res = 0.0;
        List<T> items = getItems(itemClass);
        for (T item : items) {
            res += item.getWorth();
        }
        return res;
    }

    public <T extends Item> List<T> findItemsByEdition(Class<T> itemClass, Long start, Long end) {
        Bson filter = Filters.and(Filters.gte("edition", start), Filters.lte("edition", end));
        return getItems(itemClass, filter);
    }

    public <T extends Item> List<T> findItemsBySeries(Class<T> itemClass, String seriesName) {
        Bson filter = Filters.eq("series", seriesName);
        return getItems(itemClass, filter);
    }

    public <T extends Item> List<T> findItemsByArtist(Class<T> itemClass, Person artist) {
        Bson filter = Filters.eq("artist", artist);
        return getItems(itemClass, filter);
    }

    public <T extends Item> List<T> findItemsByAuthor(Class<T> itemClass, Person author) {
        Bson filter = Filters.eq("author", author);
        return getItems(itemClass, filter);
    }

    public String getRoleName() {
        Document authInfo = database.runCommand(new Document("connectionStatus", 1)).get("authInfo", Document.class);
        Document roleInfo = authInfo.getList("authenticatedUserRoles", Document.class).get(0);
        return roleInfo.getString("role");
    }

    public boolean isWritingNecessary() {
        return getRoleName().equals("readWrite");
    }
}
