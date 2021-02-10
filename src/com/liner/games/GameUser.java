package com.liner.games;

import com.liner.utils.DB;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unused")
public class GameUser {
    public long id;
    public int timesCheated = 0;

    protected GameUser(long id) {
        this.id = id;
    }


    public int increase(int value, int by) {
        if (by < 0)
            return value;
        return value + by;
    }

    public int decrease(int value, int by) {
        if (by < 0)
            return value;
        return value + -by;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T extends GameUser> T load(long id, Class<T> tClass) {
        String identifier = "game_" + id;
        if (DB.exists(identifier)) {
            return DB.connect(identifier).get(tClass);
        } else {
            T gameUser = null;
            try {
                gameUser = (T) tClass.getConstructors()[0].newInstance(id);
                gameUser.save();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return gameUser;
        }
    }

    public void save() {
        DB.connect("game_" + id).set(this);
    }
}
