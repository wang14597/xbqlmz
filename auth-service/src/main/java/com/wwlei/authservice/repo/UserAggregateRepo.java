package com.wwlei.authservice.repo;

import com.wwlei.authservice.repo.jpa.UserRepository;
import com.wwlei.authservice.repo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserAggregateRepo {
    private final UserRepository userRepository;

    /**
     * 根据用户ID查找用户。
     * 此方法通过用户仓库查找指定ID的用户。如果用户存在，则返回该用户对象；
     * 如果用户不存在，则抛出IllegalArgumentException异常，指示用户未找到。
     *
     * @param id 用户的唯一标识符。
     * @return 找到的用户对象。
     * @throws IllegalArgumentException 如果用户ID不存在，则抛出此异常。
     */
    public User findByUserId(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() ->new IllegalArgumentException("user not find"));
    }

    /**
     * 创建一个新的用户。
     * 通过调用userRepository的save方法来保存传入的User对象。
     * 如果User对象是新的，将会被插入到数据库中；
     * 如果User对象已经存在，将会根据实际情况进行更新。
     *
     * @param user 待创建的用户对象，包含用户的全部信息。
     * @return 保存后的用户对象。这个对象可能是新创建的，也可能是已存在的对象。
     */
    public User createUser(User user) {
        if (user.getId() != null && userRepository.existsById(user.getId())) {
            // update
            return userRepository.save(user);
        }
        // insert
        user.encryptPassword();
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElseThrow(() ->new IllegalArgumentException("user not find"));
    }
}
