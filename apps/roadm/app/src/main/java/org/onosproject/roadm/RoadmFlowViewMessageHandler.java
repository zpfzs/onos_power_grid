/*
 * Copyright 2016-present Open Networking Foundation
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
package org.onosproject.roadm;

import apps.roadm.app.src.main.java.org.onosproject.roadm.test;
import apps.roadm.app.src.main.java.org.onosproject.roadm.test1;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import org.onlab.osgi.ServiceDirectory;
import org.onlab.util.Frequency;
import org.onlab.util.Spectrum;
import org.onosproject.net.ChannelSpacing;
import org.onosproject.net.DeviceId;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.OchSignal;
import org.onosproject.net.PortNumber;
import org.onosproject.net.flow.FlowEntry;
import org.onosproject.net.flow.FlowId;
import org.onosproject.net.flow.FlowRuleService;
import org.onosproject.ui.RequestHandler;
import org.onosproject.ui.UiConnection;
import org.onosproject.ui.UiMessageHandler;
import org.onosproject.ui.table.TableModel;
import org.onosproject.ui.table.TableRequestHandler;
import org.onosproject.ui.table.cell.HexLongFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.IOException;
import java.util.*;

import static org.onosproject.ui.JsonUtils.node;
import static org.onosproject.ui.JsonUtils.number;
import static org.onosproject.net.Device.Type;

/**
 * Table-View message handler for ROADM flow view.
 */
public class RoadmFlowViewMessageHandler extends UiMessageHandler {

    private static final String ROADM_FLOW_DATA_REQ = "roadmFlowDataRequest";
    private static final String ROADM_FLOW_DATA_RESP = "roadmFlowDataResponse";
    private static final String ROADM_FLOWS = "roadmFlows";

    private static final String ROADM_SET_ATTENUATION_REQ = "roadmSetAttenuationRequest";
    private static final String ROADM_SET_ATTENUATION_RESP = "roadmSetAttenuationResponse";

    private static final String ROADM_DELETE_FLOW_REQ = "roadmDeleteFlowRequest";

    private static final String ROADM_CREATE_FLOW_REQ = "roadmCreateFlowRequest";
    private static final String ROADM_CREATE_FLOW_RESP = "roadmCreateFlowResponse";

    private static final String ROADM_SHOW_ITEMS_REQ = "roadmShowFlowItemsRequest";
    private static final String ROADM_SHOW_ITEMS_RESP = "roadmShowFlowItemsResponse";


    //后端给前端发消息hi sgp 一会儿删除
    private static final String HI_SGP_RESP = "hiResponse";
    private static final String THERECEIVE="receive message";

    //定义发送的字符
    public String theSource;

    public String theTarget;

    public String theCity;
    public String theSecond;
    //后端定义
    private static final String HELLOWORLD_REQ = "helloworldRequest";
    private static final String THE_SOURCE = "source";
    private static final String THE_TARGET = "target";
    private static final String THE_CITY = "city";

//    private static final String THE_SECOND_WORD



    private static final String ID = "id";
    private static final String FLOW_ID = "flowId";
    private static final String APP_ID = "appId";
    private static final String GROUP_ID = "groupId";
    private static final String TABLE_ID = "tableId";
    private static final String PRIORITY = "priority";
    private static final String PERMANENT = "permanent";
    private static final String TIMEOUT = "timeout";
    private static final String STATE = "state";
    private static final String IN_PORT = "inPort";
    private static final String OUT_PORT = "outPort";
    private static final String CHANNEL_SPACING = "spacing";
    private static final String CHANNEL_MULTIPLIER = "multiplier";
    private static final String CURRENT_POWER = "currentPower";
    private static final String ATTENUATION = "attenuation";
    private static final String HAS_ATTENUATION = "hasAttenuation";
    private static final String CHANNEL_FREQUENCY = "channelFrequency";

    private static final String[] COLUMN_IDS = {
            ID, FLOW_ID, APP_ID, GROUP_ID, TABLE_ID, PRIORITY, TIMEOUT,
            PERMANENT, STATE, IN_PORT, OUT_PORT, CHANNEL_SPACING, CHANNEL_MULTIPLIER,
            CHANNEL_FREQUENCY, CURRENT_POWER, ATTENUATION, HAS_ATTENUATION
    };

    public static Object getKey(Map map, Object value){
        List<Object> keyList = new ArrayList<>();
        for(Object key: map.keySet()){
            if(map.get(key).equals(value)){
                keyList.add(key);
            }
        }
        return keyList.get(0);
    }

    private RoadmService roadmService;
    private DeviceService deviceService;
    private FlowRuleService flowRuleService;

   // private String myList;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void init(UiConnection connection, ServiceDirectory directory) {
        super.init(connection, directory);
        roadmService = get(RoadmService.class);
        deviceService = get(DeviceService.class);
        flowRuleService = get(FlowRuleService.class);
    }

    @Override
    protected Collection<RequestHandler> createRequestHandlers() {
        return ImmutableSet.of(
                new FlowTableDataRequestHandler(),
                new SetAttenuationRequestHandler(),
                new DeleteConnectionRequestHandler(),
                new CreateConnectionRequestHandler(),
                new CreateShowItemsRequestHandler(),
                new SayHelloWorld()
        );
    }

    private String strTmp;
    private String myList;
    private Object i1;
    private Object i2;
    private Object i3;
    private Object i4;
    private String ii1;
    private String ii2;
    //定义方法
    private final class SayHelloWorld extends RequestHandler{
        private SayHelloWorld(){super(HELLOWORLD_REQ);}

        @Override
        public void process(ObjectNode payload){
            theSource = string(payload, THE_SOURCE);
            log.info("The theSource: {}", theSource);
            theTarget = string(payload, THE_TARGET);
            log.info("The theTarget : {}", theTarget);
            theCity = string(payload, THE_CITY);
            log.info("The theCity : {}", theCity);

            HashMap<String,String> mm4= new HashMap<>();
            HashMap<String,String> mm3= new HashMap<>();

            //城市1
            try {
                FileReader filePath=new FileReader("/home/xinjiang/onos/apps/roadm/app/src/main/java/org/onosproject/roadm/chengshi1.txt");
                BufferedReader buffReader = new BufferedReader(filePath);
                while(true){
                    strTmp = buffReader.readLine();
                    if (strTmp == null){
                        break;
                    }
                    StringBuffer mm = new StringBuffer(strTmp);
                    int m1=mm.indexOf(" ");
                    int m2=mm.indexOf(" ",m1+1);
                    String nnn=mm.substring(0,m1);
                    String nnnn=mm.substring(m1+1,m2);
                    String nn=mm.substring(m2+2, mm.length()-1);
                    mm3.put(nnn+"-"+nnnn,nn);
                }
                buffReader.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            log.info("fasadf{}",mm3.get("0-1"));

            String filePath2="/home/xinjiang/onos/apps/roadm/app/src/main/java/org/onosproject/roadm/chengshi2.txt";
            FileInputStream fin2 = null;
            try {
                fin2 = new FileInputStream(filePath2);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            InputStreamReader reader2 = new InputStreamReader(fin2);
            BufferedReader buffReader2 = new BufferedReader(reader2);
            String strTmp2 = "";

            //城市2
            while(true){
                try {
                    if ((strTmp2 = buffReader2.readLine()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                StringBuffer mm = new StringBuffer(strTmp2);
                int m1=mm.indexOf(" ");
                int m2=mm.indexOf(" ",m1+1);
                String nnn=mm.substring(0,m1);
                String nnnn=mm.substring(m1+1,m2);
                String nn=mm.substring(m2+2, mm.length()-1);
                mm4.put(nnn+"-"+nnnn,nn);
            }
            //m3 chengshi1 m4 chengshi2 luyou

            //chengshijianzhi
            List<Object> mm1=new ArrayList<>();
            String filePath3="/home/xinjiang/onos/apps/roadm/app/src/main/java/org/onosproject/roadm/chengshi11.txt";
            String filePath4="/home/xinjiang/onos/apps/roadm/app/src/main/java/org/onosproject/roadm/chengshi22.txt";
            //创建HashMAP
            HashMap<String, String> chengshi111 = new HashMap<String, String>();
            HashMap<String, String> chengshi222 = new HashMap<String, String>();

            FileInputStream fin3 = null;
            try {
                fin3 = new FileInputStream(filePath3);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            FileInputStream fin4 = null;
            try {
                fin4 = new FileInputStream(filePath4);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            InputStreamReader reader3 = new InputStreamReader(fin3);
            InputStreamReader reader4 = new InputStreamReader(fin4);
            BufferedReader buffReader3 = new BufferedReader(reader3);
            BufferedReader buffReader4 = new BufferedReader(reader4);
            String strTmp3 = "";
            String strTmp4 = "";
            while(true){
                try {
                    if (!((strTmp3 = buffReader3.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                StringBuffer mm = new StringBuffer(strTmp3);
                int m1=mm.indexOf("-");
                String nn=mm.substring(m1-1, mm.length());
                String nnn=mm.substring(0,m1-2);
                chengshi111.put(nnn,nn);
            }
            while(true){
                try {
                    if (!((strTmp4 = buffReader4.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                StringBuffer mm = new StringBuffer(strTmp4);
                int m1=mm.indexOf("\t");
//            System.out.println(mm);
                String nn=mm.substring(m1+1, mm.length());
                String nnn=mm.substring(0,m1);
                chengshi222.put(nnn,nn);
            }
            log.info("jianzhi{}",chengshi111.get("1"));
            log.info("jianzhi2{}",chengshi222.get("1"));

            if(theCity.equals("1")){
                i1 = getKey(chengshi111, theSource);
                i2 = getKey(chengshi111, theTarget);
                ii1=i1+"-"+i2;
            }
            if (theCity.equals("2")){
                i3 = getKey(chengshi222, theSource);
                i4 = getKey(chengshi222, theTarget);
                ii1=i3+"-"+i4;
            }
            log.info("i1{}",ii1);
            log.info("i2{}",i2);
//
////
            if (theCity.equals("1")) {
                myList="";
                if (mm3.get(ii1).equals("on") == false) {
                    //对城市1的路由信息处理成链表
                    ArrayList<String> sites = new ArrayList<String>();
                    for (String retval : mm3.get(ii1).split(", ")) {
                        sites.add(chengshi111.get(retval));
                        myList += chengshi111.get(retval) + "——";
                    }
                    //对链表序号找值
                }else {
                    myList="null——";
                }
                log.info("null{}",myList);
            }
//
            if (theCity.equals("2")) {
                myList="";
                if (mm4.get(ii1).equals("on") == false) {
                    //对城市2的路由信息处理成链表
                    ArrayList<String> sites2 = new ArrayList<String>();
                    for (String retval : mm4.get(ii1).split(", ")) {
                        sites2.add(chengshi222.get(retval));
                        myList += chengshi222.get(retval) + "——";
                    }
                    //System.out.println(sites2);
                    //System.out.println(myList2);
                    //对链表序号找值
                }else{
                    myList="null——";
                }
            }
            log.info("null{}",mm4.get("0-1"));

            String theResponse = myList;
            ObjectNode test = objectNode();
            test.put(THERECEIVE, theResponse);
            //riskLink.put(LINK_RISK_ID,linkId);
            sendMessage(HI_SGP_RESP, test);

        }
    }

    // Handler for sample table requests
    private final class FlowTableDataRequestHandler extends TableRequestHandler {

        private FlowTableDataRequestHandler() {
            super(ROADM_FLOW_DATA_REQ, ROADM_FLOW_DATA_RESP, ROADM_FLOWS);
        }

        @Override
        protected String[] getColumnIds() {
            return COLUMN_IDS;
        }

        @Override
        protected String noRowsMessage(ObjectNode payload) {
            return RoadmUtil.NO_ROWS_MESSAGE;
        }

        @Override
        protected TableModel createTableModel() {
            TableModel tm = super.createTableModel();
            tm.setFormatter(FLOW_ID, HexLongFormatter.INSTANCE);
            return tm;
        }

        @Override
        protected void populateTable(TableModel tm, ObjectNode payload) {
            DeviceId deviceId = DeviceId.deviceId(string(payload, RoadmUtil.DEV_ID));
            // Update flows
            Iterable<FlowEntry> flowEntries = flowRuleService.getFlowEntries(deviceId);
            for (FlowEntry flowEntry : flowEntries) {
                populateRow(tm.addRow(), flowEntry, deviceId);
            }
        }

        private void populateRow(TableModel.Row row, FlowEntry entry, DeviceId deviceId) {
            ChannelData cd = ChannelData.fromFlow(entry);
            String spacing = RoadmUtil.NA, multiplier = RoadmUtil.NA, channelFrequency = "";
            OchSignal ochSignal = cd.ochSignal();
            if (ochSignal != null) {
                Frequency spacingFreq = ochSignal.channelSpacing().frequency();
                spacing = RoadmUtil.asGHz(spacingFreq);
                int spacingMult = ochSignal.spacingMultiplier();
                multiplier = String.valueOf(spacingMult);
                channelFrequency = String.format(" (%sGHz)",
                        RoadmUtil.asGHz(Spectrum.CENTER_FREQUENCY.add(spacingFreq.multiply(spacingMult))));
            }

            row.cell(ID, entry.id().value())
                    .cell(FLOW_ID, entry.id().value())
                    .cell(APP_ID, entry.appId())
                    .cell(PRIORITY, entry.priority())
                    .cell(TIMEOUT, entry.timeout())
                    .cell(PERMANENT, entry.isPermanent())
                    .cell(STATE, entry.state().toString())
                    .cell(IN_PORT, cd.inPort().toLong())
                    .cell(OUT_PORT, cd.outPort().toLong())
                    .cell(CHANNEL_SPACING, spacing)
                    .cell(CHANNEL_MULTIPLIER, multiplier)
                    .cell(CHANNEL_FREQUENCY, channelFrequency)
                    .cell(CURRENT_POWER, getCurrentPower(deviceId, cd))
                    .cell(HAS_ATTENUATION, hasAttenuation(deviceId, cd))
                    .cell(ATTENUATION, getAttenuation(deviceId, cd));
        }

        private String getCurrentPower(DeviceId deviceId, ChannelData channelData) {
            if (hasAttenuation(deviceId, channelData)) {
                // report channel power if channel exists
                Double currentPower = roadmService.getCurrentChannelPower(deviceId,
                        channelData.outPort(), channelData.ochSignal());
                return RoadmUtil.objectToString(currentPower, RoadmUtil.UNKNOWN);
            }
            // otherwise, report port power
            Type devType = deviceService.getDevice(deviceId).type();
            PortNumber port = devType == Type.FIBER_SWITCH ? channelData.inPort() : channelData.outPort();
            Double currentPower = roadmService.getCurrentPortPower(deviceId, port);
            return RoadmUtil.objectToString(currentPower, RoadmUtil.UNKNOWN);
        }

        private String getAttenuation(DeviceId deviceId, ChannelData channelData) {
            OchSignal signal = channelData.ochSignal();
            if (signal == null) {
                return RoadmUtil.NA;
            }
            Double attenuation = roadmService.getAttenuation(deviceId, channelData.outPort(), signal);
            return RoadmUtil.objectToString(attenuation, RoadmUtil.UNKNOWN);
        }

        private boolean hasAttenuation(DeviceId deviceId, ChannelData channelData) {
            OchSignal signal = channelData.ochSignal();
            if (signal == null) {
                return false;
            }
            return roadmService.attenuationRange(deviceId, channelData.outPort(), signal) != null;
        }
    }

    // Handler for setting attenuation
    private final class SetAttenuationRequestHandler extends RequestHandler {

        // Error messages to display to user
        private static final String ATTENUATION_RANGE_MSG = "Attenuation must be in range %s.";
        private static final String NO_ATTENUATION_MSG = "Cannot set attenuation for this connection";

        private SetAttenuationRequestHandler() {
            super(ROADM_SET_ATTENUATION_REQ);
        }

        @Override
        public void process(ObjectNode payload) {
            DeviceId deviceId = DeviceId.deviceId(string(payload, RoadmUtil.DEV_ID));
            FlowId flowId = FlowId.valueOf(number(payload, FLOW_ID));
            // Get connection information from the flow
            FlowEntry entry = findFlow(deviceId, flowId);
            if (entry == null) {
                log.error("Unable to find flow rule to set attenuation for device {}", deviceId);
                return;
            }
            ChannelData channelData = ChannelData.fromFlow(entry);
            PortNumber port = channelData.outPort();
            OchSignal signal = channelData.ochSignal();
            Range<Double> range = roadmService.attenuationRange(deviceId, port, signal);
            Double attenuation = payload.get(ATTENUATION).asDouble();
            boolean validAttenuation = range != null && range.contains(attenuation);
            if (validAttenuation) {
                roadmService.setAttenuation(deviceId, port, signal, attenuation);
            }
            ObjectNode rootNode = objectNode();
            // Send back flowId so view can identify which callback function to use
            rootNode.put(FLOW_ID, payload.get(FLOW_ID).asText());
            rootNode.put(RoadmUtil.VALID, validAttenuation);
            if (range  == null) {
                rootNode.put(RoadmUtil.MESSAGE, NO_ATTENUATION_MSG);
            } else {
                rootNode.put(RoadmUtil.MESSAGE, String.format(ATTENUATION_RANGE_MSG, range.toString()));
            }
            sendMessage(ROADM_SET_ATTENUATION_RESP, rootNode);
        }

        private FlowEntry findFlow(DeviceId deviceId, FlowId flowId) {
            for (FlowEntry entry : flowRuleService.getFlowEntries(deviceId)) {
                if (entry.id().equals(flowId)) {
                    return entry;
                }
            }
            return null;
        }
    }

    // Handler for deleting a connection
    private final class DeleteConnectionRequestHandler extends RequestHandler {
        private DeleteConnectionRequestHandler() {
            super(ROADM_DELETE_FLOW_REQ);
        }

        @Override
        public void process(ObjectNode payload) {
            DeviceId deviceId = DeviceId.deviceId(string(payload, RoadmUtil.DEV_ID));
            FlowId flowId = FlowId.valueOf(payload.get(ID).asLong());
            roadmService.removeConnection(deviceId, flowId);
        }
    }

    // Handler for creating a creating a connection from form data
    private final class CreateConnectionRequestHandler extends RequestHandler {

        // Keys to load from JSON
        private static final String FORM_DATA = "formData";
        private static final String CHANNEL_SPACING_INDEX = "index";

        // Keys for validation results
        private static final String CONNECTION = "connection";
        private static final String CHANNEL_AVAILABLE = "channelAvailable";

        // Error messages to display to user
        private static final String IN_PORT_ERR_MSG = "Invalid input port.";
        private static final String OUT_PORT_ERR_MSG = "Invalid output port.";
        private static final String CONNECTION_ERR_MSG = "Invalid connection from input port to output port.";
        private static final String CHANNEL_SPACING_ERR_MSG = "Channel spacing not supported.";
        private static final String CHANNEL_ERR_MSG = "Channel index must be in range %s.";
        private static final String CHANNEL_AVAILABLE_ERR_MSG = "Channel is already being used.";
        private static final String ATTENUATION_ERR_MSG = "Attenuation must be in range %s.";

        private CreateConnectionRequestHandler() {
            super(ROADM_CREATE_FLOW_REQ);
        }

        @Override
        public void process(ObjectNode payload) {
            DeviceId did = DeviceId.deviceId(string(payload, RoadmUtil.DEV_ID));
            ObjectNode flowNode = node(payload, FORM_DATA);
            int priority = (int) number(flowNode, PRIORITY);
            boolean permanent = bool(flowNode, PERMANENT);
            int timeout = (int) number(flowNode, TIMEOUT);
            PortNumber inPort = PortNumber.portNumber(number(flowNode, IN_PORT));
            PortNumber outPort = PortNumber.portNumber(number(flowNode, OUT_PORT));
            ObjectNode chNode = node(flowNode, CHANNEL_SPACING);
            ChannelSpacing spacing = channelSpacing((int) number(chNode, CHANNEL_SPACING_INDEX));
            int multiplier = (int) number(flowNode, CHANNEL_MULTIPLIER);
            OchSignal och = OchSignal.newDwdmSlot(spacing, multiplier);
            double att = number(flowNode, ATTENUATION);

            boolean showItems = deviceService.getDevice(did).type() != Type.FIBER_SWITCH;
            boolean validInPort = roadmService.validInputPort(did, inPort);
            boolean validOutPort = roadmService.validOutputPort(did, outPort);
            boolean validConnect = roadmService.validConnection(did, inPort, outPort);
            boolean validSpacing = true;
            boolean validChannel = roadmService.validChannel(did, inPort, och);
            boolean channelAvailable = roadmService.channelAvailable(did, och);
            boolean validAttenuation = roadmService.attenuationInRange(did, outPort, att);

            if (validConnect) {
                if (validChannel && channelAvailable) {
                    if (validAttenuation) {
                        roadmService.createConnection(did, priority, permanent, timeout, inPort, outPort, och, att);
                    } else {
                        roadmService.createConnection(did, priority, permanent, timeout, inPort, outPort, och);
                    }
                }
            }

            String channelMessage = "Invalid channel";
            String attenuationMessage = "Invalid attenuation";
            if (showItems) {
                // Construct error for channel
                if (!validChannel) {
                    Set<OchSignal> lambdas = roadmService.queryLambdas(did, outPort);
                    if (lambdas != null) {
                        Range<Integer> range = channelRange(lambdas);
                        if (range.contains(och.spacingMultiplier())) {
                            // Channel spacing error
                            validSpacing = false;
                        } else {
                            channelMessage = String.format(CHANNEL_ERR_MSG, range.toString());
                        }
                    }
                }

                // Construct error for attenuation
                if (!validAttenuation) {
                    Range<Double> range = roadmService.attenuationRange(did, outPort, och);
                    if (range != null) {
                        attenuationMessage = String.format(ATTENUATION_ERR_MSG, range.toString());
                    }
                }
            }

            // Build response
            ObjectNode node = objectNode();
            node.set(IN_PORT, validationObject(validInPort, IN_PORT_ERR_MSG));
            node.set(OUT_PORT, validationObject(validOutPort, OUT_PORT_ERR_MSG));
            node.set(CONNECTION, validationObject(validConnect, CONNECTION_ERR_MSG));
            node.set(CHANNEL_SPACING, validationObject(validChannel || validSpacing, CHANNEL_SPACING_ERR_MSG));
            node.set(CHANNEL_MULTIPLIER, validationObject(validChannel || !validSpacing, channelMessage));
            node.set(CHANNEL_AVAILABLE, validationObject(!validChannel || channelAvailable, CHANNEL_AVAILABLE_ERR_MSG));
            node.set(ATTENUATION, validationObject(validAttenuation, attenuationMessage));
            sendMessage(ROADM_CREATE_FLOW_RESP, node);
        }

        // Returns the ChannelSpacing based on the selection made
        private ChannelSpacing channelSpacing(int selectionIndex) {
            switch (selectionIndex) {
                case 0: return ChannelSpacing.CHL_100GHZ;
                case 1: return ChannelSpacing.CHL_50GHZ;
                case 2: return ChannelSpacing.CHL_25GHZ;
                case 3: return ChannelSpacing.CHL_12P5GHZ;
                // 6.25GHz cannot be used with ChannelSpacing.newDwdmSlot
                // case 4: return ChannelSpacing.CHL_6P25GHZ;
                default: return ChannelSpacing.CHL_50GHZ;
            }
        }

        // Construct validation object to return to the view
        private ObjectNode validationObject(boolean result, String message) {
            ObjectNode node = objectNode();
            node.put(RoadmUtil.VALID, result);
            if (!result) {
                // return error message to display if validation failed
                node.put(RoadmUtil.MESSAGE, message);
            }
            return node;
        }

        // Returns the minimum and maximum channel spacing
        private Range<Integer> channelRange(Set<OchSignal> signals) {
            Comparator<OchSignal> compare =
                    (OchSignal a, OchSignal b) -> a.spacingMultiplier() - b.spacingMultiplier();
            OchSignal minOch = Collections.min(signals, compare);
            OchSignal maxOch = Collections.max(signals, compare);
            return Range.closed(minOch.spacingMultiplier(), maxOch.spacingMultiplier());
        }
    }

    private final class CreateShowItemsRequestHandler extends RequestHandler {
        private static final String SHOW_CHANNEL = "showChannel";
        private static final String SHOW_ATTENUATION = "showAttenuation";
        private CreateShowItemsRequestHandler() {
            super(ROADM_SHOW_ITEMS_REQ);
        }

        @Override
        public void process(ObjectNode payload) {
            DeviceId did = DeviceId.deviceId(string(payload, RoadmUtil.DEV_ID));
            Type devType = deviceService.getDevice(did).type();
            // Build response
            ObjectNode node = objectNode();
            node.put(SHOW_CHANNEL, devType != Type.FIBER_SWITCH);
            node.put(SHOW_ATTENUATION, devType == Type.ROADM);
            sendMessage(ROADM_SHOW_ITEMS_RESP, node);
        }
    }
}
