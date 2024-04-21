package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.service.CategoryService;
import com.mycompany.myapp.service.dto.CategoryDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.errors.EmailAlreadyUsedException;
import com.mycompany.myapp.web.rest.errors.LoginAlreadyUsedException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/category")
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    public CategoryResource(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws URISyntaxException {
        log.debug("REST request to save Category : {}", categoryDTO);

        if (categoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new post cannot already have an ID", "categoryManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else {
            Category newCategory = categoryService.createOrUpdate(categoryDTO);
            return ResponseEntity
                .created(new URI("/api/admin/category/" + newCategory.getId()))
                .headers(HeaderUtil.createAlert(applicationName, "categoryManagement.created", newCategory.getId().toString()))
                .body(newCategory);
        }
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        log.debug("REST request to update Category : {}", categoryDTO);
        Optional<Category> existingUser = categoryRepository.findById(categoryDTO.getId());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(categoryDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = categoryRepository.findById(categoryDTO.getId());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(categoryDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Category updatedCategory = categoryService.createOrUpdate(categoryDTO);

        return ResponseUtil.wrapOrNotFound(
            Optional.of(updatedCategory),
            HeaderUtil.createAlert(applicationName, "categoryManagement.updated", categoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET /admin/users} : get all users with all the details - calling this
     * are only allowed for the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         all users.
     */
    @GetMapping("")
    public ResponseEntity<List<Category>> getListCategory(Pageable pageable, @RequestParam String search) { // pagable
        log.debug("REST request to get all Category for an admin");
        final Page<Category> page = categoryService.getListCategory(pageable, search);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Category> getPost(@PathVariable Long id) {
        log.debug("REST request to get Category : {}", id);
        return ResponseUtil.wrapOrNotFound(categoryService.findById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.debug("REST request to delete Category: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createAlert(applicationName, "categoryManagement.deleted", id.toString()))
            .build();
    }
}
