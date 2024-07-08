package com.mobile.place.repository.jpa;

import com.mobile.place.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJPARepository extends JpaRepository<ProductEntity, Long> {

    @Modifying
    @Query(value = "UPDATE product SET available=?2, VERSION=VERSION+1,MODIFIED_TIMESTAMP=now(), MODIFIED_BY=?3 WHERE id=?1 and available<>?2", nativeQuery = true)
    Integer placeProduct(Long productId, Boolean isAvailable, String userId);
}
