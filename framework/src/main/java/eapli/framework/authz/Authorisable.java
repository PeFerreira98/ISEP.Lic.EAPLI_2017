package eapli.framework.authz;

/**
 * Created by nuno on 20/03/16.
 */
public interface Authorisable<T> {

	public boolean isAuthorizedTo(T action);
}
