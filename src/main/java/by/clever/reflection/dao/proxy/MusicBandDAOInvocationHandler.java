package by.clever.reflection.dao.proxy;

import by.clever.reflection.dao.MusicBandDAO;
import by.clever.reflection.dao.cache.Cache;
import by.clever.reflection.dao.cache.CacheFactory;
import by.clever.reflection.dao.constant.ApplicationYMLParam;
import by.clever.reflection.entity.MusicBand;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of InvocationHandler for MusicBandDAO
 */
public class MusicBandDAOInvocationHandler implements InvocationHandler {

    private final MusicBandDAO musicBandDAO;
    private final CacheFactory<UUID, MusicBand> cacheFactory;

    public MusicBandDAOInvocationHandler(MusicBandDAO musicBandDAO) {
        this.musicBandDAO = musicBandDAO;
        this.cacheFactory = new CacheFactory<>(readCapacity());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String cacheAlgorithm = readCacheAlgorithm();
        Cache<UUID, MusicBand> cache = cacheFactory.getCache(cacheAlgorithm);
        String methodName = method.getName();

        if (MusicBandDAOMethodName.SAVE.equals(methodName)
                || MusicBandDAOMethodName.UPDATE.equals(methodName)) {
            UUID id = (UUID) method.invoke(musicBandDAO, args);
            MusicBand musicBand = (MusicBand) args[0];
            cache.put(id, musicBand);
            return id;
        }
        if (MusicBandDAOMethodName.READ_BY_ID.equals(methodName)) {
            UUID id = (UUID) args[0];
            Optional<MusicBand> musicBand = cache.get(id);

            if (musicBand.isEmpty()) {
                musicBand = Optional.of((MusicBand) method.invoke(musicBandDAO, args));
                cache.put(id, musicBand.orElseThrow());
                return musicBand;
            }
            return musicBand;
        }
        if (MusicBandDAOMethodName.DELETE.equals(methodName)) {
            Object object = method.invoke(musicBandDAO, args);
            UUID id = (UUID) args[0];
            cache.remove(id);
            return object;
        }
        return method.invoke(musicBandDAO, args);
    }

    private int readCapacity() {

        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(ApplicationYMLParam.YML_FILE_NAME);
        Map<String, Object> mapYML = yaml.load(inputStream);

        int capacity = Integer.parseInt(mapYML.get(ApplicationYMLParam.CACHE_CAPACITY).toString());
        return capacity;
    }

    private String readCacheAlgorithm() {

        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(ApplicationYMLParam.YML_FILE_NAME);
        Map<String, Object> mapYML = yaml.load(inputStream);

        String cacheAlgorithm = mapYML.get(ApplicationYMLParam.CACHE_ALGORITHM).toString();
        return cacheAlgorithm;
    }
}
