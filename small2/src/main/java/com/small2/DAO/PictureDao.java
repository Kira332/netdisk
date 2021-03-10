package com.small2.DAO;

import com.small2.pojo.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PictureDao extends JpaRepository<Picture,Integer>{
    Picture findByFilename(String filename);

    @Query(value = "SELECT * FROM picture WHERE foldid = :id", nativeQuery = true)
    Page<Picture> findPicturePageByFoldid(@Param("id") int foldid, Pageable pageable);

    @Query(value = "SELECT * FROM picture WHERE isPass = 0", nativeQuery = true)
    Page<Picture> findPictureByIsPass(Pageable pageable);
}