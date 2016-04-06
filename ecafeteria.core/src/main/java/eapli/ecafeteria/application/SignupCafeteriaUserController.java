package eapli.ecafeteria.application;

import eapli.ecafeteria.AppSettings;
import eapli.ecafeteria.domain.authz.ActionRight;
import eapli.ecafeteria.domain.authz.SystemUser;
import eapli.ecafeteria.domain.cafeteria.OrganicUnit;
import eapli.ecafeteria.domain.mealbooking.CafeteriaUser;
import eapli.ecafeteria.domain.mealbooking.CafeteriaUserBuilder;
import eapli.ecafeteria.persistence.CafeteriaUserRepository;
import eapli.ecafeteria.persistence.OrganicUnitRepository;
import eapli.ecafeteria.persistence.PersistenceContext;
import eapli.framework.application.Controller;
import eapli.framework.persistence.DataIntegrityViolationException;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt
 */
public class SignupCafeteriaUserController implements Controller {

    public CafeteriaUser addUser(SystemUser user, String account, OrganicUnit organicUnit, String mecanographicNumber)
            throws DataIntegrityViolationException {

        if (!AppSettings.instance().session().authenticatedUser().isAuthorizedTo(ActionRight.Administer)) {
            // TODO check which exception to throw
            throw new IllegalStateException("user is not authorized to perform this action");
        }

        final CafeteriaUserBuilder cafeteriaUserBuilder = new CafeteriaUserBuilder();
        cafeteriaUserBuilder.withSystemUser(user);
        cafeteriaUserBuilder.withAccount(account);
        cafeteriaUserBuilder.withOrganicUnit(organicUnit);
        cafeteriaUserBuilder.withMecanographicNumber(mecanographicNumber);
        final CafeteriaUser newUser = cafeteriaUserBuilder.build();

        final CafeteriaUserRepository cafeteriaUserRepository = PersistenceContext.repositories().cafeteriaUsers();
        // TODO error checking if the username is already in the persistence
        // store
        cafeteriaUserRepository.add(newUser);
        return newUser;
    }

    public Iterable<OrganicUnit> getAllOrganicUnit() {
        final OrganicUnitRepository organicUnitRepository = PersistenceContext.repositories().organicUnits();

        return organicUnitRepository.all();

    }

}
