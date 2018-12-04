/**
 * This software is writen under GPL v3
 * https://www.gnu.org/licenses/gpl-3.0.en.thml
 * Christian Görtz 2018
 */
#define LCD16X4
//#define GLCD


#include <stdlib.h>
#include <SPI.h>
#include <RFID.h>

#ifdef LCD16X4
  #include <LiquidCrystal.h>
#endif
#ifdef GLCD
//#include <GLCDLIB.h>
#endif
#include <Ethernet.h>
#include <MySQL_Connection.h>
#include <MySQL_Cursor.h>


#define RFID_SS_PIN A0
#define RFID_RST_PIN A1
#ifdef LCD16X4
#define LCD_D4 A8
#define LCD_D5 A9
#define LCD_D6 A10
#define LCD_D7 A11
#define LCD_EN A12
#define LCD_RS A13
#endif

#define BTN_IN 49
#define BTN_OUT 47
#define LED_IN 43
#define LED_OUT 45
#define LED_BUSY 53
#define RFIDTAG_LENGHT 5
#define MODE_IN 1
#define MODE_OUT 2

RFID rfid(RFID_SS_PIN,RFID_RST_PIN);
#ifdef LCD16X4
LiquidCrystal lcd(LCD_RS,LCD_EN,LCD_D4,LCD_D5,LCD_D6,LCD_D7);
#endif

byte mac_addr[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
IPAddress server_addr(192,168,100,19);  // IP of the MySQL *server* here
char user[] = "stempeluhr";              // MySQL user login username
char password[] = "stempel1";        // MySQL user login password
EthernetClient client;
MySQL_Connection conn((Client *)&client);

byte rfidtag[RFIDTAG_LENGHT]= {0,0,0,0,0};


void setup() {
  Serial.begin(115200);
  Serial.print("Initialisierung\n");
  Serial.print("GPIO init...\n");
  initGPIO();
  Serial.print("test lamps...\n");
  digitalWrite(LED_BUSY,HIGH);
  digitalWrite(LED_IN,HIGH);
  digitalWrite(LED_OUT,HIGH);
  delay(300);
  digitalWrite(LED_IN,LOW);
  digitalWrite(LED_OUT,LOW);
  Serial.print("LCD init...\n");
  
  #ifdef LCD16X4
    lcd.begin(16,4);
  #endif
  
  Serial.print("SPI init...\n");
  SPI.begin();
  Serial.print("RFID init...\n");
  rfid.init();
  #ifdef LCD16X4
    lcd.print("Stempeluhr");
  #endif
  Serial.print("Ethernet init...\n");
  Ethernet.begin(mac_addr);
  if (conn.connect(server_addr, 3307, user, password)) {
    Serial.println("connected to MSQL-DB");
  }
  else{
    Serial.println("Connecting to MYSQL-DB failed.");
  }
  
  Serial.print("Date: ");
  Serial.println(getDate());
  Serial.print("Time: ");
  Serial.println(getTime());
  Serial.print("Datetime: ");
  Serial.println(getDateTime());
  digitalWrite(LED_BUSY,LOW);
  Serial.print("SETUP done\n");
}

void loop() {
  long cycletime = 0;
  byte sts = readBtns();
  setLeds(sts);
  
  if(rfid.isCard()){
    digitalWrite(LED_BUSY,HIGH);
    cycletime = millis();
    if (!readCard(&sts)){
      Serial.println("readCard");
      putDB(&sts);
    
    Serial.print("Cycle: ");
    Serial.print(millis() - cycletime);
    Serial.println(" ms");
    }
    digitalWrite(LED_BUSY,LOW);  
  }
  
}

/**
 * GPIO configuration
 */
void initGPIO(){
  pinMode(BTN_IN,INPUT_PULLUP);
  pinMode(BTN_OUT,INPUT_PULLUP);
  pinMode(LED_IN,OUTPUT);
  pinMode(LED_OUT,OUTPUT);
  pinMode(LED_BUSY,OUTPUT);
}



/**
 * Read the rfidtags serial
 */
byte readCard(byte *drctn){
  static char lastRfid[RFIDTAG_LENGHT+1]= {0,0,0,0,1};
  char readRfid[RFIDTAG_LENGHT+1];
  static byte drctn_old = MODE_IN;
  bool repeatedRfid = drctn_old == *drctn ? true : false;

  
  if(rfid.readCardSerial()){
    //Store serial in buffer
    for(uint8_t i = 0; i < RFIDTAG_LENGHT; i++){
      readRfid[i] = rfid.serNum[i];
      
    }
    //check if at least of the five bytes is different
    //from the old value, the it's no repeat
    for(uint8_t i = 0; i < RFIDTAG_LENGHT; i++){
      if(readRfid[i] != lastRfid[i]){
        repeatedRfid = false;
      }
      //store serial in compare buffer (lastRFid)
      for(uint8_t i = 0; i < RFIDTAG_LENGHT; i++){
        lastRfid[i] = readRfid[i];
        rfidtag[i] = readRfid[i];
      }
    }
  }
  drctn_old = *drctn;
  if(repeatedRfid){
    return 1;
  }
  return 0;
}

/**
 * 
 * puts the rfid-tag into db
 */
void putDB(byte *sts){

  char cbuffer[15];
  sprintf(cbuffer,"%02X:%02X:%02X:%02X:%02X",
          rfidtag[4],rfidtag[3],rfidtag[2],rfidtag[1],rfidtag[0]);
  Serial.println(cbuffer);
  lcd.setCursor(0,1);
  lcd.print(cbuffer);
  //cbuffer contains the rfidtag as string formated like:
  //00:11:22:33:44
  //serial[0]...serial[4]

  char INSERT_SQL[100];
  char drctn[4];
  if(*sts == MODE_IN){
    sprintf(drctn,"%s","in");
  }
  else{
    sprintf(drctn,"%s","out");
  }
  sprintf(INSERT_SQL,"INSERT INTO zeiterfassung.stampevents (rfid, status) VALUES ('%s','%s');",cbuffer,drctn);
  Serial.println(INSERT_SQL);
  // Initiate the query class instance
  MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
  // Execute the query
  cur_mem->execute(INSERT_SQL);
  // Note: since there are no results, we do not need to read any data
  // Deleting the cursor also frees up memory used
  delete cur_mem;
}




byte readBtns(){
  static byte statDirection = MODE_IN;
  if(!digitalRead(BTN_IN)){
    statDirection = MODE_IN;
  }
  if(!digitalRead(BTN_OUT)){
    statDirection = MODE_OUT;
  }
  return statDirection;
}

/**
 * sets the leds as indicators, depending on the
 * selected mode
 */
void setLeds(byte d){
  switch (d){
    case MODE_IN:
      digitalWrite(LED_OUT,LOW);
      digitalWrite(LED_IN,HIGH);
      break;
    case MODE_OUT:
      digitalWrite(LED_IN,LOW);
      digitalWrite(LED_OUT,HIGH);
      break;
  }
}

String getDate(){
  row_values *row = NULL;
  char *query = "SELECT CURRENT_DATE";
  String dateString = "";
  MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
  // Execute the query
  cur_mem->execute(query);
  // Fetch the columns (required) but we don't use them.
  column_names *columns = cur_mem->get_columns();
  // Read the row (we are only expecting the one)
  do {
    row = cur_mem->get_next_row();
    if (row != NULL) {
      dateString = (row->values[0]);
    }
  } while (row != NULL);
  // Deleting the cursor also frees up memory used
  delete cur_mem;
  return dateString;
}

String getTime(){
  row_values *row = NULL;
  char *query = "SELECT CURRENT_TIME";
  String dateString = "";
  MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
  // Execute the query
  cur_mem->execute(query);
  // Fetch the columns (required) but we don't use them.
  column_names *columns = cur_mem->get_columns();
  // Read the row (we are only expecting the one)
  do {
    row = cur_mem->get_next_row();
    if (row != NULL) {
      dateString = (row->values[0]);
    }
  } while (row != NULL);
  // Deleting the cursor also frees up memory used
  delete cur_mem;
  return dateString;
}

String getDateTime(){
  row_values *row = NULL;
  char *query = "SELECT CURRENT_TIMESTAMP";
  String dateString = "";
  MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
  // Execute the query
  cur_mem->execute(query);
  // Fetch the columns (required) but we don't use them.
  column_names *columns = cur_mem->get_columns();
  // Read the row (we are only expecting the one)
  do {
    row = cur_mem->get_next_row();
    if (row != NULL) {
      dateString = (row->values[0]);
    }
  } while (row != NULL);
  // Deleting the cursor also frees up memory used
  delete cur_mem;
  return dateString;
}
