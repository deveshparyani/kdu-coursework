package com.myproject.backendminiproject.repositories;

import com.myproject.backendminiproject.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    Optional<Device> findByKickstonIdAndDeviceUsername(
            String kickstonId,
            String deviceUsername
    );
}