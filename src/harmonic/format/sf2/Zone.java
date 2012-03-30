package harmonic.format.sf2;

import java.util.List;

final class Zone {
    private List<ZoneModulator> zoneModulators;
    private List<ZoneGenerator> zoneGenerators;
    
    Zone(List<ZoneModulator> zoneModulators, List<ZoneGenerator> zoneGenerators) {
        this.zoneModulators = zoneModulators;
        this.zoneGenerators = zoneGenerators;
    }

    List<ZoneGenerator> getZoneGenerators() {
        return zoneGenerators;
    }

    List<ZoneModulator> getZoneModulators() {
        return zoneModulators;
    }

}
