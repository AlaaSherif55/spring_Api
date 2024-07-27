package com.example.test.controller;

import com.example.test.model.Post;
import com.example.test.model.User;
import com.example.test.service.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserResource {
    private  UserService userService;

     UserResource(UserService userService) {
         this.userService=userService;
    }
    @GetMapping()
    public List<User> getAllUsers(){
        return  userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        Optional<User> user =  userService.getUser(id);
        if(user.isEmpty()){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user.get());
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@Valid @RequestBody User user){
        try {
            userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@Valid @RequestBody User user) {
        try{
            User updatedUser = userService.updateUser(id,user);
            return ResponseEntity.ok().body(updatedUser);
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        try{
            User user = userService.deleteUser(id);
            return ResponseEntity.ok().body( user);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable Long id){
        try{
            List<Post> posts = userService.getUserPosts(id);
            return ResponseEntity.ok().body(posts);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Post> createUserPost(@PathVariable Long id, @RequestBody Post post){
        try{
             post = userService.createUserPost(id,post);
             return ResponseEntity.ok().body(post);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{user_id}/posts/{post_id}")
    public ResponseEntity<Post> deleteUserPost(@PathVariable Long user_id,@PathVariable Long post_id){
        try{
            Post post = userService.deleteUserPost(user_id,post_id);
            return ResponseEntity.ok().body(post);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{user_id}/posts/{post_id}")
    public ResponseEntity<Post> getUserPost(@PathVariable Long user_id,@PathVariable Long post_id){
            Post post = userService.getUserPost(user_id,post_id);
            return ResponseEntity.ok().body(post);
    }
}
