package com.library.System.Repo;

import com.library.System.entity.Borrower;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BorrowerRepo  extends JpaRepository<Borrower, Long>{
}
