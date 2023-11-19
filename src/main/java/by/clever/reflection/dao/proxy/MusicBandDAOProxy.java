package by.clever.reflection.dao.proxy;

import by.clever.reflection.dao.MusicBandDAO;
import by.clever.reflection.dao.impl.MusicBandDAOImpl;

import java.lang.reflect.Proxy;

/**
 * Proxy for MusicBandDAO
 */
public class MusicBandDAOProxy {

    private MusicBandDAOProxy() {

    }

    public static MusicBandDAO getInstance() {

        return MusicBandDAOProxyHelper.proxyMusicBandDAO;
    }

    private static class MusicBandDAOProxyHelper {
        private final static MusicBandDAO musicBandDAO = new MusicBandDAOImpl();

        private static final MusicBandDAO proxyMusicBandDAO = (MusicBandDAO) Proxy.newProxyInstance(
                musicBandDAO.getClass().getClassLoader(),
                musicBandDAO.getClass().getInterfaces(),
                new MusicBandDAOInvocationHandler(musicBandDAO));
    }
}
