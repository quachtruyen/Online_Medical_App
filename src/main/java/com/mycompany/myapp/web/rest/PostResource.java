package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Post;
import com.mycompany.myapp.domain.PostCategory;
import com.mycompany.myapp.repository.PostCategoryRepository;
import com.mycompany.myapp.repository.PostRepository;
import com.mycompany.myapp.repository.PostRepositoryCustom;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.service.PostService;
import com.mycompany.myapp.service.dto.PostDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/post")
public class PostResource {

    private final Logger log = LoggerFactory.getLogger(PostResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostService postService;

    private final PostCategoryRepository postCategoryRepository;

    private final PostRepository postRepository;

    private final PostRepositoryCustom postRepositoryCustom;

    public PostResource(
        PostService postService,
        PostCategoryRepository postCategoryRepository,
        PostRepository postRepository,
        PostRepositoryCustom postRepositoryCustom
    ) {
        this.postService = postService;
        this.postCategoryRepository = postCategoryRepository;
        this.postRepository = postRepository;
        this.postRepositoryCustom = postRepositoryCustom;
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to save Post : {}", postDTO);

        if (postDTO.getId() != null) {
            throw new BadRequestAlertException("A new post cannot already have an ID", "postManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else {
            Post newPost = postService.createOrUpdate(postDTO);
            if (!CollectionUtils.isEmpty(postDTO.getCategoryIds())) {
                for (Long id : postDTO.getCategoryIds()) {
                    PostCategory postCategory = new PostCategory();
                    postCategory.setIdCategory(id);
                    postCategory.setIdPost(newPost.getId());
                    postCategoryRepository.save(postCategory);
                }
            }
            return ResponseEntity
                .created(new URI("/api/admin/post/" + newPost.getId()))
                .headers(HeaderUtil.createAlert(applicationName, "postManagement.created", newPost.getId().toString()))
                .body(newPost);
        }
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Post> updatePost(@Valid @RequestBody PostDTO postDTO) {
        log.debug("REST request to update Post : {}", postDTO);
        Optional<Post> existingUser = postRepository.findById(postDTO.getId());
        if (existingUser.isEmpty()) {
            throw new RuntimeException("Không tồn tại bài viết này");
        }
        Post updatedPost = postService.createOrUpdate(postDTO);
        if (!CollectionUtils.isEmpty(postDTO.getCategoryIds())) {
            List<Long> idNotExist = new ArrayList<>();
            for (Long id : postDTO.getCategoryIds()) {
                PostCategory postCategory = postCategoryRepository.findByIdPostAndIdCategory(updatedPost.getId(), id);
                if (postCategory == null) {
                    postCategory = new PostCategory();
                    postCategory.setIdCategory(id);
                    postCategory.setIdPost(updatedPost.getId());
                    postCategoryRepository.save(postCategory);
                } else {
                    idNotExist.add(id);
                }
            }
            postCategoryRepository.deleteAllByIdPostAndIdCategoryNotIn(postDTO.getId(), idNotExist);
        }
        return ResponseUtil.wrapOrNotFound(
            Optional.of(updatedPost),
            HeaderUtil.createAlert(applicationName, "postManagement.updated", postDTO.getId().toString())
        );
    }

    @PutMapping("/publish")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> publishOrUnPublish(@RequestBody PostDTO postDTO) {
        log.debug("REST request to update Post : {}", postDTO);
        Optional<Post> existingUser = postRepository.findById(postDTO.getId());
        if (existingUser.isEmpty()) {
            throw new RuntimeException("Không tồn tại bài viết này");
        }
        existingUser.get().setPublished(postDTO.isPublished());
        postRepository.save(existingUser.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<Post>> getListPost(Pageable pageable, @RequestParam String search) { // pagable
        log.debug("REST request to get all Post for an admin");
        final Page<Post> page = postService.getListPost(pageable, search);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<Post>> getListPostUser(Pageable pageable, @RequestParam String search) { // pagable
        log.debug("REST request to get all Post for an admin");
        final Page<Post> page = postService.getListPostUser(pageable, search);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<Post>> getListPost(Pageable pageable, @RequestParam Long categoryId) { // pagable
        List<Post> post = postRepositoryCustom.findAllByCategoryId(categoryId, pageable.getPageSize(), pageable.getOffset());
        Integer count = postRepositoryCustom.countAllByCategoryId(categoryId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Total-Count", count.toString());
        return new ResponseEntity<>(post, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        log.debug("REST request to get Post : {}", id);
        return ResponseEntity.ok(postService.findById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        log.debug("REST request to delete Post: {}", id);
        postService.deletePost(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "postManagement.deleted", id.toString())).build();
    }
}
