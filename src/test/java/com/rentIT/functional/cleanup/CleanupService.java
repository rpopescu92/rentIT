package com.rentIT.functional.cleanup;

import com.rentIT.domain.model.ProfileDetails;
import com.rentIT.domain.model.Property;
import com.rentIT.domain.model.User;
import com.rentIT.domain.repository.ProfileDetailsRepository;
import com.rentIT.domain.repository.PropertyRepository;
import com.rentIT.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CleanupService {

    private final UserRepository userRepository;
    private final ProfileDetailsRepository profileDetailsRepository;
    private final PropertyRepository propertyRepository;

    @Autowired
    public CleanupService(UserRepository userRepository,
                          ProfileDetailsRepository profileDetailsRepository,
                          PropertyRepository propertyRepository) {
        this.userRepository = userRepository;
        this.profileDetailsRepository = profileDetailsRepository;
        this.propertyRepository = propertyRepository;
    }

    public void cleanupUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(RuntimeException::new);
        ProfileDetails profileDetails = profileDetailsRepository.findByUser(user);

        profileDetailsRepository.delete(profileDetails.getId());
        userRepository.delete(user.getId());
    }

    public void cleanupProperty(String ownerUserName) {
        List<Property> property = propertyRepository.findByOwner(ownerUserName);
        propertyRepository.deleteInBatch(property);
    }

}
