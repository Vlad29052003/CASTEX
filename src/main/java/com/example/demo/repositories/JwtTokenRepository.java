package com.example.demo.repositories;

import com.example.demo.entities.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JwtTokenRepository extends JpaRepository<JwtToken, String> {
    @Query(value = """
      select t from JwtToken t inner join UserEntity u\s
      on t.user.email = u.email\s
      where u.email = :email and t.revoked = false\s
      """)
    List<JwtToken> findAllValidTokenByUser(String email);
}
