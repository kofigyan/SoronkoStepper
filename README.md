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

 - A Two-State SoronkoStepper

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/two_state_spb.png)

```
<com.kofigyan.soronkostepper.SoronkoStepper
            android:layout_width="match_parent"
            android:id="@+id/soronko_stepper"
            app:ssv_maxStepperNumber="two"
            app:ssv_currentStepperNumber="one"
            android:layout_height="wrap_content"/>

```


 - A Three-State StateProgressBar

  ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/three_state_spb.png)

```
  <com.kofigyan.soronkostepper.SoronkoStepper
              android:layout_width="match_parent"
              android:id="@+id/soronko_stepper"
              app:ssv_maxStepperNumber="three"
              app:ssv_currentStepperNumber="two"
              android:layout_height="wrap_content"/>

  ```


  -  A Four-State StateProgressBar

  ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/four_state_spb.png)

```
   <com.kofigyan.soronkostepper.SoronkoStepper
               android:layout_width="match_parent"
               android:id="@+id/soronko_stepper"
               app:ssv_maxStepperNumber="four"
               app:ssv_currentStepperNumber="three"
               android:layout_height="wrap_content"/>

```


-  A Five-State StateProgressBar

  ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/five_state_spb.jpg)

```
   <com.kofigyan.soronkostepper.SoronkoStepper
               android:layout_width="match_parent"
               android:id="@+id/soronko_stepper"
               app:ssv_maxStepperNumber="five"
               app:ssv_currentStepperNumber="four"
               android:layout_height="wrap_content"/>

```
 



  -  Check States Completed

  ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/check_states_completed.png)

```
   <com.kofigyan.soronkostepper.SoronkoStepper
               android:layout_width="match_parent"
               android:id="@+id/soronko_stepper"
               app:ssv_checkStepperCompleted="true"
               app:ssv_maxStepperNumber="four"
               app:ssv_currentStepperNumber="three"
               android:layout_height="wrap_content"/>

 ```
   
   
- Add Description Data to StateProgressBar

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/add_description_data.png)

```
 <com.kofigyan.soronkostepper.SoronkoStepper
                android:layout_width="match_parent"
                android:id="@+id/soronko_stepper"
                app:ssv_maxStepperNumber="four"
                app:ssv_currentStepperNumber="three"
                android:layout_height="wrap_content"/>

private val descriptionData = arrayOf("Details", "Status", "Photo", "Confirm", "Done")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(your_layout)
        mStepper = findViewById(R.id.soronko_stepper)
        mStepper.descriptionData = descriptionData
    }

```


- Add Custom Font to State Items and State Description Data

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/custom_font_spb.jpg)

```
 private val descriptionData = arrayOf("Details", "Status", "Photo", "Confirm", "Done")
 
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(your_layout)
         mStepper = findViewById(R.id.soronko_stepper)
         mStepper.descriptionData = descriptionData
        
        mStepper.stepperNumberTypeface = "fonts/RobotoSlab-Light.ttf"
        mStepper.stepperDescriptionTypeface = "fonts/RobotoSlab-Light.ttf"         
         
     }


```


- Change Colors (State Background , State Foreground, State Number Background ,State Number Foreground, Current State Description, State Description)

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/state_color_change.png)

 ```
 <com.kofigyan.soronkostepper.SoronkoStepper
             android:layout_marginTop="6dp"
             android:layout_width="match_parent"
             android:id="@+id/soronko_stepper"
             app:ssv_maxStepperNumber="four"
             app:ssv_currentStepperNumber="one"
             app:ssv_checkStepperCompleted="true"
             android:layout_height="wrap_content"
             app:ssv_currentStepperDescriptionColor="#009688"
             app:ssv_stepperBackgroundColor="#BDBDBD"
             app:ssv_stepperDescriptionColor="#808080"
             app:ssv_stepperForegroundColor="#009688"
             app:ssv_stepperNumberBackgroundColor="#808080"
             app:ssv_stepperNumberForegroundColor="#eeeeee"/>

 ```
 
 
- Change Dimensions (State, State Number and State Description sizes)

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/state_dimension_change.png)

 ```
<com.kofigyan.soronkostepper.SoronkoStepper
            android:layout_width="match_parent"
            android:id="@+id/soronko_stepper_two"
            app:ssv_maxStepperNumber="four"
            android:layout_marginTop="15dp"
            app:ssv_currentStepperNumber="one"
            app:ssv_stepperSize="30dp"
            app:ssv_stepperTextSize="18sp"
            android:layout_height="wrap_content"/>
```


- Add Multiline Description Data to StateProgressBar

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/desc_multiline_spb.jpg)

```
 private val descriptionData = arrayOf("Details", "Status", "Photo", "Confirm", "Done")
  
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(your_layout)
          mStepper = findViewById(R.id.soronko_stepper)
          mStepper.descriptionData = descriptionData
          
        mStepper.descriptionMultilineTruncateEnd = 2       
      }

```

- Ellipsized Description Data

 ![alt tag](https://raw.githubusercontent.com/kofigyan/StateProgressBar/master/screenshots/justify_spacing_multiline.jpg)

```
 private val descriptionData = arrayOf("DetailsDetailsDetails", "StatusStatusStatus", "PhotoPhotoPhoto", "Confirm", "Done")
   
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(your_layout)
           mStepper = findViewById(R.id.soronko_stepper)
           mStepper.descriptionData = descriptionData
          
         mStepper.descriptionTruncateEnd = true
            
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
