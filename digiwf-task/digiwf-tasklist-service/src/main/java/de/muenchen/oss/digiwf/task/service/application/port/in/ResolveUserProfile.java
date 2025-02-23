package de.muenchen.oss.digiwf.task.service.application.port.in;

import de.muenchen.oss.digiwf.task.service.application.port.out.user.UserNotFoundException;
import de.muenchen.oss.digiwf.task.service.domain.UserProfile;
import org.springframework.lang.NonNull;

/**
 * Use case to retrieve user information.
 */
public interface ResolveUserProfile {

  /**
   * Resolve a user profile.
   * @param userId user id (lhmObjectId)
   * @return user profile for given id.
   * @throws UserNotFoundException if user can't be found.
   */
  @NonNull
  UserProfile resolveUserProfile(@NonNull String userId) throws UserNotFoundException;

  /**
   * Finds a profile of the user or returns an "Unknown" user.
   * @param userId user id (lhmObjectId)
   * @return user profil for given id, will fall back to user profil "unknown" on any error.
   */
  @NonNull
  UserProfile findUserProfile(@NonNull String userId);
}
