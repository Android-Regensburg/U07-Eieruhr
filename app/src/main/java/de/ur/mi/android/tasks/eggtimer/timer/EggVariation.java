package de.ur.mi.android.tasks.eggtimer.timer;

/**
 * Repräsentiert verschiedene Zubereitungsarten bzw. -Zeiten für gekochte Eier
 */
public enum EggVariation {

    // Weichgekochte Eier werden 3 Minuten gekocht
    SOFT(180),
    // Mittelweiche Eier werden 5 Minuten gekocht
    MEDIUM(300),
    // Hartgekochte Eier werden 9 Minuten gekocht
    HARD(540);

    private final int timeInSeconds;

    EggVariation(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public int getTimeInSeconds() {
        return this.timeInSeconds;
    }
}
