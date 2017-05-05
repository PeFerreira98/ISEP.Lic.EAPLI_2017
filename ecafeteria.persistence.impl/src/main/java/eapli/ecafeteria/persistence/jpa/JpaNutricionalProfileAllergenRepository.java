package eapli.ecafeteria.persistence.jpa;

import eapli.ecafeteria.domain.cafeteria.NutricionalProfile;
import eapli.ecafeteria.domain.cafeteria.NutricionalProfileAllergen;
import eapli.ecafeteria.persistence.NutricionalProfileAllergenRepository;

/**
 *
 * @author Rafael Vieira <1141233@isep.ipp.pt>
 */
class JpaNutricionalProfileAllergenRepository extends CafeteriaJpaRepositoryBase<NutricionalProfileAllergen, String> implements NutricionalProfileAllergenRepository {

    @Override
    public Iterable<NutricionalProfileAllergen> nutricionalProfileAllergenByNutricionalProfile(NutricionalProfile profile) {
        return match("e.nutricionalProfile='" + profile + "'");
    }

}