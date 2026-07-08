package com.foodexpress.repository;

import com.foodexpress.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Optional<Voucher> findByCodiceIgnoreCaseAndAttivoTrue(String codice);
}
