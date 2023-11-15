package by.clever.reflection.dao.proxy;

import by.clever.reflection.dao.MusicBandDAO;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MusicBandDAOInvocationHandler implements InvocationHandler {

    private final MusicBandDAO musicBandDAO;

    public MusicBandDAOInvocationHandler(MusicBandDAO musicBandDAO) {
        this.musicBandDAO = musicBandDAO;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return method.invoke(musicBandDAO, args);
    }
}
