package com.example.springbootwebfluxsecuritymybatis.persistance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final static String USER = "USER_";
    public final static String DEFATLT = "#";
    private User user;

    private UserMapper userMapper;
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setReactiveRedisTemplate(ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return reactiveRedisTemplate.opsForValue().get(USER + username).switchIfEmpty(
            Mono.defer(() -> {
                User user = userMapper.findByUsername(username);
                if (ObjectUtils.isEmpty(user)) {
                    log.error("User not found: {}", username);
//                    return reactiveRedisTemplate.opsForValue().set(USER + username, DEFATLT, Duration.ofHours(1)).then(Mono.empty());
                    return Mono.empty();
                } else {
                    String s = null;
                    try {
                        s = objectMapper.writeValueAsString(user);
                    } catch (JsonProcessingException e) {
                        log.error("ObjectMapper 序列化异常: {}, {}", user, e);
                        s = DEFATLT;
                    }
                    return Mono.just(s);
                }
            })
        ).flatMap(s -> {
            User user = null;
            if (s.equals(DEFATLT)) {
                return Mono.empty();
            }
            try {
                user = objectMapper.readValue(s, User.class);
            } catch (IOException e) {

            }
            return Mono.justOrEmpty(user);
        });
    }
}
