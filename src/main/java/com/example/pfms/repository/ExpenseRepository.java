package com.example.pfms.repository;

import com.example.pfms.model.Expense;
import com.example.pfms.model.Income;
import com.example.pfms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);
    List<Expense> findByUserAndCategoryAndDateBetween(User user, String category, LocalDate startDate, LocalDate endDate);

    @Query("SELECT i FROM Income i WHERE i.user = :user AND MONTH(i.date) = :month AND YEAR(i.date) = :year")
    List<Expense> findByUserAndMonthAndYear(@Param("user") User user, @Param("month") int month, @Param("year") int year);
}
