package harmonic.format.sf2;

final class SampleType {
    static final int TERMINAL = 0;
    static final int MONO = 1;
    static final int RIGHT = 2;
    static final int LEFT = 4;
    static final int LINKED = 8;
    static final int ROM_MONO = 0x8001;
    static final int ROM_RIGHT = 0x8002;
    static final int ROM_LEFT = 0x8004;
    static final int ROM_LINKED = 0x8008;
    
    private SampleType(){}
}
