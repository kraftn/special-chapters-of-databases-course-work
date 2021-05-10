package ru.databases.client.utils;

import javafx.scene.image.Image;
import redis.clients.jedis.Jedis;

public class RedisManager {
    private final Jedis jedis;
    private static RedisManager instance;

    private RedisManager() {
        jedis = new Jedis();
        jedis.configSet("SAVE", "");
    }

    public static RedisManager getInstance() {
        if (instance == null) {
            instance = new RedisManager();
        }
        return instance;
    }

    public static void close() {
        if (instance != null) {
            instance.jedis.close();
            instance = null;
        }
    }

    public Image loadImage(Class<?> itemClass, String itemId) {
        String data = jedis.get(getClassName(itemClass) + itemId);
        if (data != null) {
            return ImageUtils.decodeToImage(data);
        } else {
            return null;
        }
    }

    public void saveImage(Class<?> itemClass, String itemId, Image image) {
        jedis.set(getClassName(itemClass) + itemId, ImageUtils.encodeToString(image, "png"));
        jedis.save();
    }

    public void deleteImage(Class<?> itemClass, String itemId) {
        jedis.del(getClassName(itemClass) + itemId);
        jedis.save();
    }

    private String getClassName(Class<?> itemClass) {
        String fullClassName = itemClass.getName();
        return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
    }
}
