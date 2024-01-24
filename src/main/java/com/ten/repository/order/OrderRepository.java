package com.ten.repository.order;

import com.ten.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.member.id = :memberId")
    Page<Order> findByMemberId(@Param("memberId")Long memberId, Pageable pageable);
}
