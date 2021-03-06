Steps for account creation:

	1. Visit the following website:  https://nameless-temple-44705.herokuapp.com/ (accessible from the app's login screen)
	2. Follow the account creation process.
	3. Verify email address.
	4. (Optional) Customize account on website.

Steps to run:

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

	Sometimes date strings will not be displayed correctly when viewing an individual post; however, posts are still sorted correctly by age when that option is selected.

How to use:

	When prompted to sign in, enter information.
	Press the Local Offers button in the main menu to view all locally stored offers.
	Press the floating action button in Local Offers to view all offers in the area using maps feature.
	Press the floating action button in the main menu to create a new post. 	
	Submitting this post will save it to the device's offers directory and to the account stored on the server.
	Press any offer to view its price, a picture, a description, and how to contact the owner.

Implementation notes:

	Phone numbers and confirmations were not completed in implementation.
	Reviews are only saved to RAM currently; you can write reviews for a user and they will display until you navigate away from that user. After navigating away, the reviews will disappear and the user will get their default rating of 3 stars back.
