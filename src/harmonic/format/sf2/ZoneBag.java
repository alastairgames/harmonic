package harmonic.format.sf2;

class ZoneBag {
   
    private int genIndex;
    private int modIndex;
    
    ZoneBag(int genIndex, int modIndex) {
        this.modIndex = modIndex;
        this.genIndex = genIndex;
    }

    int getGeneratorIndex() {
        return genIndex;
    }

    int getModulatorIndex() {
        return modIndex;
    }
   
}
