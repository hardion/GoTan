//
//  testJSONAppDelegate.h
//  testJSON
//
//  Created by Vincent Hardion on 03/04/11.
//  Copyright 2011 Synchrotron Soleil. All rights reserved.
//

#import <UIKit/UIKit.h>

@class testJSONViewController;

@interface testJSONAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    testJSONViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet testJSONViewController *viewController;

@end

