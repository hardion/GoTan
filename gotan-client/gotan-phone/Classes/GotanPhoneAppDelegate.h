//
//  GotanPhoneAppDelegate.h
//  GotanPhone
//
//  Created by Vincent Hardion on 06/04/11.
//  Copyright 2011 Synchrotron Soleil. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GotanPhoneAppDelegate : NSObject <UIApplicationDelegate, UITabBarControllerDelegate> {
    UIWindow *window;
    UITabBarController *tabBarController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet UITabBarController *tabBarController;

@end
