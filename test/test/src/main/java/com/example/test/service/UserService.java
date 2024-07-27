package com.example.test.service;

import com.example.test.model.Post;
import com.example.test.model.User;
import com.example.test.repository.PostRepositery;
import com.example.test.repository.UserRepositery;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepositery userRepository;
    private final PostRepositery postRepository;

    UserService(UserRepositery  userRepository, PostRepositery  postRepository){
        this.userRepository=userRepository;
        this.postRepository= postRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        Optional<User> foundedUser = userRepository.findById(id);
        if (foundedUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User existingUser = foundedUser.get();
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);

    }

    public User deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        userRepository.delete(user.get());
        return user.get();
    }

    public List<Post> getUserPosts(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return user.get().getPosts();
    }

    public Post createUserPost(Long id, Post post) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        post.setUser(user.get());
        postRepository.save(post);
        return post;
    }

    public Post deleteUserPost(Long user_id, Long post_id) {
        Optional<User> user = userRepository.findById(user_id);
        Optional<Post> post = postRepository.findById(post_id);
        if (user.isEmpty() || post.isEmpty()) {
            throw new RuntimeException("User or Post not found");
        }

        if (!post.get().getUser().getId().equals(user_id)) {
            throw new RuntimeException("Not authorized to delete this post");
        }
        postRepository.delete(post.get());
        return  post.get();
    }

    public Post getUserPost(Long user_id, Long post_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Optional<Post> post = postRepository.findById(post_id);

        if (!post.get().getUser().getId().equals(user_id)) {
            throw new RuntimeException("Not authorized to delete this post");
        }
        if (post.isEmpty()) {
            throw new RuntimeException("Post not found");
        }

        return post.get();
    }


}
