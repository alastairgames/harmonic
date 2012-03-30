package harmonic.format.sf2;

final class ZoneGenerator {
    
    ZoneGenerator(Generator generator, int value) {
        this.g = generator;
        this.value = value;
    }
    
    private Generator g;
    private int value; //this stores one of three formats: 2 bytes, a short, or an unsignedShort.

    Generator getGenerator() {
        return g;
    }
    
    int getValue() {
        return value;
    }
    
}
