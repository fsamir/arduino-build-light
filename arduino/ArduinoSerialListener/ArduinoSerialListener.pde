#include <Colorduino.h>

unsigned char plasma[ColorduinoScreenWidth][ColorduinoScreenHeight];
long paletteShift;

int inByte = 0;         // incoming serial byte

void setup()
{
  Serial.begin(9600);
  initializeColorduino();
  establishContact();  // send a byte to establish contact until receiver responds   
}
void loop(){
  // if we get a valid byte, read analog ins:
  if (Serial.available() > 0) {
    // get incoming byte:
    inByte = Serial.read();
    /*
    LEgend:
     SUCCESS(1),
     FAILED(2),
     BUILDING(3),
     BUILDING_FROM_SUCCESS(4),
     BUILDING_FROM_FAILURE(5),
     DISABLED(6);
     */
    switch(inByte) {
    case 1:
      set_all_green();
      break;
    case 2:
      set_all_red();
      break;
    case 4:
      set_all_blue();
      break;
    case 5:
//      blinkOrange();
      setAllOrange();
      break;
    case 6:
      set_all_red();
      break;
    default:
      set_all_grey();
      Serial.println("I do not speak your language!");   // send back the value received.
      break;
    } 
    
    delay(1000);     
    Serial.print(inByte);   // send back the value received.
  } 
}

void waitingForCommunicationLight() {
  Colorduino.SetPixel(0, 0, 36,25,255);
  Colorduino.FlipPage(); 
  delay(1000);
  Colorduino.SetPixel(0, 0, 255,0,0);
  Colorduino.FlipPage(); 
}
//RGB samples: http://www.tayloredmktg.com/rgb/
void blinkOrange(){
   set_all(0,0,0);//black
   delay(500);   
   set_all(255,165,0);//orange
}
void setAllOrange(){   
   set_all(255,165,0);//orange
}
void set_all_grey(){
    set_all(30,30,30);
}
void set_all_blue(){
  set_all(0,0,255);
}

void set_all_green(){
  set_all(0,255,0);
}
void set_all_red(){
  set_all(255,0,0);
}

void set_all(int r, int g, int b) {
  unsigned char x,y;
  float value;

  for(y = 0; y < ColorduinoScreenHeight; y++) {
    for(x = 0; x < ColorduinoScreenWidth; x++) {	
      Colorduino.SetPixel(x, y, r, g, b);
    }
  }
  paletteShift++;
  Colorduino.FlipPage(); // swap screen buffers to show it
}

void establishContact() {
  while (Serial.available() <= 0) {
    Serial.print('A', BYTE);   // send a capital A
    delay(300);
  }
}

void initializeColorduino() {
    Colorduino.Init(); // initialize the board

  // compensate for relative intensity differences in R/G/B brightness
  // array of 6-bit base values for RGB (0~63)
  // whiteBalVal[0]=red
  // whiteBalVal[1]=green
  // whiteBalVal[2]=blue
  unsigned char whiteBalVal[3] = {
    36,63,63        }; // for LEDSEE 6x6cm round matrix
  Colorduino.SetWhiteBal(whiteBalVal);


  // start with morphing plasma, but allow going to color cycling if desired.
  paletteShift=128000;
  unsigned char bcolor;

  //generate the plasma once
  for(unsigned char y = 0; y < ColorduinoScreenHeight; y++)
    for(unsigned char x = 0; x < ColorduinoScreenWidth; x++)
    {
      //the plasma buffer is a sum of sines
      bcolor = (unsigned char)
        (
        128.0 + (128.0 * sin(x*8.0 / 16.0))
          + 128.0 + (128.0 * sin(y*8.0 / 16.0))
          ) / 2;
      plasma[x][y] = bcolor;
    }  

  waitingForCommunicationLight();
}




