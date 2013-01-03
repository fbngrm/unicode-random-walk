package randomWalk;

// Datei RandomWalk01.java
/* ------------------------------------------------------------------------
Unvollstaendige Version der Datei RandomWalk01.java.
befolgend Sie die Hinweise MUSS ERSETZT WERDEN und
entfernen Sie diese drei Zeilen.

Dieses Programm erzeugt ein PoxelPanel und gibt darauf einen Zufallsweg
(engl. random walk) aus. Die Ausgabe der einzelnen Schritte wird so
verzoegert, dass ein Betrachter sie noch als "sequentielle Schritte"
wahrnehmen kann.

Die Groesse des PoxelPanels wird durch die Konstanten anzX und anzY
festgelegt.

Der Zufallsweg beginnt bei einem zufaellig gewaehlten Poxel.
Die Laengen der einzelnen Zufallsschritte sind durch die Konstanen
mDX und mDY (max Poxel in x- bzw. y-Richtung) begrenzt.
Die Anzahl der Schritte des Zufallsweges wird durch die Konstante
steps festgelegt.

Das jeweils naechste Poxel des Zufallsweges kann wahlweise durch eine
der Methoden nextA, nextB oder nextC festgelegt werden (ein Wechsel
der Methode erfordert eine Rekompilation). Die drei Methoden unterscheiden
sich dann, wenn das naechste Poxel p eigentlich ausserhalb des PoxelPanels
liegt. In einem solchen Fall gilt:
- nextA macht auf der gegenuebliegenden Seite des PoxelPanels weiter.
- nextB "spiegelt p am Rand".
- nextC waehlt einen anderen Zufallsschritt
------------------------------------------------------------------------ */
import javax.swing.JFrame;
import java .awt  .Color;
import java .util .Random;

import static java.awt.Color.*;

class RandomWalk01 {
   // ---------------------------------------------------------------------
   final static boolean TST = false;
   // ---------------------------------------------------------------------
   final JFrame     ram;  // Ein Rahmen fuer alles
   final PoxelPanel pop;  // Ein PoxelPanel (Groesse: anzX mal anzY)

   final int anzX;        // Anzahl Poxel    in x-Richtung
   final int anzY;        // Anzahl Poxel    in y-Richtung

   final int mDX;         // maximales Delta in x-Richtung
   final int mDY;         // maximales Delta in y-Richtung

   final int steps = 10000;// Anzahl der Schritte eines Zufallsweges
   
   final char c;

   Random rand;           // Ein Zufallszahlen-Generator
   // ---------------------------------------------------------------------
   public RandomWalk01(char c) {
      // Initialisiert mit den festen Werten eines bestimmten random walk:
	  this.rand = new Random();
	  this.c = c;
      this.anzX = 40;     // NUSS ERSETZT WERDEN
      this.anzY = 35;     // NUSS ERSETZT WERDEN
      this.mDX  = 3;     // NUSS ERSETZT WERDEN
      this.mDY  = 3;     // NUSS ERSETZT WERDEN
      this.pop  = new PoxelPanel(anzX, anzY);
      this.ram  = machNenJFrame("Random Walk 01 Methode: " + c, pop);  // NUSS ERSETZT WERDEN
      
      walk(pop);  

   } // Standard-Konstruktor RandomWalk01
   // ---------------------------------------------------------------------
   public RandomWalk01(String titel, int anzX, int anzY, int mDX, int mDY, char c) {
	  this.rand = new Random();
	  this.c = c;
      this.anzX = anzX;  // Breite von pop (in Poxeln)
      this.anzY = anzY;  // Hoehe  von pop (in Poxeln)
      this.mDX  = mDX;   // maximales Delta in x-Richtung
      this.mDY  = mDY;   // maximales Delta in y-Richtung
      this.pop  = new PoxelPanel(anzX, anzY); 
      this.ram  = machNenJFrame("Random Walk 01 Methode: " + c, pop); 

      walk(pop);            // Zeichne einen random walk nach pop
   } // Konstruktor RandomWalk01
   // ---------------------------------------------------------------------
   JFrame machNenJFrame(String titel, PoxelPanel pop) {
      JFrame jf = new JFrame(titel);
      jf.setContentPane(pop);
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jf.pack(); 		 //minimale benötigte groesse des frames 
      jf.setLocation(100, 50);
      jf.setVisible(true);
      return jf;
   } // machNenJFrame
   // ---------------------------------------------------------------------
   int nextA(int v, int mDV, int anzV) {
      // v   : Alter Wert einer zufaellig zu veraendernden Variable
      // mDV : Betrag der maximal erlaubte Veraenderung
      // anzV: Anzahl der moeglichen Werte von v (v hat einen Wert
      //       zwischen 0 und anzV-1).
      // Diese Methode berechnet und liefert
      // einen zufaellig gewaehlten naechsten Wert fuer v.
      // Strategie:
      // Nach "ganz rechts" kommt "ganz links" und umgekehrt.
      // Nach "ganz unten"  kommt "ganz oben"  und umgekehrt
	   
	   v += rand.nextInt(mDV);
	   if( v >= anzV){ v -= anzV; }
	   
	   v -= rand.nextInt(mDV);
	   if( v < 0){ v += anzV; }

      return v; // NUSS ERSETZT WERDEN
   } // nextA
   // ---------------------------------------------------------------------
   int nextB(int v, int mDV, int anzV) {
      // v   : Alter Wert einer zufaellig zu veraendernden Variable
      // mDV : Betrag der maximal erlaubte Veraenderung
      // anzV: Anzahl der moeglichen Werte von v (v hat einen Wert
      //       zwischen 0 und anzV-1).
      // Diese Methode berechnet und liefert
      // einen zufaellig gewaehlten naechsten Wert fuer v.
      // Strategie:
      // Geht ein Schritt ueber den Rand hinaus, wird er "am Rand gespiegelt".
      // Beispiel: Wenn v gleich +2 ist:
      //   statt um -3 nach -1 wird um +3 nach +5 gesprungen
      //   statt um -4 nach -2 wird um +4 nach +6 gesprungen
      //   statt um -5 nach -3 wird um +5 nach +7 gesprungen etc.
	   int r1 = rand.nextInt(mDV);
	   if (v+r1 > anzV-1) {
		   v -= r1; 
	   } else {
		   v += r1;
	   }
	   int r2 = rand.nextInt(mDV);
	   if (v-r2 < 0) {
		   v += r2; 
	   } else {
		   v -= r2;
	   }

      return v; // NUSS ERSETZT WERDEN
   } // nextB
   // ---------------------------------------------------------------------
   int nextC(int v, int mDV, int anzV) {
      // v   : Alter Wert einer zufaellig zu veraendernden Variable
      // mDV : Betrag der maximal erlaubten Veraenderung
      // anzV: Anzahl der moeglichen Werte von v (v hat einen Wert
      //       zwischen 0 und anzV-1).
      // Diese Methode berechnet und liefert
      // einen zufaellig gewaehlten naechsten Wert fuer v.
      // Strategie:
      // Geht ein Schritt ueber den Rand hinaus, wird er
      // verworfen und ein anderer Schritt gewaehlt. Das wird
      // wiederholt, bis ein akzeptabler Schritt gefunden wurde.
	   while(true){
		   int r = rand.nextInt(mDV);
		   v +=r;
		   if(v < anzV) {
			   return v;		   
		   }
		   v -= r;
		   if(v > 0) {
			   return v;
		   }
	   }
   } // nextC
   // ---------------------------------------------------------------------
   void schlafRuhig(long ms) {
      // Verzoegert den ausfuehrenden Faden, mindestens um ms Millisekunden.
      // Faengt Ausnahmen des (geprueften) Typs InterruptedException ab.

      try {
         Thread.sleep(ms);
      } catch (InterruptedException ex) {
         printf("Ausnahme in Methode walk:%n");
         printf("%s%n", ex);
      }
   } // schlafRuhig
   // ---------------------------------------------------------------------
   void walk(PoxelPanel pop) {
      // Gibt einen Zufallsweg zum PoxelPanel pop aus
	   	int startX = rand.nextInt(anzX);  // zufälliger startpunkt
	   	int startY = rand.nextInt(anzY);  // zufälliger startpunkt
	   	char z  = 'A'; 
	    int  ci =  0;  // Index fuer die Reihung cTab
	    int x = 0;	   //neue x koordinate
	    int y = 0;	   //neue y koordinate
	      
	   	for(int i=0; i<steps; i++){
	   		if(c == 'A'){ //methode A
	   			x = nextA(startX, mDX, anzX);
	   			y = nextA(startY, mDY, anzY);
	   		}
	   		if(c == 'B'){  //methode B
		   		x = nextB(startX, mDX, anzX);
		   		y = nextB(startY, mDY, anzY);
		   		}
	   		if(c == 'C'){  //methode C
		   		x = nextC(startX, mDX, anzX);
		   		y = nextC(startY, mDY, anzY);
		   		}
		   	pop.drawRect(x, y, cTab[ci], z++); //zeichnet poxel
		   	startX = x; //startwert für nächsten schleifendurchlauf
		   	startY = y; //startwert für nächsten schleifendurchlauf
		   	ci = (ci + 1) ; // index für farbe
		   	if(ci>=cTab.length-1)ci = 0;
	//        if (z>'Z') z='A'; //nach z kommt a
	        schlafRuhig(10L);
	   	}

   } // walk
   // ---------------------------------------------------------------------
   static Color[] cTab = {
      // Eine Tabelle mit ein paar Color-Objekten. Die Namen BLACK,
      // BLUE etc. bezeichnen Klassenattribute der Klasse Color.

      BLACK,
      BLUE   .darker(),   BLUE,      BLUE   .brighter(),
      CYAN   .darker(),   CYAN,      CYAN   .brighter(),
      GRAY   .darker(),   GRAY,      GRAY   .brighter(),
      GREEN  .darker(),   GREEN,     GREEN  .brighter(),
      MAGENTA.darker(),   MAGENTA,   MAGENTA.brighter(),
      ORANGE .darker(),   ORANGE,    ORANGE .brighter(),
      PINK   .darker(),   PINK,      PINK   .brighter(),
      RED    .darker(),   RED,       RED    .brighter(),
      YELLOW .darker(),   YELLOW,    YELLOW .brighter(),
      WHITE,
   };
   // ---------------------------------------------------------------------
   static public void main(String[] _) {
      printf("RandomWalk01: Jetzt geht es los!%n");

     RandomWalk01 rw00 = new RandomWalk01('C');
     //    RandomWalk01 rw01 = new RandomWalk01("Random Walk 1", 60, 40, 3, 3, 'A');

      printf("RandomWalk01: Das war's erstmal!%n");
   } // main
   // ---------------------------------------------------------------------
   // Methoden mit kurzen Namen:
   static void printf(String f, Object... v) {System.out.printf(f, v);}
   // ---------------------------------------------------------------------
} // class RandomWalk01
