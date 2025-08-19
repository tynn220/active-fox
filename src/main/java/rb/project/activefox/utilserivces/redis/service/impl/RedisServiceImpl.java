package rb.project.activefox.utilserivces.redis.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import rb.project.activefox.utilserivces.redis.service.RedisService;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<Object, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public void addObject(String key, Object value, Long expireTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
    }

    @Override
    public Optional<Object> getObject(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {

        return getObject(key).map(o -> {
            try {
                return objectMapper.readValue(o.toString(), clazz);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).orElse(null);

    }
    @Override
    public void removeObject(String key) {
        redisTemplate.delete(key);
    }


}
