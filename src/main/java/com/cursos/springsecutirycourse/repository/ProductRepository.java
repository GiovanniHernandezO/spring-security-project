package com.cursos.springsecutirycourse.repository;

import com.cursos.springsecutirycourse.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
