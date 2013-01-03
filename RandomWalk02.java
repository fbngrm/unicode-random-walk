package randomWalk;

// Datei RandomWalk02.java
/* ------------------------------------------------------------------------
Unvollstaendige Version der Datei RandomWalk02.java.
befolgend Sie die Hinweise MUSS ERSETZT WERDEN und
entfernen Sie diese drei Zeilen.

Dieses Programm erzeugt ein PoxelPanel und mehrere Objekte der inneren
Klasse Faden. Jedes dieser Faden-Objekte gibt (nebenläufig zu den anderen
Faden-Objekten) einen Zufallsweg zu dem PoxelPanel aus.
------------------------------------------------------------------------ */
import javax.swing.JFrame;
import java .awt  .Color;
import java .util .Random;

import static java.awt.Color.*;

class RandomWalk02 {
   // ---------------------------------------------------------------------
   final static boolean TST = false;
   // ---------------------------------------------------------------------
   final JFrame     ram;  // Ein Rahmen fuer alles
   final PoxelPanel pop;  // Ein PoxelPanel (Groesse: anzX mal anzY)

   final int anzX;        // Anzahl Poxel    in x-Richtung
   final int anzY;        // Anzahl Poxel    in y-Richtung

   final int mDX;         // maximales Delta in x-Richtung
   final int mDY;         // maximales Delta in y-Richtung

   final int steps = 4000;// Anzahl der Schritte eines Zufallsweges
   
   final char c;		  // steuerung der methode
   
   Faden faden01;   // fäden 
   Faden faden02;
   Faden faden03;
   // ---------------------------------------------------------------------
   public RandomWalk02(char c) {
	   
      this.anzX = 60;    // Breite von pop (in Poxeln)
      this.anzY = 40;    // Hoehe  von pop (in Poxeln)
      this.mDX  =  3;    // maximales Delta in x-Richtung
      this.mDY  =  3;    // maximales Delta in y-Richtung
      this.pop  = new PoxelPanel(anzX, anzY);  // NUSS ERSETZT WERDEN
      this.ram  = machNenJFrame("Random Walk 02 Methode", pop);  // NUSS ERSETZT WERDEN
      this.c = c;
      this.faden01 = new Faden(pop, RED, 100L);
      this.faden02 = new Faden(pop, GREEN, 5L);
      this.faden03 = new Faden(pop, BLUE, 50000L);
      
   } // Standard-Konstruktor RandomWalk02
   // ---------------------------------------------------------------------
   JFrame machNenJFrame(String titel, PoxelPanel pop) {
      JFrame jf = new JFrame(titel);
      jf.setContentPane(pop);
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jf.pack();
      jf.setLocation(100, 50);
      jf.setVisible(true);
      return jf;
   } // machNenJFrame
   // ---------------------------------------------------------------------
   void schlafRuhig(long ms) {
      // Verzoegert den ausfuehrenden Faden, mindestens um ms Millisekunden.
      // Faengt Ausnahmen des (geprueften) Typs InterruptedException ab.

      try {
         Thread.sleep(ms);
      } catch (InterruptedException ex) {
         printf("Ausnahme in Methode run:%n");
         printf("%s%n", ex);
      }
   } // schlafRuhig
   // ---------------------------------------------------------------------
   class Faden extends Thread {
      // ------------------------------------------------------------------
      static final boolean TST = true;
      // ------------------------------------------------------------------
      final PoxelPanel pop;
      final Color      color;

      Random rand;
      // ------------------------------------------------------------------
      public Faden(PoxelPanel pop, Color color, Long seed) {

         this.pop   = pop;
         this.color = color;

         if (seed == null) {         // Art des Zufalls:
            rand = new Random();     // Nicht-reproduzierbar
         } else {
            rand = new Random(seed); // Reproduzierbar
         }

         this.start();
      } // Konstruktor Faden
      // ------------------------------------------------------------------
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
    	   
    	   v = v + rand.nextInt(mDV);
    	   if(v>=anzV){ v-=anzV; }

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
    	   
    	   int r = rand.nextInt(mDV);
    	   if(v + r >= anzV){ 
    		   v-=r; 
    	   }else{
    		   v+=r;
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
    		   if(v + r < anzV) return v + r;		   
    	   }
    	   
       } // nextC
   // ------------------------------------------------------------------
      public void run() {
         // Gibt einen Zufallsweg zum PoxelPanel pop aus
    	int startX = rand.nextInt(anzX);  // zufälliger startpunkt
  	   	int startY = rand.nextInt(anzY);  // zufälliger startpunkt
  	   	char z  = '+'; 
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
		   	pop.drawRect(x, y, color, z); //zeichnet poxel
		   	startX = x; //startwert für nächsten schleifendurchlauf
		   	startY = y; //startwert für nächsten schleifendurchlauf
//		   	if (z>'Z') z='A'; //nach z kommt a
	        schlafRuhig(15L);
	   	}
      } // run
      // ------------------------------------------------------------------
   } // class Faden
   // ---------------------------------------------------------------------
   static public void main(String[] _) {
      printf("RandomWalk02: Jetzt geht es los!%n");

      RandomWalk02 rw02 = new RandomWalk02('A');

      printf("RandomWalk02: Das war's erstmal!%n");
   } // main
   // ---------------------------------------------------------------------
   // Eine Methode mit einem kurzen Namen:
   static void printf(String f, Object... v) {System.out.printf(f, v);}
   // ---------------------------------------------------------------------
} // class RandomWalk02
