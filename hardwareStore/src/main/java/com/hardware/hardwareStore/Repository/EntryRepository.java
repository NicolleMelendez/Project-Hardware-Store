package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    @Query("SELECT e FROM Entry e WHERE e.inventory.id = :inventoryId")
    List<Entry> findByInventoryId(@Param("inventoryId") Long inventoryId);

    @Query("SELECT e FROM Entry e WHERE e.supplier.id = :supplierId")
    List<Entry> findBySupplierId(@Param("supplierId") Long supplierId);

    List<Entry> findByDateEntryBetween(Date startDate, Date endDate);
}