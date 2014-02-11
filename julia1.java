import processing.core.*;

public class julia1 extends PApplet {
  
  public final int bredd = 960;
  public final int höjd = 720;
  
  double minX = -2.0;
  double maxX = 2.0;
  double minY = -1.5;
  double maxY = 1.5;
    
  public int maxIterations = 100;
  
  Complex c = new Complex(-0.123, 0.745);
  
  //////////////////////////////////////////////////////////////////////////////////////////
  
  double breddZ = (Math.abs(maxX - minX));
  double höjdZ = (Math.abs(maxY - minY));
  
  public double pixelPerRe = (double) (bredd / breddZ);
  public double pixelPerIm = (double) (höjd / höjdZ);
  
  Complex mouse;
  
  PFont font;
  
  //////////////////////////////////////////////////////////////////////////////////////////
  
  public void setup() {
    size(bredd, höjd + 21 /*, P2D*/);

    background(0);
    stroke(255);
    fill(255);
    font = createFont("Courier New", 16, true);
    textFont(font);
    
    ritaUt();
    
  }
  
  ////////////////////////////////////////////////////////////////////////////////////
  
  public void draw() {
    fill(0);
    noStroke();
    rect(0,höjd,bredd,höjd+21);
    
    stroke(220);
    fill(230);
    mouse = toComplex(mouseX,mouseY);
    text("      X: " + Math.round(mouse.getRe()*1000000)/1000000.0, 5, höjd+16);
    text( "Y: " + Math.round(mouse.getIm()*1000000)/1000000.0 + "i", bredd/2, höjd+16);
    
  }
  
  ///////////////////////////////////////////////////////////////////////////
  
  public void ritaUt() {
      
    Complex z, hej;
    boolean isInside;
    int antalN = 0;
    
    background(0);
    
    for(int i = 0; i < width*height; i++) {
      isInside = true;
      
      z = toComplex( indexToPixel(i)[0], indexToPixel(i)[1]);
      
      for(int a = 0; a <= maxIterations; a++) {
        if ((z.getRe()*z.getRe() + z.getIm()*z.getIm() ) > 4.0) {
          isInside = false;
          antalN = a;
          break;
        }
        hej = z.mul(z);
        z = hej.add(c);
      }
      
      if (isInside) {
        stroke(0);
        point(indexToPixel(i)[0], indexToPixel(i)[1]);
      } else {
        
        //the smmoth fade from black to red to yellow to white to yellow to black.
        if (antalN <= 12) {
          stroke(antalN*21,0,0);
        } else if (antalN <= 24) {
          stroke(252, (antalN-12)*21,0);
        } else if (antalN >= 76) {
          stroke(255-(antalN-76)*7,140-(antalN-76)*8,0);
        } else if (antalN >= 40) {
          stroke(252, 252-(antalN-40)*4,140-(antalN-40)*9);
        } else {
          stroke(255/*-((antalN-24)/6)*/,252,(antalN-24)*9);
        }        
        
        point(indexToPixel(i)[0], indexToPixel(i)[1]);
      }
      
    }
  }
  
  ///////////////////////////////////////////////////////////////////////////
  
  public void mouseReleased() {
    
    mouse = toComplex(mouseX, mouseY);
    
    if(mouseButton == LEFT) {
      background(0);
      
      minX = (mouse.getRe() - 0.25 * breddZ);
      maxX = (mouse.getRe() + 0.25 * breddZ);
      minY = (mouse.getIm() - 0.25 * höjdZ);
      maxY = (mouse.getIm() + 0.25 * höjdZ);
      
      //-------
      
      breddZ = (Math.abs(maxX - minX));
      höjdZ = (Math.abs(maxY - minY));
      
      pixelPerRe = (double) (bredd / breddZ);
      pixelPerIm = (double) (höjd / höjdZ);
      
      ritaUt();
      
    } else if(mouseButton == RIGHT) {
      background(0);
      
      minX = (mouse.getRe() - 1.5 * breddZ);
      maxX = (mouse.getRe() + 1.5 * breddZ);
      minY = (mouse.getIm() - 1.5 * höjdZ);
      maxY = (mouse.getIm() + 1.5 * höjdZ);
      //-------
      
      breddZ = (Math.abs(maxX - minX));
      höjdZ = (Math.abs(maxY - minY));
      
      pixelPerRe = (double) (bredd / breddZ);
      pixelPerIm = (double) (höjd / höjdZ);
      
      ritaUt();
      
    }
  }
  
  public void keyPressed() {
    if( key == ENTER ){
      background(0);
      
      minX = -2.0;
      maxX = 1.0;
      minY = -1.2;
      maxY = 1.2;
      
      //-------
      
      breddZ = (Math.abs(maxX - minX));
      höjdZ = (Math.abs(maxY - minY));
      
      pixelPerRe = (double) (bredd / breddZ);
      pixelPerIm = (double) (höjd / höjdZ);
      
      ritaUt();
    } else if(key == 's') {
      saveFrame("mandelbrot-####.jpg");
      
    } else if(key == 'q') {
      c = new Complex(-0.123, 0.745);
      ritaUt();
    } else if(key == 'w') {
      c = new Complex(-0.391, 0.587);
      ritaUt();
    } else if(key == 'e') {
      c = new Complex(0, 1);
      ritaUt();
    } else if(key == 'r') {
      c = new Complex(-0.75, 0);
      ritaUt();
    } else if(key == '5') {
      c = new Complex(-0.75, 0);
    } else if(key == '6') {
      c = new Complex(-0.75, 0);
    } else if(key == '7') {
      c = new Complex(-0.75, 0);
    } else if(key == '8') {
      c = new Complex(-0.75, 0);
    } else if(key == '9') {
      c = new Complex(-0.75, 0);
    } 
  }
  
  ///////////////////////////////////////////////////////////////////////////
  
  public int[] indexToPixel(int i) {
    int[] pixel = { i%bredd, (i/bredd) };
    return pixel;
  }
  
  public Complex toComplex(int x, int y) {
    return new Complex( minX + x*(maxX-minX)/(bredd), maxY - y*(maxY-minY)/(höjd) );
  }
  
  public int[] toCoordinate(Complex punkt) {
    int[] koordinater = {(int) (punkt.getRe()* pixelPerRe + (bredd/2)),(int) ((höjd/2) + (-1 * punkt.getIm() * pixelPerIm)) };
    return koordinater;
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
  //Krävs för att använda processings lib.
  public static void main(String args[]) {
    PApplet.main(new String[] {"--present", "julia1" });
  }
}