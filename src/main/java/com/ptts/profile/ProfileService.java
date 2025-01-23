package com.ptts.profile;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional

public interface ProfileService {

    Profile createProfile(Profile profile);

    Profile findProfileById(String profileId);

    List<Profile> findAllProfiles();

    Profile updateProfile(String profileId, Profile updatedProfile);

    void deleteProfile(String profileId);



    MapProfileRoute createMapProfileRoute(MapProfileRoute route);
    MapProfileRoute findMapProfileRouteById(String routeId);
    List<MapProfileRoute> findAllMapProfileRoutes();
    MapProfileRoute updateMapProfileRoute(String routeId, MapProfileRoute updatedRoute);
    void deleteMapProfileRoute(String routeId);
}
