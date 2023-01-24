 #include <Servo_ESP32.h>
#include <WiFi.h>
#include <Firebase_ESP_Client.h>

#define TRIG_PIN  22  // ESP32 pin GIOP23 connected to Ultrasonic Sensor's TRIG pin
#define ECHO_PIN  23  // ESP32 pin GIOP22 connected to Ultrasonic Sensor's ECHO pin
#define SERVO_PIN 32  // ESP32 pin GIOP32 connected to Servo Motor's pin
#define DISTANCE_TH 20 // centimeters
#define TRIG_PIN1   25 // ESP32 pin GIOP26 connected to Ultrasonic Sensor's TRIG pin
#define ECHO_PIN1  26 // ESP32 pin GIOP25 connected to Ultrasonic Sensor's ECHO pin
#define BUZZER_PIN 17 // ESP32 pin GIOP17 connected to Piezo Buzzer's pin
#define DISTANCE_THRESHOLD 17 // centimeters
// The below are variables, which can be changed
float duration_us, distance_cm;
float duration, dis;

 Servo_ESP32 servo;
 // Provide the token generation process info.
#include <addons/TokenHelper.h>

// Provide the RTDB payload printing info and other helper functions.
#include <addons/RTDBHelper.h>

/* 1. Define the WiFi credentials */
#define WIFI_SSID "wifi"
#define WIFI_PASSWORD "*****"

// For the following credentials, see examples/Authentications/SignInAsUser/EmailPassword/EmailPassword.ino

/* 2. Define the API Key */
#define API_KEY "AIzaSyCdAAMbj7C2TcRx2MR7_3Lf0MKuy2eU-Rg"

/* 3. Define the RTDB URL */
#define DATABASE_URL "https://poubelleapp-fabb3-default-rtdb.firebaseio.com" //<databaseName>.firebaseio.com or <databaseName>.<region>.firebasedatabase.app

/* 4. Define the user Email and password that alreadey registerd or added in your project */
#define USER_EMAIL "nedraghdamsi4@gmail.com"
#define USER_PASSWORD "123456"

// Define Firebase Data object
FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;
unsigned long sendDataPrevMillis = 0;
unsigned long count = 0;
void setup() {
  Serial.begin (115200);         // initialize serial port
  pinMode(TRIG_PIN1, OUTPUT);   // set ESP32 pin to output mode
  pinMode(ECHO_PIN1, INPUT);    // set ESP32 pin to input mode
  pinMode(BUZZER_PIN, OUTPUT); // set ESP32 pin to output mode
   Serial.begin (9600);       // initialize serial port
  pinMode(TRIG_PIN, OUTPUT); // set ESP32 pin to output mode
  pinMode(ECHO_PIN, INPUT);  // set ESP32 pin to input mode
  servo.attach(SERVO_PIN);   // attaches the servo on pin 9 to the servo object
  servo.write(0);
  Serial.begin(115200);

    WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
    Serial.print("Connecting to Wi-Fi");
    while (WiFi.status() != WL_CONNECTED)
    {
        Serial.print(".");
        delay(300);
    }
    Serial.println();
    Serial.print("Connected with IP: ");
    Serial.println(WiFi.localIP());
    Serial.println();

    Serial.printf("Firebase Client v%s\n\n", FIREBASE_CLIENT_VERSION);

    /* Assign the api key (required) */
    config.api_key = API_KEY;

    /* Assign the user sign in credentials */
    auth.user.email = USER_EMAIL;
    auth.user.password = USER_PASSWORD;

    /* Assign the RTDB URL (required) */
    config.database_url = DATABASE_URL;

    /* Assign the callback function for the long running token generation task */
    config.token_status_callback = tokenStatusCallback; // see addons/TokenHelper.h

    // Or use legacy authenticate method
    // config.database_url = DATABASE_URL;
    // config.signer.tokens.legacy_token = "<database secret>";

    Firebase.begin(&config, &auth);

    // Comment or pass false value when WiFi reconnection will control by your code or third party library
    Firebase.reconnectWiFi(true);
}

void loop() {
  // generate 10-microsecond pulse to TRIG pin
  digitalWrite(TRIG_PIN1, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIG_PIN1, LOW);

  // measure duration of pulse from ECHO pin
  duration_us = pulseIn(ECHO_PIN1, HIGH);
  // calculate the distance
  distance_cm = 0.034*duration_us/2.0;
 
  // print the value to Serial Monitor
  Serial.print("distance: ");
  Serial.print(distance_cm);
  Serial.println(" cm");

  delay(500);
    // generate 10-microsecond pulse to TRIG pin
  digitalWrite(TRIG_PIN, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIG_PIN, LOW);

  // measure duration of pulse from ECHO pin
  duration = pulseIn(ECHO_PIN, HIGH);
  // calculate the distance
  dis = 0.034 * duration/2;

  if (dis <=DISTANCE_TH)
    servo.write(0); // rotate servo motor to 0 degree
  else
    servo.write(180);  // rotate servo motor to 180 degree

  // print the value to Serial Monitor
  Serial.print("distance2: ");
  Serial.print(dis);
  Serial.println(" cm");

  delay(500);
  if (Firebase.ready() && (millis() - sendDataPrevMillis > 15000 || sendDataPrevMillis == 0))
    {
        sendDataPrevMillis = millis();

        FirebaseJson json;
        json.setDoubleDigits(3);
        json.add("value",distance_cm );

        Serial.printf("Set json... %s\n", Firebase.RTDB.setJSON(&fbdo, "/test/json", &json) ? "ok" : fbdo.errorReason().c_str());
if (Firebase.RTDB.getInt(&fbdo,"test/json/ouvrir")){
Serial.println("PASSED");
Serial.println("PATH: "+fbdo.dataPath());
Serial.println("PATH: "+fbdo.dataType());
Serial.println(fbdo.intData());
if (fbdo.intData()==1)
 servo.write(0);
else
 servo.write(0);

}




        
}



      
}
