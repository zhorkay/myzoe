package io.myzoe.site.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authority")
@JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Authority extends BaseAuditEntity implements GrantedAuthority
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String authority;
    private Set<UserPrincipal> userPrincipals = new HashSet<>();

    public Authority() {};
    public Authority(String authority)
    {
        this.authority = authority;
    }

    @Id
    @Column(name = "AuthorityId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    @Column(name = "AuthorityName")
    @JsonProperty
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @ManyToMany(mappedBy = "authorities")
    public Set<UserPrincipal> getUserPrincipals() {
        return this.userPrincipals;
    }

    public void setUserPrincipals(Set<UserPrincipal> userPrincipals) {
        this.userPrincipals = userPrincipals;
    }
}
