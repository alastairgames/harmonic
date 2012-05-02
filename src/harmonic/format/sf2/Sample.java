package harmonic.format.sf2;

final class Sample {

    Sample(String name, long start, long end, long startLoop, long endLoop,
           long sampleRate, int originalKey, int pitchCorrection,
           long correspondingSampleLink, int sampleType) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.startLoop = startLoop;
        this.endLoop = endLoop;
        this.sampleRate = sampleRate;
        this.originalKey = originalKey;
        this.pitchCorrection = pitchCorrection;
        this.correspondingSampleLink = correspondingSampleLink;
        this.sampleType = sampleType;
    }
    
    private String name;
    private long start;
    private long end;
    private long startLoop;
    private long endLoop;
    private long sampleRate;
    private int originalKey;
    private int pitchCorrection;
    private long correspondingSampleLink;
    private int sampleType;

    long getCorrespondingSampleLink() {
        return correspondingSampleLink;
    }

    long getEnd() {
        return end;
    }

    long getEndLoop() {
        return endLoop;
    }

    String getName() {
        return name;
    }

    int getOriginalKey() {
        return originalKey;
    }

    int getPitchCorrection() {
        return pitchCorrection;
    }

    long getSampleRate() {
        return sampleRate;
    }

    int getSampleType() {
        return sampleType;
    }

    long getStart() {
        return start;
    }

    long getStartLoop() {
        return startLoop;
    }
  
}