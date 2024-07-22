package com.example.pfms.repository;

import com.example.pfms.model.Income;
import com.example.pfms.model.User;
import com.example.pfms.response.IncomeResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUser(User user);

}
