package com.elcom.repository.book;

import com.elcom.project.model.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, UUID> {
}
