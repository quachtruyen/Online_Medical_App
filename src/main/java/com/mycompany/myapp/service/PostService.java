package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.Post;
import com.mycompany.myapp.domain.PostCategory;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.repository.PostCategoryRepository;
import com.mycompany.myapp.repository.PostRepository;
import com.mycompany.myapp.repository.PostRepositoryCustom;
import com.mycompany.myapp.service.dto.PostDTO;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    private final PostRepositoryCustom postRepositoryCustom;

    private final PostCategoryRepository postCategoryRepository;

    private final CategoryRepository categoryRepository;

    public PostService(
        PostRepository postRepository,
        UserService userService,
        PostRepositoryCustom postRepositoryCustom,
        PostCategoryRepository postCategoryRepository,
        CategoryRepository categoryRepository
    ) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.postRepositoryCustom = postRepositoryCustom;
        this.postCategoryRepository = postCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public Post createOrUpdate(PostDTO postDTO) {
        Post post = new Post();
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isEmpty()) {
            throw new RuntimeException("Không tìm thấy user hiện tại");
        }
        if (postDTO.getId() != null) {
            Optional<Post> postOptional = postRepository.findById(postDTO.getId());
            if (postOptional.isEmpty()) {
                throw new RuntimeException("Không tồn tại post này");
            }
            post = postOptional.get();
        } else { // tạo mới nếu chưa tồn tại
            post = new Post();
        }
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageUrl(postDTO.getImageUrl());
        post.setCreatedAt(Instant.now());
        post.setCreatedBy(user.get().getLogin());
        post.setPublished(postDTO.isPublished());

        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postCategoryRepository.deleteAllByIdPost(postId);
        postRepository.deleteById(postId);
    }

    // láy ra danh sách
    public Page<Post> getListPost(Pageable pageable, String search) {
        return postRepository.findAllByTitleContainingIgnoreCase(pageable, search);
    }

    // láy ra danh sách
    public Page<Post> getListPostUser(Pageable pageable, String search) {
        return postRepository.findAllByTitleContainingIgnoreCaseAndPublishedIsTrue(pageable, search);
    }

    // lấy chi tiết 1
    public PostDTO findById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new RuntimeException("Không tồn tại bài viết này");
        }

        PostDTO postDTO = new PostDTO(post.get());
        List<PostCategory> postCategories = postCategoryRepository.findAllByIdPost(postDTO.getId());
        List<Long> categoryId = postCategories.stream().map(PostCategory::getIdCategory).collect(Collectors.toList());
        List<Category> categories = categoryRepository.findAllByIdIn(categoryId);
        postDTO.setCategoryIds(categoryId);
        postDTO.setCategories(categories);
        return postDTO;
    }
}
