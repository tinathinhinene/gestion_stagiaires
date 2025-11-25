package com.gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

}
