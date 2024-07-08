package com.mobile.place.repository.jpa;

import com.mobile.place.entity.ProductHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPlaceJPARepository extends JpaRepository<ProductHistoryEntity, Long> {
    List<ProductHistoryEntity> getProductBookEntitiesByProductId(Long productId);

    @Query(value = "select * from product_history_info where product_id=?1 and status=?2", nativeQuery = true)
    ProductHistoryEntity getLatestProductHistoryByProductId(Long productId, String status);
}
