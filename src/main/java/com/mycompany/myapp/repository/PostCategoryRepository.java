package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PostCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    PostCategory findByIdPostAndIdCategory(Long postId, Long categoryId);

    @Transactional
    void deleteAllByIdPostAndIdCategoryNotIn(Long postId, List<Long> categoryIds);

    List<PostCategory> findAllByIdPost(Long idPost);

    @Transactional
    void deleteAllByIdPost(Long postId);
}
