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
import { Component, OnInit, OnDestroy} from '@angular/core';

import {
    FnService,
    LogService,
    WebSocketService,
    SortDir, TableBaseImpl, TableResponse
} from 'gui2-fw-lib';
import { StorageService } from '../roadm/services/storage.service';
import { ActivatedRoute, Router } from '@angular/router';

/**
 * Model of the response from WebSocket
 */
interface RoadmDeviceTableResponse extends TableResponse {
    roadms: RoadmDevice[];
}

/**
 * Model of the ROADM devices returned from the WebSocket
 */
interface RoadmDevice {
    available: boolean;
    chassisid: string;
    hwVersion: string;
    id: string;
    master: string;
    Vendor: string;
    name: string;
    ports: number;
    protocol: string;
    serial: string;
    swVersion: string;
    type: string;
    _iconid_available: string;
    _iconid_type: string;
}


/**
 * ONOS GUI -- Roadm Device View Component
 */
@Component({
    selector: 'roadm-device',
    templateUrl: './roadm.component.html',
    styleUrls: ['./roadm.component.css', './roadm.theme.css', '../../../fw/widget/table.css', '../../../fw/widget/table.theme.css']
})
export class RoadmDeviceComponent extends TableBaseImpl implements OnInit, OnDestroy {

    // TODO: Update for LION

    flowTip = 'Show flow view for selected device';
    portTip = 'Show port view for selected device';
    groupTip = 'Show group view for selected device';
    meterTip = 'Show meter view for selected device';
    pipeconfTip = 'Show pipeconf view for selected device';
    public picUrl='https://pic2.zhimg.com/80/v2-6a9d7ae6a58b0c147c66222024cd273b_720w.jpg?source=d16d100b';
    public picUrl1='https://pic3.zhimg.com/80/v2-a6c3103f731b16d68a7f4d815969e249_720w.jpg?source=d16d100b';
    public picUrl2='https://pic2.zhimg.com/v2-f96eed5df9f4d53d77c5151c9efbc2bd_b.jpg';
    public picUrl3='https://pic2.zhimg.com/v2-8035ed5023a1897c3e541d6c7dfd1411_b.jpg';
    public picUrl4='https://pic2.zhimg.com/v2-bee00f860d867504fc9417cdbb706171_b.jpg';
    public picUrl5='https://pic3.zhimg.com/v2-68cbe763ac287d1ea583fde16c97bcda_b.jpg';
    public picUrl6='https://pic4.zhimg.com/v2-9dc5f402f62c3c2e943d0f83328f873b_b.jpg';
    public picUrl7='https://pic3.zhimg.com/v2-9f960ae048d0eec9111ed233880f527e_b.jpg';
    public reveal1:boolean=false;
    public reveal2:boolean=false;
    public reveal3:boolean=false;
    public sev:any={
        sne:"",
        dne:"",
        bw:"",
        CT:new Date,
        xh:0
    }

    constructor(
        protected fs: FnService,
        protected log: LogService,
        protected as: ActivatedRoute,
        protected router: Router,
        protected wss: WebSocketService,
        public storage:StorageService,
    ) {
        super(fs, log, wss, 'roadm');
        this.responseCallback = this.deviceResponseCb;

        this.as.queryParams.subscribe(params => {
            this.selId = params['devId'];

        });

        this.payloadParams = {
            devId: this.selId
        };

        this.sortParams = {
            firstCol: 'name',
            firstDir: SortDir.asc,
            secondCol: 'id',
            secondDir: SortDir.desc,
        };
    }
    clean(){
    setTimeout(() => {window.location.reload();},500);
   
    }
    SendMessageToBackward(){
            let obj1=this.storage.get('sev')//导出服务
            if(obj1){
                  this.sev=obj1;
            }
            console.log(this.sev);
            if(this.wss.isConnected){
                this.wss.sendEvent('helloworldRequest',{
                'thefirstword':'hello',
                'thesecondworld':'world',
                });
                this.log.info('websocket发送helloworld成功');
            }
    }
    ngOnInit() {
        this.init();
        this.log.debug('RoadmDeviceComponent initialized');
    }

    ngOnDestroy() {
        this.destroy();
        this.log.debug('RoadmDeviceComponent destroyed');
    }

    deviceResponseCb(data: RoadmDeviceTableResponse) {
        this.log.debug('Device response received for ', data.roadms.length, 'roadm devices');
    }

    navto(path) {
        this.log.debug('navigate');
        if (this.selId) {
            this.router.navigate([path], { queryParams: { devId: this.selId } });
        }
    }

}
