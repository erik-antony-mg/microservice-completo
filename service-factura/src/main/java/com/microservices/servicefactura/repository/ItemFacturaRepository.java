package com.microservices.servicefactura.repository;

import com.microservices.servicefactura.entities.ItemFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemFacturaRepository extends JpaRepository<ItemFactura,Long> {

}
