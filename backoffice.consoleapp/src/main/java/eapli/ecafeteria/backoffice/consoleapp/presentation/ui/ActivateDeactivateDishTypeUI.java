package eapli.ecafeteria.backoffice.consoleapp.presentation.ui;

import eapli.ecafeteria.application.ActivateDeactivateDishTypeController;
import eapli.ecafeteria.domain.DishType;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.util.Console;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MCN on 29/03/2016.
 */
public class ActivateDeactivateDishTypeUI extends AbstractUI {

    private final ActivateDeactivateDishTypeController theController = new ActivateDeactivateDishTypeController();

    @Override
    protected Controller controller() {
        return this.theController;
    }

    @Override
    protected boolean doShow() {
        
        final Iterable<DishType> allDishTypes = this.theController.listDishTypes();
        if (!allDishTypes.iterator().hasNext()) {
            System.out.println("There is no registered Dish Type");
        } else {
            //Note: Java no longer requires explicit type argument, thus new SelectWidget<DishType> may be replaced by new SelectWidget<>
            final SelectWidget<DishType> selector = new SelectWidget<DishType>(allDishTypes, new DishTypePrinter());

            selector.show();
            final DishType updtDishType = selector.selectedElement();
                    this.theController.changeDishTypeState(updtDishType);
            }
        return true;
    }

    @Override
    public String headline() {
        return "Activate / Deactivate Dish Types";
    }
}
