package com.example.test.repository;

import com.example.test.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepositery extends JpaRepository<Post,Long> {
}
