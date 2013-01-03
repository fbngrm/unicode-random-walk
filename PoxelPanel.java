package randomWalk;

// Datei PoxelPanel.java
/* ------------------------------------------------------------------------
Ein Poxel aehnelt einem Pixel, ist normalerweise aber groesser (z.B.
10x10 Pixel oder 15x20 Pixel etc.).
Ein PoxelPanel ist ein Grabo-Objekt (engl. GUI object), welches
auf dem Bildschirm als Rechteck von Poxeln dargestellt wird.

Die Groesse des Panels kann bei seiner Erzeugen festgelegt werden
(z.B. 10 x 20 Poxel). Mit der Methode  drawRect  kann man in jedes Poxel
eine Farbe (repraesentiert durch ein Color-Objekt) und
ein Zeichen (genauer: einen char-Wert) schreiben.

Die Groesse eines Poxels in Pixeln wird durch die Konstanten
anzPixX und anzPixY beschrieben.
------------------------------------------------------------------------ */
import javax.swing.JPanel;
import javax.swing.JFrame; // Zum Testen von PoxelPanel
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;

import static java.awt.Color.*;

public class PoxelPanel extends JPanel {
   // ---------------------------------------------------------------------
   final static boolean TST = false;
   // ---------------------------------------------------------------------
   // Groesse dieses Objekts in Poxeln (in X- und in Y-Richtung):
   private final int anzPoxX;
   private final int anzPoxY;
   private Color[][] fTab;     // Fuer jedes Poxel eine Farbe
   private char [][] zTab;     // Fuer jedes Poxel ein  Zeichen
   // ---------------------------------------------------------------------
   // Groesse eines Poxels in Pixeln (in X- und in Y-Richtung):
   private final int anzPixX = 15;
   private final int anzPixY = 20;
   // ---------------------------------------------------------------------
   // Korrektur-Summanden die bewirken sollen, dass (in der Methode paint)
   // die Zeichen genau in die Poxel geschrieben werden (und nicht kurz
   // darueber oder schief daneben). Diese Summanden muessen evtl. angepasst
   // werden, wenn man den verwendeten Font aendert.
   private final int  korrX =        +2;
   private final int  korrY = anzPixY-5;
   private final Font font  = new Font("Courier", Font.BOLD, 18);
   // ---------------------------------------------------------------------
   public PoxelPanel(int anzPoxX, int anzPoxY) {
      // Initialisiert das aktuelle Objekt so, dass es
      // anzPoxX mal anzPoxY viele Poxel enthaelt.

      super(null); // Kein LayoutManager!

      // Zu kleine Param-Werte durch "vernuenftige" Werte ersetzen und
      // in Attributen abspeichern:
      this.anzPoxX = Math.max(anzPoxX, 1);
      this.anzPoxY = Math.max(anzPoxY, 1);

      // Die Groesse des aktuellen Objekts in Pixeln berechnen
      // und (als PreferredSize) festlegen:
      Dimension dim = new Dimension(this.anzPoxX*anzPixX,
                                    this.anzPoxY*anzPixY);
      setPreferredSize(dim);
      setBackground(Color.WHITE);
      setFont(font);

      // Die 2D-Reihungen mit einer Farbe bzw. einem Zeichen pro Poxel
      // erzeugen:
      fTab = new Color[this.anzPoxX][this.anzPoxY]; // Farben
      zTab = new char [this.anzPoxX][this.anzPoxY]; // Zeichen
      // Die standardmaessige Initialisierung dieser Reihungen reicht aus.
   } // Konstruktor PoxelPanel
   // ---------------------------------------------------------------------
   // Getter-Methoden:
   public int getAnzPoxX() {return anzPoxX;}
   public int getAnzPoxY() {return anzPoxY;}

   public Color getColor(int x, int y) {
      // Liefert die Farbe des Poxels mit den Koordinaten x und y
      // (moeglichersweise auch null).

      // Unzulaessige Koordinaten durch zulaessige ersetzen:
      x = Math.abs(x) % anzPoxX;
      y = Math.abs(y) % anzPoxY;
      return fTab[x][y];
   } // getColor

   public char getChar(int x, int y) {
      // Liefert das Zeichen des Poxels mit den Koordinaten y und y.
      x = Math.abs(x) % anzPoxX;
      y = Math.abs(y) % anzPoxY;
      return zTab[x][y];
   } // getChar

   public void gibAus(int x, int y) {
      printf("Poxel[%2d,%2d]: %34s, %c%n",
         x, y, getColor(x, y), getChar(x, y));
   } // gibAus
   // ---------------------------------------------------------------------
   // Wichtigste Methode fuer die Benutzer der Klasse PoxelPanel:
   public void drawRect(int x, int y, Color f, char z) {
      // Faerbt das Poxel mit den Koordinaten x und y mit
      // der Farbe f und schreibt das Zeichen z hinein.

      // Unzulaessige Koordinaten durch zulaessige ersetzen:
      x = Math.abs(x) % anzPoxX;
      y = Math.abs(y) % anzPoxY;

      fTab[x][y] = f;
      zTab[x][y] = z;

      repaint();
   } // drawRect
   // ---------------------------------------------------------------------
   public void paint(Graphics g) {
      // Zeichnet alle Poxel, die durch die Reihungen fTab (Farbe) und.
      // zTab (Zeichen, z.B. Buchstabe) beschrieben werden.

      super.paint(g);

      // Alle Poxel zeichnen (Spalte fuer Spalte):
      for (int x=0; x<anzPoxX; x++) {
         for (int y=0; y<anzPoxY; y++) {

            Color hf = fTab[x][y]; // (Hintergrund-) Farbe des Poxels
            char  z  = zTab[x][y]; // Das Zeichen des Poxels

            if (hf==null) continue;

            // Das Poxel mit seiner (Hintergrund-) Farbe zeichnen:
            g.setColor(hf);
            g.fillRect(x*anzPixX, y*anzPixY, anzPixX, anzPixY);

            Color vf = kontrast(hf); // Eine geeignete Vordergundfarfe

            if (TST) printf("hf: %-33s, vf: %-33s%n", hf, vf);

            // Das Zeichen in das Poxel zeichnen:
            g.setColor(vf);
            g.drawString(z+"", x*anzPixX+korrX, y*anzPixY+korrY);
         }
      }
   } // paint
   // ---------------------------------------------------------------------
   static public Color kontrast(Color f1) {
      // Berechnet eine zu f1 kontrastierende Farbe f2 (damit z.B.
      // ein f2-Zeichen vor einem f1-Hintergrund gut erkennbar ist).
      // Gibt es einen besseren, einfacheren Algorithmus?

      int   f2r = (f1.getRed  () > 127) ? 0 : 255;
      int   f2g = (f1.getGreen() > 127) ? 0 : 255;
      int   f2b = (f1.getBlue () > 127) ? 0 : 255;

      Color f2  = new Color(f2r, f2g, f2b);
      return f2;
   } // kontrast
   // =====================================================================
   // Die folgenden Klassen-Elemente dienen nur dazu, diese Klasse
   // ein bisschen auszuprobieren. Sie könnten auch in eine andere
   // Klasse ausgelagert werden

   static private JFrame macheJF(String titel, PoxelPanel pop) {
      JFrame jf = new JFrame(titel);
      jf.add(pop, "Center");
      jf.pack();
      jf.setLocation(100, 50);
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jf.setVisible(true);
      return jf;
   } // macheJF
   // ---------------------------------------------------------------------
   // Alle Farben, fuer die es in der Klasse Color Konstanten gibt, plus
   // darker() und brighter() Versionen davon. Der Buchstabe im Kommentar
   // wird von diesem Programm in der betreffenden Farbe ausgegeben.
   static Color[] cTab = {
      BLACK,                // A

      BLUE   .darker(),     // B
      BLUE,                 // C
      BLUE   .brighter(),   // D

      CYAN   .darker(),     // E
      CYAN,                 // F
      CYAN   .brighter(),   // G

      GRAY   .darker(),     // H
      GRAY,                 // I
      GRAY   .brighter(),   // J

      GREEN  .darker(),     // K
      GREEN,                // L
      GREEN  .brighter(),   // M

      MAGENTA.darker(),     // N
      MAGENTA,              // O
      MAGENTA.brighter(),   // P

      ORANGE .darker(),     // Q
      ORANGE,               // R
      ORANGE .brighter(),   // S

      PINK   .darker(),     // T
      PINK,                 // U
      PINK   .brighter(),   // V

      RED    .darker(),     // W
      RED,                  // X
      RED    .brighter(),   // Y

      YELLOW .darker(),     // Z
      YELLOW,               // A
      YELLOW .brighter(),   // B

      WHITE,                // C
   };
   // ---------------------------------------------------------------------
   static public void main(String[] _) {
      // Kleiner Test der Klasse PoxelPanel. Demonstriert auch Aufrufe
      // der Methode drawRect.

      printf("PoxelPanel: Jetzt geht es los!%n");

      final int AX = 60; // Anzahl Poxel in x-Richtung (pro Zeile)
      final int AY = 40; // Anzahl Poxel in y-Richtung (pro Spalte)

      // Ein PoxelPanel pop in einem JFrame anzeigen:
      PoxelPanel pop = new PoxelPanel(AX, AY);
      JFrame     ram = macheJF("Test-PoxelPanel:", pop);

      // Ein paar Poxel in das Panel pop schreiben:
      pop.drawRect(   0,    0, Color.RED,       '1');
      pop.drawRect(AX-1, AY-1, Color.DARK_GRAY, '4');
      pop.drawRect(   0, AY-1, Color.WHITE,     '3');
      pop.drawRect(AX-1,    0, Color.YELLOW,    '2');

      char z  = 'A';
      int  ci =  0;  // Index fuer die Reihung cTab
      for (int i=2; i<=30; i++) {
         pop.drawRect(AX/2-i, i, cTab[ci], z++);
         ci = (ci + 1) % cTab.length;
         if (z>'Z') z='A';
      }
      pop.drawRect(AX/2, AY/2, Color.BLACK, '!');

      pop.gibAus(   0,    0);
      pop.gibAus(AX-1, AY-1);
      pop.gibAus(   0, AY-1);
      pop.gibAus(AX-1,    0);

      printf("PoxelPanel: Das war's erstmal!%n");
   } // main
   // ---------------------------------------------------------------------
   // Eine Methode mit kurzem Namen:
   static void printf(String f, Object... v) {System.out.printf(f, v);}
   // ---------------------------------------------------------------------
} // class PoxelPanel
