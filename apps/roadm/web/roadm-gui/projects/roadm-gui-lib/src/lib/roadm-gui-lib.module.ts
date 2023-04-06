/*
 * Copyright 2019-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Gui2FwLibModule } from 'gui2-fw-lib';
import { RoadmDeviceComponent } from './roadm/roadm.component';
import { RoadmRoutingModule } from './roadm-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RoadmPortComponent } from './port/port.component';
import { City1Component } from './roadm/components/city1/city1.component';
import { City1dataService } from './roadm/services/city1data.service';
import { City2Component } from './roadm/components/city2/city2.component';
import { City2dataService } from './roadm/services/city2data.service';
import { StorageService } from './roadm/services/storage.service';

@NgModule({
    declarations: [
        RoadmDeviceComponent,
        RoadmPortComponent,
        City1Component,
        City2Component,
    ],
    imports: [
        RoadmRoutingModule,
        Gui2FwLibModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule
    ],
    exports: [
        RoadmDeviceComponent,
        RoadmPortComponent,
    ],
    providers: [
        City1dataService,
        City2dataService,
        StorageService
    ]
})
export class RoadmGuiLibModule { }
