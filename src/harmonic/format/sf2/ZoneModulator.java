package harmonic.format.sf2;

final class ZoneModulator {
    
    ZoneModulator(Modulator m, Generator g, int amount, Modulator control, Transform t) {
        this.m = m;
        this.g = g;
        this.amount = amount;
        this.control = control;
        this.t = t;
    }

    private Modulator m;
    private Generator g;
    private int amount;
    private Modulator control;
    private Transform t;

    Modulator getModulator() {
        return m;
    }

    Generator getGenerator() {
        return g;
    }

    int getAmount() {
        return amount;
    }

    Modulator getControl() {
        return control;
    }

    Transform getTransform() {
        return t;
    }
    
}

