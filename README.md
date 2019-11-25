# SoronkoStepper
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-StateProgressBar-green.svg?style=true)](https://android-arsenal.com/details/1/4883)

SoronkoStepper is an Android library implementation of a stepper to indicate the various states and transitions in an app.

![alt tag](https://raw.githubusercontent.com/kofigyan/SoronkoStepper/master/screenshots/demo.gif)

## Quick Start

Get a feel of how it works:

<a href="https://play.google.com/store/apps/details?id=com.kofigyan.stateprogressbarsample">
  <img alt="Get it on Google Play"
       src="https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/google-play-badge.png" />
</a>

Check the [wiki](https://github.com/kofigyan/StateProgressBar/wiki) for detailed documentation.

### Gradle

Add the following dependency to your build.gradle :
```
dependencies {
  implementation 'com.kofigyan.soronkostepper:soronkostepper:0.0.2'   
}
```

### XML

```
      <com.kofigyan.soronkostepper.SoronkoStepper
         android:layout_width="match_parent"
         android:id="@+id/soronko_stepper"
         app:ssv_maxStepperNumber="five"
        app:ssv_currentStepperNumber="two"
       app:ssv_checkStepperCompleted="true"
       android:layout_height="wrap_content"
       app:ssv_currentStepperDescriptionColor="#009688"
       app:ssv_stepperBackgroundColor="#BDBDBD"
       app:ssv_stepperDescriptionColor="#808080"
       app:ssv_stepperForegroundColor="#009688"
       app:ssv_stepperNumberBackgroundColor="#808080"
       app:ssv_stepperNumberForegroundColor="#eeeeee"/>
        


```

To add description data to SoronkoStepper :

```

  private val descriptionData = arrayOf("Details", "Status", "Photo", "Confirm", "Done")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(your_layout)
        mStepper = findViewById(R.id.soronko_stepper)
        mStepper.descriptionData = descriptionData
    }

```

## XML Attributes

        ssv_currentStepperNumber => Current stepper number. Must be one of the following constant values : one , two , three , four .
        Related method : setCurrentStepperNumber(StepperNumber)

        ssv_maxStepperNumber  => Maximum stepper number. Must be one of the following constant values : one , two , three , four .
        Related method : setMaxStepperNumber(StepperNumber)

        ssv_stepperBackgroundColor  => State background color. Should be a color value. Possible forms are "#rgb", "#argb", "#rrggbb", or "#aarrggbb".
        Related method : stepperBackgroundColor(int)

        ssv_stepperForegroundColor  => State foreground color. Should be a color value. Possible forms are "#rgb", "#argb", "#rrggbb", or "#aarrggbb".
        Related method : stepperForegroundColor(int)

        ssv_stepperNumberBackgroundColor => State number background color. Should be a color value. Possible forms are "#rgb", "#argb", "#rrggbb", or "#aarrggbb".
        Related method : stepperNumberBackgroundColor(int)

        ssv_stepperNumberForegroundColor => State number foreground color. Should be a color value. Possible forms are "#rgb", "#argb", "#rrggbb", or "#aarrggbb".
        Related method : stepperNumberForegroundColor(int)

        ssv_currentStepperDescriptionColor => Current state description color. Should be a color value. Possible forms are "#rgb", "#argb", "#rrggbb", or "#aarrggbb".
        Related method : currentStepperDescriptionColor(int)

        ssv_stepperDescriptionColor => State description color. Should be a color value. Possible forms are "#rgb", "#argb", "#rrggbb", or "#aarrggbb".
        Related method : stepperDescriptionColor(int)

        ssv_stepperSize => State size . Must be a dimension value with preferrable unit of dp eg. 25dp
        Related method : stepperSize(float)

        ssv_stepperTextSize => State text(number) size . Must be a dimension value with preferrable unit of sp eg. 15sp
        Related method : stepperNumberTextSize(float)

        ssv_stepperDescriptionSize => State description size . Must be a dimension value with preferrable unit of dp eg. 20dp
        Related method : stepperDescriptionSize(Int)        
 
        ssv_checkStepperCompleted => Check completed states . Must be a boolean value,either "true" or "false"
        Related method : checkStepperCompleted(Boolean)        
     
        ssv_animationStartDelay => State joining line animation start delay . Must be an integer value eg. "500" , "1000" , "2000" , "5000" , "10000" etc
        Related method : animStartDelay(int)

        spb_descriptionLinesSpacing => State description multiline spacing . Must be a dimension value with preferrable unit of dp eg. 20dp
        Related method : descriptionTruncateEnd(Boolean)

        spb_maxDescriptionLines => Maximum number of line for multiline description . Must be an integer value eg. "2" , "3" , "4" , "5" , "6" etc
        Related method : descriptionMultilineTruncateEnd(Int)
 


## KOTLIN/JAVA

SoronkoStepper mStepper = findViewById(R.id.soronko_stepper)

     mStepper.stepperForegroundColor = ContextCompat.getColor(this, R.color.demo_state_foreground_color)
     mStepper.stepperBackgroundColor = ContextCompat.getColor(this, android.R.color.darker_gray)
                        
     mStepper.stepperNumberForegroundColor = ContextCompat.getColor(this, android.R.color.white)
     mStepper.stepperNumberBackgroundColor = ContextCompat.getColor(this, android.R.color.background_dark)
                  
    mStepper.stepperSize = 40f
    mStepper.stepperNumberTextSize = 20f
    
    setCurrentStepperNumber(SoronkoStepper.StepperNumber.TWO)
    
    mStepper.stepperNumberTypeface = "fonts/RobotoSlab-Light.ttf"
    
    mStepper.checkStepperCompleted = true
     
    mStepper.setStateDescriptionSize(18f);

    currentStepperDescriptionColor =
                        ContextCompat.getColor(this , R.color.description_foreground_color)

    stepperDescriptionColor =
                        ContextCompat.getColor(this , R.color.description_background_color)
             
    mStepper.stepperDescriptionTypeface = "fonts/RobotoSlab-Light.ttf"
 
    mStepper.descriptionTruncateEnd = true
    mStepper.descriptionMultilineTruncateEnd = 2
     
 

### EXTRA DEMOS(WITH CODES)

 - A Two-State StateProgressBar

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/two_state_spb.png)

```
<com.kofigyan.stateprogressbar.StateProgressBar
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:spb_currentStateNumber="one"
  app:spb_maxStateNumber="two"/>

```


 - A Three-State StateProgressBar

  ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/three_state_spb.png)

```
  <com.kofigyan.stateprogressbar.StateProgressBar
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:spb_currentStateNumber="two"
  app:spb_maxStateNumber="three"/>

  ```


  -  A Four-State StateProgressBar

  ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/four_state_spb.png)

```
   <com.kofigyan.stateprogressbar.StateProgressBar
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   app:spb_currentStateNumber="three"
   app:spb_maxStateNumber="four"/>

```


-  A Five-State StateProgressBar

  ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/five_state_spb.jpg)

```
   <com.kofigyan.stateprogressbar.StateProgressBar
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   app:spb_currentStateNumber="four"
   app:spb_maxStateNumber="five"/>

```



-  A Five-State StateProgressBar(Descending/Rtl Languages Support)

  ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/five_state_arab_spb.jpg)

```
   <com.kofigyan.stateprogressbar.StateProgressBar
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   app:spb_currentStateNumber="four"
   app:spb_maxStateNumber="five"
   app:spb_stateNumberIsDescending="true"/>

```



  -  Check States Completed

  ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/check_states_completed.png)

```
   <com.kofigyan.stateprogressbar.StateProgressBar
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   app:spb_currentStateNumber="three"
   app:spb_maxStateNumber="four"
   app:spb_checkStateCompleted="true"/>

 ```



  - Check All States

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/check_all_states.png)

  ```
         <com.kofigyan.stateprogressbar.StateProgressBar
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         app:spb_currentStateNumber="three"
         app:spb_maxStateNumber="four"
         app:spb_enableAllStatesCompleted="true"/>

  ```

  - Animate to Current State

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/anim_to_current.gif)

 ```
     <com.kofigyan.stateprogressbar.StateProgressBar
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         app:spb_currentStateNumber="three"
         app:spb_maxStateNumber="four"
         app:spb_stateBackgroundColor="#BDBDBD"
         app:spb_stateForegroundColor="#DB0082"
         app:spb_stateNumberBackgroundColor="#808080"
         app:spb_stateNumberForegroundColor="#eeeeee"
         app:spb_currentStateDescriptionColor="#DB0082"
         app:spb_stateDescriptionColor="#808080"
         app:spb_animateToCurrentProgressState="true"
         app:spb_checkStateCompleted="true"/>       
```



 - Add Click Listener to State Items

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/click_listener_spb.gif)

 ```
     mStepper.setOnStateItemClickListener(new OnStateItemClickListener() {
                 @Override
                 public void onStateItemClick(StateProgressBar mStepper, StateItem stateItem, int stateNumber, boolean isCurrentState) {
                     Toast.makeText(getApplicationContext(), "state Clicked Number is " + stateNumber, Toast.LENGTH_LONG).show();

                 }
             });
```


- Add Description Data to StateProgressBar

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/add_description_data.png)

```
 <com.kofigyan.stateprogressbar.StateProgressBar
 android:id="@+id/your_state_progress_bar_id"
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 app:spb_currentStateNumber="two"
 app:spb_maxStateNumber="four"/>

String[] descriptionData = {"Details", "Status", "Photo", "Confirm"};

@Override
protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.your_layout);

 StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
 stateProgressBar.setStateDescriptionData(descriptionData);

}

```


- Add Custom Font to State Items and State Description Data

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/custom_font_spb.jpg)

```
 String[] descriptionData = {"Details", "Status", "Photo", "Confirm"};

@Override
protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.your_layout);

 StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
 stateProgressBar.setStateDescriptionData(descriptionData);

   stateProgressBar.setStateDescriptionTypeface("fonts/RobotoSlab-Light.ttf");
   stateProgressBar.setStateNumberTypeface("fonts/Questrial-Regular.ttf");


}

```


- Change Colors (State Background , State Foreground, State Number Background ,State Number Foreground, Current State Description, State Description)

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/state_color_change.png)

 ```
 <com.kofigyan.stateprogressbar.StateProgressBar
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:spb_currentStateNumber="three"
    app:spb_maxStateNumber="four"
    app:spb_stateBackgroundColor="#BDBDBD"
    app:spb_stateForegroundColor="#009688"
    app:spb_stateNumberBackgroundColor="#808080"
    app:spb_stateNumberForegroundColor="#eeeeee"
    app:spb_currentStateDescriptionColor="#009688"
    app:spb_stateDescriptionColor="#808080"
    app:spb_checkStateCompleted="true"/>

 ```


-  Description Top Spacing

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/spb_description_top_spacing.png)

 ```
 <com.kofigyan.stateprogressbar.StateProgressBar
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         app:spb_descriptionTopSpaceIncrementer="5dp"/>


         String[] descriptionData = {"Details", "Status", "Photo", "Confirm"};

         @Override
         protected void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
             setContentView(R.layout.your_layout);

             StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
             stateProgressBar.setStateDescriptionData(descriptionData);

         }

 ```





- Change Dimensions (State, State Number ,State Line and State Description sizes)

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/state_dimension_change.png)

 ```
<com.kofigyan.stateprogressbar.StateProgressBar
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 app:spb_descriptionTopSpaceIncrementer="2dp"
 app:spb_stateDescriptionSize="20sp"
 app:spb_stateLineThickness="10dp"
 app:spb_stateSize="40dp"
 app:spb_stateTextSize="15sp" />
```


- Add Multiline Description Data to StateProgressBar

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/desc_multiline_spb.jpg)

```
 <com.kofigyan.stateprogressbar.StateProgressBar
 android:id="@+id/your_state_progress_bar_id"
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 app:spb_currentStateNumber="three"
 app:spb_maxStateNumber="five"/>

    String[] descriptionData = {"Details\nPlace", "Status\nPrice", "Photo\nShoot", "Confirm\nResponse" , "Buy\nDone"};

@Override
protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.your_layout);

 StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
 stateProgressBar.setStateDescriptionData(descriptionData);

}

```

- Justify and Spacing for Multiline Description Data

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/justify_spacing_multiline.jpg)

```
 <com.kofigyan.stateprogressbar.StateProgressBar
 android:id="@+id/your_state_progress_bar_id"
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 app:spb_currentStateNumber="three"
 app:spb_maxStateNumber="five"
 app:spb_justifyMultilineDescription="true"
 app:spb_descriptionLinesSpacing="5dp"/>

    String[] descriptionData = {"Details\nPlace", "Status\nPrice", "Photo\nShoot", "Confirm\nResponse" , "Buy\nDone"};

@Override
protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.your_layout);

 StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
 stateProgressBar.setStateDescriptionData(descriptionData);

}

```


##  Developer

  Kofi Gyan
  (kofigyan2011@gmail.com) Currently opened to android engineer positions(remote/relocation)

##  License

 Copyright 2016 Kofi Gyan.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   `http://www.apache.org/licenses/LICENSE-2.0`

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
