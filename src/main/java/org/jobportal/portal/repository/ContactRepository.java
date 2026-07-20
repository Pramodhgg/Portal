package org.jobportal.portal.repository;

import org.jobportal.portal.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Long> {

    List<Contact> findContactByStatus(String status);

    List<Contact> findContactByStatusOrderByCreatedAtAsc(String status);

    List<Contact> findContactsByStatus(String status, Sort sort);

    Page<Contact> findContactsByStatus(String status, Pageable pageable);

    @Modifying
    int updateStatusById(@Param("status") String status, @Param("id") Long id, @Param("updatedBy") String updateBy);


//    @Modifying
//    @Query("UPDATE Contact c SET c.status = :status, c.updatedAt = CURRENT_TIMESTAMP, c.updatedBy = :updatedBy WHERE c.id = :id")
//    int updateStatusById(@Param("status") String status, @Param("id") Long id, @Param("updatedBy") String updateBy);

}
