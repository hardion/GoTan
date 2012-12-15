//
//  GotanDomain.h
//  GotanPhone
//
//  Created by Vincent Hardion on 06/04/11.
//  Copyright 2011 Synchrotron Soleil. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface GotanDomain : UITableViewController <UITableViewDelegate, UITableViewDataSource> {

	NSArray *tableViewArray;
	NSMutableData *responseData;

	
}

@property (nonatomic, retain) NSArray *tableViewArray;

@end
