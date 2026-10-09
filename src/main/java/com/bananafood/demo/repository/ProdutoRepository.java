package com.bananafood.demo.repository;

import com.bananafood.demo.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p WHERE " +
           "(:categoria IS NULL OR :categoria = '' OR LOWER(:categoria) = 'todos' OR LOWER(p.categoria) = LOWER(:categoria)) AND " +
           "(:busca IS NULL OR :busca = '' OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :busca, '%')) OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :busca, '%')))")
    List<Produto> filtrar(@Param("categoria") String categoria, @Param("busca") String busca);
}
