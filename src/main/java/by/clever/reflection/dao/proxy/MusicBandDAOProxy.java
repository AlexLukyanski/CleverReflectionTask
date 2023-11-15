package by.clever.reflection.dao.proxy;

import by.clever.reflection.dao.MusicBandDAO;
import by.clever.reflection.dao.impl.MusicBandDAOImpl;

import java.lang.reflect.Proxy;

public class MusicBandDAOProxy {

    private final static MusicBandDAO musicBandDAO = new MusicBandDAOImpl();

    public static MusicBandDAO getInstance() {
        return (MusicBandDAO) Proxy.newProxyInstance(
                musicBandDAO.getClass().getClassLoader(),
                musicBandDAO.getClass().getInterfaces(),
                new MusicBandDAOInvocationHandler(musicBandDAO));
    }
}
