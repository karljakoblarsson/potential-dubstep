import processing.core.*;

public class Mandelbrot5-jan13 extends PApplet {
  
  public final int bredd = 960;
  public final int hajd = 720;
  
  double minX = -2.0;
  double maxX = 1.0;
  double minY = -1.2;
  double maxY = 1.2;
  
  double breddZ = (Math.abs(maxX - minX));
  double hajdZ = (Math.abs(maxY - minY));
  
  public double pixelPerRe = (double) (bredd / breddZ);
  public double pixelPerIm = (double) (hajd / hajdZ);
  
  public int maxIterations = 100;
  
  Complex mouse;
  
  PFont font;
  
  //////////////////////////////////////////////////////////////////////////////////////////
  
  public void setup() {
    size(bredd, hajd + 21 /*, P2D*/);

    background(0);
    stroke(255);
    fill(255);
    font = createFont("Courier New", 16, true);
    textFont(font);
    //textSize(20);
    
    ritaUt();
    
  }
  
  ////////////////////////////////////////////////////////////////////////////////////
  
  public void draw() {
    fill(0);
    noStroke();
    rect(0,hajd,bredd,hajd+21);
    
    //stroke(105,204,0);
    //fill(105,204,0);
    //stroke(0,255,0);
    //fill(0,255,0);
    stroke(220);
    fill(230);
    mouse = toComplex(mouseX,mouseY);
    text("      X: " + Math.round(mouse.getRe()*1000000)/1000000.0, 5, hajd+16);
    text( "Y: " + Math.round(mouse.getIm()*1000000)/1000000.0 + "i", bredd/2, hajd+16);
    //line(0,hajd,bredd,hajd);
    
  }
  
  ///////////////////////////////////////////////////////////////////////////
  
  public void ritaUt() {
      
    Complex c, z, hej;
    boolean isInside;
    int antalN;
    
    background(0);
    
    for(int i = 0; i < width*height; i++) {
      isInside = true;
      antalN = 0;
      
      c = toComplex( indexToPixel(i)[0], indexToPixel(i)[1]);
      
      z = new Complex(0,0);
      
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
    
    if(mouseButton == LEFT) {
      background(0);
      Complex c = toComplex(mouseX, mouseY);
      minX = (c.getRe() - 0.25 * breddZ);
      maxX = (c.getRe() + 0.25 * breddZ);
      minY = (c.getIm() - 0.25 * hajdZ);
      maxY = (c.getIm() + 0.25 * hajdZ);
      
      //-------
      
      breddZ = (Math.abs(maxX - minX));
      hajdZ = (Math.abs(maxY - minY));
      
      pixelPerRe = (double) (bredd / breddZ);
      pixelPerIm = (double) (hajd / hajdZ);
      
      ritaUt();
      
    } else if(mouseButton == RIGHT) {
      background(0);
      
      minX = ( minX - breddZ );
      maxX = ( maxX + breddZ );
      minY = ( minY - hajdZ );
      maxY = ( maxX + hajdZ );
      
      //-------
      
      breddZ = (Math.abs(maxX - minX));
      hajdZ = (Math.abs(maxY - minY));
      
      pixelPerRe = (double) (bredd / breddZ);
      pixelPerIm = (double) (hajd / hajdZ);
      
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
      hajdZ = (Math.abs(maxY - minY));
      
      pixelPerRe = (double) (bredd / breddZ);
      pixelPerIm = (double) (hajd / hajdZ);
      
      ritaUt();
    } else if(key == 's') {
      saveFrame("mandelbrot-####.jpg");
    } 
  }
  
  ///////////////////////////////////////////////////////////////////////////
  
  public int[] indexToPixel(int i) {
    int[] pixel = { i%bredd, (i/bredd) };
    return pixel;
  }
  
  public Complex toComplex(int x, int y) {
    return new Complex( minX + x*(maxX-minX)/(bredd), maxY - y*(maxY-minY)/(hajd) );
  }
  
  public int[] toCoordinate(Complex punkt) {
    int[] koordinater = {(int) (punkt.getRe()* pixelPerRe + (bredd/2)),(int) ((hajd/2) + (-1 * punkt.getIm() * pixelPerIm)) };
    return koordinater;
  }
  
  //////////////////////////////////////////////////////////////////////////////
  
  //Kravs far att anvanda processings lib.
  public static void main(String args[]) {
    PApplet.main(new String[] {"--present", "Mandelbrot5" });
  }
}