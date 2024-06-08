package com.wwlei.authservice.service;

import com.wwlei.authservice.repo.UserAggregateRepo;
import com.wwlei.authservice.repo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class UserService {
    private final UserAggregateRepo userAggregateRepo;


    /**
     * 根据用户ID获取用户聚合体。
     * 此方法标记为只读事务，意味着它不会对数据库进行修改操作。
     * 通过用户ID从用户聚合仓库中检索相应的用户聚合体。
     *
     * @param id 用户的唯一标识符。
     * @return 用户聚合体，包含有关用户的详细信息。
     */
    @Transactional(readOnly = true)
    public User getUserAggregate(Long id) {
        return userAggregateRepo.findByUserId(id);
    }

    /**
     * 创建用户聚合体。
     * 本方法通过调用用户聚合仓库的创建方法，来实现在数据库中创建一个新的用户聚合体。
     * 使用@Transactional注解确保该操作在数据库事务中完成，保证数据的一致性。
     *
     * @param user 待创建的用户对象，包含用户的所有相关信息。
     * @return 创建成功的用户聚合体。
     */
    @Transactional
    public User createUserAggregate(User user) {
        if (userAggregateRepo.isExists(user)) {
            return userAggregateRepo.updateUser(user);
        }
        return userAggregateRepo.createUser(user);
    }


    public String login(User user) throws NoSuchAlgorithmException, InterruptedException {
        User userFromDb = userAggregateRepo.findByUsername(user.getUsername());
        user.verifyPassword(userFromDb);
        return userFromDb.createJWT();
    }

    public User registry(User user) {
        User byUsername = userAggregateRepo.findByUsername(user.getUsername());
        if (byUsername != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        return userAggregateRepo.createUser(user);
    }
}
