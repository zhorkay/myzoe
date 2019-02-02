package io.myzoe.site.repositories;

import io.myzoe.site.entities.Authority;
import org.springframework.data.repository.CrudRepository;


public interface AuthorityRepository extends CrudRepository<Authority, Long>
{
    Authority getByAuthority(String authorityname);
}
