/*
Copyright 2023 Norman Breau 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

#import "ViewController.h"
#import <EchoPlugin.h>
#import <NBSFuse/NBSFuse.h>
#import <permissionplugin/PermissionPlugin.h>

@implementation ViewController

- (instancetype) init {
    self = [super init];
    [self $initialize];
    return self;
}

- (instancetype) initWithCoder:(NSCoder*) coder {
    self = [super initWithCoder:coder];
    [self $initialize];
    return self;
}

- (instancetype) initWithNibName:(NSString*) nibNameOrNil bundle:(NSBundle*) nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    [self $initialize];
    return self;
}

- (void) $initialize {
    $fuseController = [[NBSFuseViewController alloc] init];
    NBSFuseContext* context = [$fuseController getContext];
    [context registerPlugin:[[EchoPlugin alloc] init: context]];
    [context registerPlugin:[[PermissionPlugin alloc] init: context]];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self addChildViewController: $fuseController];
    [self.view addSubview: $fuseController.view];
    
    if (@available(iOS 16.4, *)) {
        [[$fuseController getContext] getWebview].inspectable = true;
    }
}


@end
