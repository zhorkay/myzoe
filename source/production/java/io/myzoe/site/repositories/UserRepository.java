package io.myzoe.site.repositories;

import io.myzoe.site.entities.UserPrincipal;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserPrincipal, Long>
{
    UserPrincipal getByUsername(String username);
}
