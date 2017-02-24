# BarterBayUCSB
CS48 project - G07

README
-----------------------
Steps to run: \n

Option 1: build from Android Studio

	1. Download and install Android Studio with default libraries as well as the libraries you are prompted to install and/or update.
	2. Create a new project with the following configuration:
		Application name: BarterBay
		Company Domain: barterbayucsb.com
		next->check only Phone and Tablet, minimum SDK API 21: Android 5.0
		next-> Add No Activity
		finish
	3. Overwrite new project's src directory with the provided src directory.
	4. Open project pane, navigate to Gradle Scripts/build.gradle (Module: app)
	5. Copy/Paste the following lines under dependencies:
		compile 'com.google.android.gms:play-services-maps:10.0.1'
    		compile 'com.google.android.gms:play-services:10.0.1'
		compile 'com.android.support:design:25.1.1'
    		compile 'com.android.support:support-v4:25.1.1'
    		compile 'com.android.support:cardview-v7:25.1.1'
    		compile 'com.android.support:recyclerview-v7:25.1.1'
	6. Either build apk from Android Studio (hammer button) or configure a virtual device and run using the AVD menus and the green play button.
	6a. If you built the apk, navigate to your new project's build/apk directory. Refer to Option 2 from here.


Option 2: Use the provided Debug.apk provided

	1. Connect your android device and install any drivers required to access internal storage
	2. From this directory copy the Debug.apk file into any accessible directory
	3. (If necessary) Install a file browser. Suggested: root browser, available on Google Play Store
	4. Open Debug.apk on your device using any file browser, and click install
	5. Open BarterBay from your app drawer if installed successfully.


Known bugs:
	in UploadPicActivity (reachable from main menu's floating action button > PostActivity's upload image button):
		If an image that is currently in the process of being synced to Google Photos (the thumbnail will still be selectable) is selected, the app can crash

How to use:
	When prompted to sign in, enter any (or no) information. This is just a placeholder for now.
	Press the Local Offers button in the main menu to view all locally stored offers, up to the first 7 in the device's offers directory. If there are less than 7 offers there will be test offers displayed.
	Press the floating action button in the main menu to create a new post. Submitting this post will save it to the device's offers directory.
	

