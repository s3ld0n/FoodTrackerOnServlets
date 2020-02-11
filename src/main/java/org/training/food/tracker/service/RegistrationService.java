package org.training.food.tracker.service;

import org.training.food.tracker.dto.BiometricsDTO;
import org.training.food.tracker.dto.UserDTO;

public interface RegistrationService {
    boolean validateFields(UserDTO userDTO, BiometricsDTO biometricsDTO);
}
