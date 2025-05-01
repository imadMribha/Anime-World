package com.animeworld.animeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animeworld.animeapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
