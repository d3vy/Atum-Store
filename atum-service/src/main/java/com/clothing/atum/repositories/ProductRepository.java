package com.clothing.atum.repositories;


import com.clothing.atum.models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    //Product именно класс, а не таблица, и p.title свойство, а не колонка таблицы.
//     @Query(value = "select p from Product p where p.title ilike :filter") //JPQL

//    @Query(value = "select * from catalogue.t_product where c_title ilike :filter", nativeQuery = true)

    @Query(name="findAllByTitleLikeIgnoringCase")
    Iterable<Product> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);
}
