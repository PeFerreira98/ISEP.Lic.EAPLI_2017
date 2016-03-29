package eapli.ecafeteria.domain.users;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eapli.framework.authz.Authorisable;
import eapli.framework.domain.AggregateRoot;
import eapli.framework.dto.DTOable;
import eapli.framework.dto.GenericDTO;
import eapli.framework.visitor.Visitable;
import eapli.framework.visitor.Visitor;
import eapli.util.DateTime;

/**
 * a system user
 *
 * @author pgsou_000
 *
 */
@Entity
public class User implements AggregateRoot<Username>, Authorisable<ActionRight>, DTOable<User>, Visitable<GenericDTO>,
        Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // TODO provably we should have a db ID (long)...
    @Id
    private Username username;
    private Password password;
    private Name name;
    private EmailAddress email;
    private RoleSet roles;
    @Temporal(TemporalType.DATE)
    private Calendar createdOn;

    public User(String username, String password, String firstName, String lastName, String email,
            List<RoleType> roles) {
        if (roles == null) {
            throw new IllegalArgumentException("roles cannot be null");
        }
        this.createdOn = DateTime.now();
        this.username = new Username(username);
        this.password = new Password(password);
        this.name = new Name(firstName, lastName);
        this.email = new EmailAddress(email);
        this.roles = new RoleSet();
        for (final RoleType rt : roles) {
            this.roles.add(new Role(rt, this.createdOn));
        }
    }

    // for ORM
    protected User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        final User user = (User) o;

        if (!this.username.equals(user.username)) {
            return false;
        }

        // FIXME DDD entities are only compared thru their ID field. in this
        // case only username should be compared
        if (!this.password.equals(user.password)) {
            return false;
        }
        if (!this.name.equals(user.name)) {
            return false;
        }
        if (!this.email.equals(user.email)) {
            return false;
        }
        return this.roles.equals(user.roles);

    }

    @Override
    public int hashCode() {
        int result = this.username.hashCode();
        // FIXME hash should only use username field
        result = 31 * result + this.password.hashCode();
        result = 31 * result + this.name.hashCode();
        result = 31 * result + this.email.hashCode();
        result = 31 * result + this.roles.hashCode();
        return result;
    }

    @Override
    public Username id() {
        return this.username;
    }

    /**
     * Add role to user
     *
     * @param role
     */
    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public GenericDTO toDTO() {
        final GenericDTO ret = new GenericDTO("user");
        ret.put("username", this.username.toString());
        ret.put("password", this.password.toString());
        ret.put("name", this.name.toString());
        ret.put("email", this.email.toString());
        ret.put("roles", this.roles.roleTypes().toString());
        // TODO: ASK Isn't it easy to forget mapping an element to DTO when
        // manipulating members?

        return ret;
    }

    /**
     * remove role from user
     *
     * @param role
     */
    public void removeRole(Role role) {
        // TODO should the role be removed or marked as "expired"?
        this.roles.remove(role);
    }

    @Override
    public boolean isAuthorizedTo(ActionRight action) {
        return action.canBePerformedBy(this.roles.roleTypes());
    }

    // TODO this method's name suggests a boolean return not a void
    // we are using exception handling for logic behavior...
    public void passwordMatches(Password password) throws InvalidPasswordException {
        if (!this.password.equals(password)) {
            throw new InvalidPasswordException("Password does note match", this);
        }
    }

    @Override
    public void accept(Visitor<GenericDTO> visitor) {
        visitor.visit(toDTO());
    }

    @Override
    public boolean is(Username id) {
        return id().equals(id);
    }
}
