package com.br.dsevoluction.apicoberturateste.repositories;

import com.br.dsevoluction.apicoberturateste.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
