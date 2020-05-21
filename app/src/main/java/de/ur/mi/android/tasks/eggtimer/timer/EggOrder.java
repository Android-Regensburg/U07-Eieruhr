package de.ur.mi.android.tasks.eggtimer.timer;

/**
 * Repräsentiert eine "Bestellung" für ein gekochtes Eier der gewünchsten Zubereitungsart (EggVariation)
 */
public class EggOrder {

    public final int targetTime;

    /**
     * Aus der, an den Konstruktor übergebenen Zubereitungsvariante, wird die benötigte Zeit für das
     * Kochen des Eis ausgelesen und abgespeichert.
     */
    public EggOrder(EggVariation variation) {
        targetTime = variation.getTimeInSeconds();
    }

    /**
     * Diese Methode wandelt eine in Textform (String) übergebene Zubereitungsdauer in eine EggOrder-
     * Instanz um. Dabei wird geprüft, ob der übergebene Text in den als valide definierten Varianten
     * vorhanden ist. Übergeben wird in der Activity dabei das Array, das auch für die Darstellung der
     * einzelnen Einträge in der Auswahlliste (Spinner) im UI verwendet wird. Wird eine Übereinstimmung
     * zwischen Textform und validen Varianten erkannt, wird die Position im String[]-Array zur Auswahl
     * der entsprechenden Variante aus dem EggVariation-Enum verwendet.
     *
     * @TODO: Den Auswahlprozess kann man sicherlich noch schöner und sicherer gestalten. Gerne können Sie die Methode anpassen und optimieren.
     */
    public static EggOrder fromString(String value, String[] validValues) {
        for (int i = 0; i < validValues.length; i++) {
            if (value.equals(validValues[i])) {
                return new EggOrder(EggVariation.values()[i]);
            }
        }
        return null;
    }

}
