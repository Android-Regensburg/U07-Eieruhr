---
title: EggTimer
author: Zuletzt bearbeitet von Alexander Bazo
documentclass: scrartcl
classoption:
  - a4paper
header-includes: |
    \usepackage{german} 
    \usepackage[a4paper,left=2.5cm, right=2.5cm,top=2.5cm, bottom=3cm]{geometry}
    \usepackage{fancyhdr}
    \pagestyle{fancy}
    \fancyhf{}
    \rhead{Mobile Apps für Android}
    \lhead{Übungsaufgaben}
    \cfoot{\includegraphics[height=2cm]{docs/footer.png}}
    \fancypagestyle{plain}{
      \fancyhf{}
      \rhead{Mobile Apps für Android}
      \lhead{Übungsaufgaben}
      \cfoot[C]{\includegraphics[height=2cm]{docs/footer.png}}}
---

# U07 | EggTimer: Die Eieruhg

![Cover für die siebte Übungsaufgabe](./docs/cover.png)

## Aufgabe

In dieser Aufgabe implementieren Sie eine Eieruhr, die das Stoppen der benötigten Zeit für verschiedene Varianten gekochter Eier erlaubt. NutzerInnen können aus drei verschiedenen Varianten (weich-, mittelhart- oder hartgekochten Eiern) wählen. Die Anwendung zählt die verbleibende Zeit herunter und informiert, sobal der Kochvorgang abgschlossen ist. Durch die Verwendung eines paralleln Threads und eines *Service* verhindern wir das Blockieren des *UI Thread* und sorgen dafür, dass die Zeit auch dann weiter kontrolliert wird, wenn die Anwendung in den Hintergrund verschoben wird.

## Ausgangslage

Das *User Interface* und einige Hilfsklassen für die Repräsentation des Kochvorgangs haben wir für Sie vorbereitet. Im Starterpaket finden Sie die `EggTimerActivity`. Über das verwendete *Layout* können NutzerInnen auswählen, welche Eivariante gekocht werden soll. Die verbleibende Zeit wird ebenfalls in dieser *Activity* angezeigt. Für die Umsetzung der Aufgabenstellung müssen Sie keine Änderungen am *User Interface* vornehmen. Wichtig für die Arbeit an der Aufgabe sind die folgenden Methoden:

- `startTimeFor(EggOrder order)`: Diese Methode wird aufgerufen, wenn NutzerInnen üer Auswahlliste und *Button* einen Kochvorgang starten. Der übergebenen Parameter enthält die Informationen, wieviele Sekunden das Ei gekocht werden muss. **Beginnen Sie hier mit der Implementierung der Timer-Logik.**

- `updateTimerValue(int remainingSeconds)`: Mit dieser Methode können Sie die Restzeit des Kochvorangs im *User Interface* aktualisieren. Übergeben Sie die verbleibende Zeit (als Sekunden), damit diese im entsprechenden `TextView` angezeigt werden.

### Kommunikation zwischen Services und Activity

Für die spätere Kommunikation zwischen Service und Activity verwenden wir [Broadcasts](https://developer.android.com/guide/components/broadcasts#context-registered-receivers). Die notwendige Infrastruktur dafür haben wir bereits für Sie implementiert. Die `EggTimerActivity` nutzt einen *BroadcastReceiver*, der Nachrichten aus dem systemweiten *Broadcast-Netzwerk* abfängt und weitergibt. Die Activity erreichen diese Nachrichten automatisch in den Methoden `onTimerUpdate` und `onTimerFinished`. Aus Ihrem Service heraus können Sie diese Methoden über das versenden von *Intents* erreichen. Dabei werden die Intents zuerst vom *BroadcastReceiver* abgefangen und dann über die Methoden des `EggTimerBroadcastListener`-Interface an die Activity übergeben:



## Vorgehen


1. Implementieren Sie einen *Runnable*, in dem die Kochzeit heruntergezählt wird und der in regelmäßigen Intervallen (z.B. jede Sekunde) die Activity über die verbleibende Zeit informiert. Sorgen Sie dafür, dass das *Runnable* in einem separaten Thread ausgeführt wird. Erstellen Sie für die Verbindung zwischen Activity und *Runnable* ein geeignetes Interface, das die Methoden vorgibt, über die der Thread mit der Activity kommunzieren soll. Denken Sie daran, die Aktualisierung der verbleibenden Zeit im *UI Thread* durchzuführen (`runOnUiThread`).



### Hinweise zu Services


## Screenshots der Anwendung

| | | |
|-|-|-|
|![Screenshot der Laufapp](./docs/screenshot-1.png ){ height=8cm } |![Screenshot der Laufapp](./docs/screenshot-2.png ){ height=8cm } |![Screenshot der Laufapp](./docs/screenshot-3.png ){ height=8cm } |