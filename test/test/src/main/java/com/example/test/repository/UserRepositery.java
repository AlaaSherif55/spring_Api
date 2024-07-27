package com.example.test.repository;
import com.example.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositery extends JpaRepository<User,Long>{


}
