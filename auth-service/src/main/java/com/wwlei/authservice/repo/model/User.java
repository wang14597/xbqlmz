package com.wwlei.authservice.repo.model;

import com.wwlei.common.utils.JwtTokenProvider;
import com.wwlei.common.utils.PasswordUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String salt;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean enabled = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_permissions",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    /**
     * 对密码进行加密处理。
     * 使用密码加密工具类生成随机盐值，然后将盐值与原始密码结合，对密码进行加密。
     * 如果在生成盐值或加密过程中遇到算法相关的问题，将抛出运行时异常。
     *
     * @throws RuntimeException 如果加密过程中出现算法相关异常。
     */
    public void encryptPassword() {
        try {
            String generateSalt = PasswordUtil.generateSalt();
            this.setSalt(generateSalt);
            this.setPassword(PasswordUtil.hashPassword(this.getPassword(), generateSalt));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void validate() {
        if (StringUtils.isEmpty(this.getUsername()) || StringUtils.isEmpty(this.getPassword())) {
            throw new RuntimeException("user name and password can not empty");
        }
    }

    public String createJWT() {
        StringBuilder roleStr = new StringBuilder();
        for (Role role : this.roles) {
            roleStr.append(role.getName());
            roleStr.append(",");
        }
        roleStr.deleteCharAt(roleStr.length() - 1);
        return JwtTokenProvider.createToken(this.username, roleStr.toString());
    }
}
