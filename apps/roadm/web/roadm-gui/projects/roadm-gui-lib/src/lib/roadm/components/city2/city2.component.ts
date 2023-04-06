import { Component, OnInit, AfterViewInit } from '@angular/core';
import * as d3 from 'd3';
import { City2dataService } from '../../../roadm/services/city2data.service';
import { StorageService } from '../../../roadm/services/storage.service';
import {
    FnService,
    LogService,
    WebSocketService,
    SortDir, TableBaseImpl, TableResponse
} from 'gui2-fw-lib';


@Component({
  selector: 'roadm-app-city2',
  templateUrl: './city2.component.html',
  styleUrls: ['./city2.component.css']
})
export class City2Component {
  public switch:boolean=false;
  public NElist:any[]=["NE1","NE2"];
  public topoPanel:any={
    SNE:"",
    DNE:"",
    BW:""
  }
  public sev:any={
    sne:"",
    dne:"",
    bw:"",
    route:"",
    CT:new Date,
    xh:0
  }

  public sevlist:any[]=[];
    public svg:any;
    //     public dataset = [50,44,120,88,99,167,142];
    public height =1600;
    public width = 1600;
    //     public padding = {top:20, left:20, right:20, bottom:20};
    //     public rectStep = 35;
    //     public rectWidth = 30;
    public nodes:any;
    public simulationNode:any;
    public links:any;
    public simulationLink:any;
    public simulationText:any;
    public simulation:any;
    public nodes_num:number;
    public links_num:number;
    public pic3 = "https://pic4.zhimg.com/80/v2-207e1cf40966e6f3f18fd6558015de3f_720w.jpg";
    public service_list:any;

    public ID_list:any[]=[];

    protected handlers: string[] = [];
    protected topoData1: string = '';
    public path1=false;
    public path11=false;

    constructor(public data2:City2dataService,
                public storage:StorageService,
                protected fs: FnService,
                protected log: LogService,
                protected wss: WebSocketService,){
    this.nodes=this.data2.nodes;
    console.log(this.nodes);
    this.links=this.data2.links;
    console.log(this.links);
    this.nodes_num=this.nodes.length;
    this.links_num=this.links.length;
    console.log(this.nodes_num);
    console.log(this.links_num);
    this.service_list=this.data2.serv_list;
    let fgdgfg=this.nodes
                            for(let item of fgdgfg){
                                this.ID_list.push(JSON.parse(JSON.stringify(item.name)));
                            }
                            console.log(this.ID_list);
    }
 SendMessageToBackward(){
            if(this.wss.isConnected){
                this.wss.sendEvent('helloworldRequest',{
                'city':'2',
                'source':this.topoPanel.SNE,
                'target':this.topoPanel.DNE,
                });
                this.log.info('websocket发送helloworld成功');
            }
 }
 ReceiveMessageFromBackward(){
         this.wss.bindHandlers(new Map<string,(data)=>void>([
             ['hiResponse',(data)=>{
                 this.log.info(data);
                 this.topoData1 = data["receive message"];
                 console.log('jieshou',this.topoData1);
                 console.log(typeof this.topoData1);
             }]
         ]));

//          this.handlers.push('hiResponse');
//          this.SendMessageToBackward();

   }
  sevpush(){
    this.sevlist.push(JSON.parse(JSON.stringify(this.sev)));
    this.storage.set('sevlist2',this.sevlist);//装入服务
//     window.location.reload();

  }

  sub(){
    this.sev.sne=this.topoPanel.SNE;
    this.sev.dne=this.topoPanel.DNE;
    this.sev.bw='默认';
    this.sev.route=this.topoData1.slice(0,-2);
    this.sev.CT=new Date;
    this.sev.xh+=1;
    this.storage.set('sev2',this.sev);
    this.sevpush();
    console.log('算路结果',this.topoData1);
    this.path1=false;
    this.path11=true;
  }
  aa(){
     this.path1=true;
     this.path11=false;
     this.SendMessageToBackward();
     this.ReceiveMessageFromBackward();
     setTimeout(() => {this.sub();},2000);
  }
  show(){
    this.switch=true;
  }
  vanish(){
    this.switch=false;
  }
  initialize(){
    this.sevlist=[];
    this.sev={
                sne:"",
                dne:"",
                bw:"",
                route:"",
                CT:new Date,
                xh:0
              };
    this.storage.set('sevlist2',this.sevlist);
    this.storage.set('sev2',this.sev);
//     window.location.reload();
  }
  ngOnInit() {
    let list1=this.storage.get('sevlist2')//导出服务
    if(list1){
      this.sevlist=list1;
    }
    let obj1=this.storage.get('sev2')//导出服务
    if(obj1){
      this.sev=obj1;
    }
  }
   ngAfterViewInit(){
         let sev_list=this.service_list;
         this.svg=d3.select("#mainsvg").append("svg")
//             .attr("height",this.height).attr("width",this.width).attr('class','insvg');
         let xixixi=this.svg;
         this.simulation=d3.forceSimulation(this.nodes)
                             .force('charge',d3.forceManyBody())
                             .force('link',d3.forceLink(this.links))
                             .force('center',d3.forceCenter(800,800))

         this.simulation.alphaDecay(0.05);
         this.simulation.force('charge')
                             .strength(-100);
         this.simulation.force('link')
                             .id(d => d.id)
                             .distance(100)
                             .strength(0.1)
                             .iterations(1);
         let hahaha=this.simulation;
         this.svg.call(d3.zoom()
                                .scaleExtent([0.1, 10])
                                .on('start', () => { // zoom 事件发生前 将变小手

                                   xixixi.style('cursor', 'pointer')
                                })
                                .on('zoom', () => {

                                   xixixi.attr('transform',
                                      d3.event.transform)
                               })
                               .on('end', () => {
                                   xixixi.style('cursor', 'default')
                               })
                               );
         this.simulationLink=this.svg.selectAll('line').data(this.links).enter().append('line').attr('stroke','black').attr('opacity',0.8).attr('stroke-width',0.5);
         this.simulationNode=this.svg.selectAll('circle').data(this.nodes).enter().append('circle').attr('r',8).attr('fill','blue').call(d3.drag().on('start',function(d){
                                                                                                                                                                             if(!d3.event.active){
                                                                                                                                                                                hahaha.alphaTarget(.2).restart();
                                                                                                                                                                             }
                                                                                                                                                                             d.fx=d.x;
                                                                                                                                                                             d.fy=d.y;
                                                                                                                                                                         })
                                                                                                                                                  .on('drag',function(d){
                                                                                                                                                                            d.fx=d3.event.x;
                                                                                                                                                                            d.fy=d3.event.y;
                                                                                                                                                                       })
                                                                                                                                                  .on('end',function(d){
                                                                                                                                                                            if(!d3.event.active){
                                                                                                                                                                                hahaha.alphaTarget(0);

                                                                                                                                                                            }
                                                                                                                                                                            d.fx=null;
                                                                                                                                                                            d.fy=null;
                                                                                                                                                                       })
                                                                                                                      );
         this.simulationText=this.svg.selectAll('text').data(this.nodes).enter().append('text').attr("text-anchor", "middle").text(d => d.name).attr("dy", "25px");
         let LINKS=this.simulationLink;
         let NODES=this.simulationNode;
         let TEXTS=this.simulationText;

                  let p1=document.createElement('p');
                  let p2=document.createElement('p');
                  let p3=document.createElement('p');
                  let p4=document.createElement('p');
                  let p5=document.createElement('p');
                  let p6=document.createElement('p');
                  let p7=document.createElement('p');
                  let p8=document.createElement('p');
                  let p9=document.createElement('p');
                  let p10=document.createElement('p');
                  let p11=document.createElement('p');
                  let p12=document.createElement('p');
                  let p13=document.createElement('p');
                  let p14=document.createElement('p');
//                   let p15=document.createElement('p');
//                   let p16=document.createElement('p');
//
                  NODES.on('click',function(d){
                  p1.innerHTML="名称:"+d.name;
                  p2.innerHTML="包含业务:"+d.service;
                  p3.innerHTML="设备ID:"+d.device_ID;
                  p4.innerHTML="网管ID:"+d.controler_ID;
                  p5.innerHTML="创建者:"+d.creater;
                  p6.innerHTML="绑定IP:"+d.bind_ip;
                  p7.innerHTML="设备类型:"+d.device_type;
                  p8.innerHTML="网元类型:"+d.type;
                  p9.innerHTML="速度等级:"+d.speed_rank;
                  p10.innerHTML="IP地址:"+d.ip;
                  p11.innerHTML="线缆数量:"+d.core_num;
                  p12.innerHTML="公务号码:"+d.official_num;
                  p13.innerHTML="通信状态:"+d.com_status;
                  p14.innerHTML="制造厂商:"+d.manufacturer;
                  let mctir=d.service.slice(1,-1);
                                    let shuzu=mctir.split(',');
                                    let zhen=[];
                                    zhen=shuzu.map(item => {
                                        return +item;
                                    });
                                    console.log(zhen);
                                    let sss=document.getElementById('services');
                                    sss.innerHTML="";
                                    for(let item of zhen){
                                        console.log(item);
                                        if(item > 0){
                                         let pnum=document.createElement('p');
                                         let pnam=document.createElement('p');

                                         pnum.innerHTML="业务序号:"+sev_list[item-1].num;
                                         pnam.innerHTML="业务名称:"+sev_list[item-1].name;

                                         document.getElementById('services').appendChild(pnum);
                                         document.getElementById('services').appendChild(pnam);
                                         console.log('ceshi',sev_list[item-1])

                                        }
                                    }
//                   p15.innerHTML="Gateway IP:"+d.gateway_IP;
//                   p16.innerHTML="Services List:"+d.service;
                  });
//
                  document.getElementById('nodes').appendChild(p1);
                  document.getElementById('nodes').appendChild(p2);
                  document.getElementById('nodes').appendChild(p3);
                  document.getElementById('nodes').appendChild(p4);
                  document.getElementById('nodes').appendChild(p5);
                  document.getElementById('nodes').appendChild(p6);
                  document.getElementById('nodes').appendChild(p7);
                  document.getElementById('nodes').appendChild(p8);
                  document.getElementById('nodes').appendChild(p9);
                  document.getElementById('nodes').appendChild(p10);
                  document.getElementById('nodes').appendChild(p11);
                  document.getElementById('nodes').appendChild(p12);
                  document.getElementById('nodes').appendChild(p13);
                  document.getElementById('nodes').appendChild(p14);
//                   document.getElementById('nodes').appendChild(p15);
//                   document.getElementById('nodes').appendChild(p16);

                  let q1=document.createElement('p');
                  let q2=document.createElement('p');
                  let q3=document.createElement('p');
                  let q4=document.createElement('p');
                  let q5=document.createElement('p');
                  let q6=document.createElement('p');
                  let q7=document.createElement('p');
//                   let q8=document.createElement('p');
                  LINKS.on("mouseover",function(d){
                  d3.select(this).attr('stroke','gray').attr('stroke-width',2);
                  })
                       .on("mouseout",function(d){
                                 d3.select(this).attr('stroke','black').attr('opacity',0.8).attr('stroke-width',0.5);
                                 })
                       .on('click',function(d){
                                q1.innerHTML="光缆段:"+d.section;
                                q2.innerHTML="光缆所属线路名称:"+d.line;
                                q3.innerHTML="光缆类型:"+d.fiber_type;
                                q4.innerHTML="电压等级:"+d.voltage;
                                q5.innerHTML="长度（km）:"+d.length_km;
                                q6.innerHTML="源网元:"+d.source_NE;
                                q7.innerHTML="宿网元:"+d.target_NE;
//                                 q8.innerHTML="Rank:"+d.rank;

                       });
                  document.getElementById('links').appendChild(q1);
                  document.getElementById('links').appendChild(q2);
                  document.getElementById('links').appendChild(q3);
                  document.getElementById('links').appendChild(q4);
                  document.getElementById('links').appendChild(q5);
                  document.getElementById('links').appendChild(q6);
                  document.getElementById('links').appendChild(q7);
//                   document.getElementById('links').appendChild(q8);
         this.simulation.on('tick',function(){
                 LINKS.attr('x1',d => d.source.x).attr('y1',d => d.source.y).attr('x2',d => d.target.x).attr('y2',d => d.target.y);
                 NODES.attr('cx', d => d.x).attr('cy', d => d.y);
                  TEXTS.attr('x',d => d.x).attr('y',d => d.y);
         });

//        this.svg.selectAll("rect").data(this.dataset).enter().append("rect").attr("fill","red").attr('x',(d,i)=>this.padding.left+i*this.rectStep).attr('y',(d,i)=>this.height-this.padding.bottom-d).attr("width",this.rectWidth).attr("height",d=>d);
    }
}
