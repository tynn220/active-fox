package rb.project.activefox.utilserivces.redis.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    void addObject(String key, Object value, Long expireTime, TimeUnit timeUnit);
    <T> T getObject(String key, Class<T> clazz);
    void removeObject(String key);
    Optional<Object> getObject(String key);
}
