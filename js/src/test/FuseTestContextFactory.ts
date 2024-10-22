
/*
Copyright 2024 Breautek

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

import { AbstractFuseAPIFactory } from '../AbstractFuseAPIFactory';
import { FuseContext } from '../FuseContext';
import {FuseTestContext} from './FuseTestContext';
import {FuseContextFactory} from '../FuseContextFactory';
import { IFuseLogger } from '../IFuseLogger';
import { Platform } from '../Platform';

export class FuseTestContextFactory extends FuseContextFactory {
    public override create(platform: Platform, apiFactory: AbstractFuseAPIFactory, logger: IFuseLogger): FuseContext {
        if (platform === Platform.TEST) {
            return new FuseTestContext(apiFactory, logger);
        }
        else {
            return super.create(platform, apiFactory, logger);
        }
    }
}