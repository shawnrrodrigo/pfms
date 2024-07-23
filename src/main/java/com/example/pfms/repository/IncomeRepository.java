package com.example.pfms.repository;

import com.example.pfms.model.Income;
import com.example.pfms.model.User;
import com.example.pfms.response.IncomeResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUser(User user);
    @Query("SELECT i FROM Income i WHERE i.user = :user AND MONTH(i.date) = :month AND YEAR(i.date) = :year")
    List<Income> findByUserAndMonthAndYear(@Param("user") User user, @Param("month") int month, @Param("year") int year);

}
