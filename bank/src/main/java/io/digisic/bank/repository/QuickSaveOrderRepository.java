package io.digisic.bank.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import io.digisic.bank.model.QuickSaveOrder;

public interface QuickSaveOrderRepository extends CrudRepository<QuickSaveOrder, Long>{
	
	List<QuickSaveOrder> findAll();

}
