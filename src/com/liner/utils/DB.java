package com.liner.utils;

import com.google.gson.Gson;
import com.sun.istack.internal.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class DB implements Serializable {
    private static DB DB;
    private static File directory;
    private static String filename;

    public static DB init(String directoryName) {
        DB = new DB();
        DB.directory = new File(System.getProperty("user.dir") + File.separator + directoryName);
        if (!DB.directory.exists()) {
            DB.directory.mkdirs();
        }
        return DB;
    }

    public static DB connect(String name) {
        if (name == null)
            return DB;
        DB.filename = name + ".json";
        File file = new File(directory, filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return DB;
    }


    @Nullable
    public <T> T get(Class<T> clazz) {
        return (T) new Gson().fromJson(FileUtils.readFile(new File(directory, filename)), clazz);
    }


    @Nullable
    public <T> List<T> getList(Class<T> clazz) {
        List<T> list = new Gson().fromJson(FileUtils.readFile(new File(directory, filename)), new ListType<>(clazz));
        return list == null ? new ArrayList<>() : list;
    }

    public <T> T set(T object) {
        FileUtils.writeFile(new Gson().toJson(object), new File(directory, filename));
        return null;
    }


    public <T> List<T> all(Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (File file : directory.listFiles()) {
            String gson = FileUtils.readFile(new File(directory, file.getName()));
            if(gson.startsWith("{")){
                list.add(new Gson().fromJson(FileUtils.readFile(new File(directory, file.getName())), clazz));
            }
        }
        return list;
    }

    public static boolean exists(String name) {
        return FileUtils.containFile(directory, new String[]{name});
    }
}