import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Put your name here
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);
    /**
     * Useful constants.
     */
    private static final NaturalNumber ONE = new NaturalNumber2(1);
    /**
     * Useful constants.
     */
    private static final NaturalNumber ZERO = new NaturalNumber2(0);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model,
            NNCalcView view) {

        /*
         * Get model info
         */
        NaturalNumber top = model.top();
        NaturalNumber bottom = model.bottom();
        /*
         * Update view to reflect changes in model
         */
        if (top.compareTo(bottom) >= 0) {
            view.updateSubtractAllowed(true);
        } else {
            view.updateSubtractAllowed(false);
        }

        if (bottom.compareTo(ONE) > 0) {
            view.updateDivideAllowed(true);
        } else {
            view.updateDivideAllowed(false);
        }

        if (bottom.compareTo(ZERO) >= 0) {
            view.updatePowerAllowed(true);
        } else {
            view.updatePowerAllowed(false);
        }

        if (bottom.compareTo(TWO) >= 0) {
            view.updateRootAllowed(true);
        } else {
            view.updateRootAllowed(false);
        }
        view.updateTopDisplay(top);
        view.updateBottomDisplay(bottom);
    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        top.copyFrom(bottom);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processAddEvent() {

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        bottom.add(top);
        top.copyFrom(ZERO);
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processSubtractEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        top.subtract(bottom);
        bottom.transferFrom(top);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processMultiplyEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        bottom.multiply(top);
        top.copyFrom(ZERO);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processDivideEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        NaturalNumber r = top.divide(bottom);
        bottom.transferFrom(top);
        top.transferFrom(r);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processPowerEvent() {
        NaturalNumber bottom = this.model.bottom();
        NaturalNumber top = this.model.top();
        if (bottom.compareTo(INT_LIMIT) < 0) {
            top.power(bottom.toInt());
            bottom.transferFrom(top);
            top.copyFrom(ZERO);
        }
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processRootEvent() {
        NaturalNumber bottom = this.model.bottom();
        NaturalNumber top = this.model.top();
        if (bottom.compareTo(TWO) >= 0 && bottom.compareTo(INT_LIMIT) < 0) {
            top.root(bottom.toInt());
            bottom.transferFrom(top);
            top.copyFrom(ZERO);
        }
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processAddNewDigitEvent(int digit) {
        NaturalNumber bottom = this.model.bottom();
        bottom.multiplyBy10(digit);
        updateViewToMatchModel(this.model, this.view);
    }

}
