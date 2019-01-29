package com.smart.im.protocal;

import com.google.protobuf.InvalidProtocolBufferException;
import com.smart.im.protocal.proto.ProtocalEntity;
import com.smart.im.protocal.proto.ProtocalTypeEntity;

import java.util.UUID;


/**
 * @date : 2018/8/10 10:54
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :协议解析
 */

public class ProtocalFactory {

    public final static String SERVER_ID = "0";



    /**
     * byte[]解析成Protocal
     *
     * @param bytes
     * @return
     */
    public static ProtocalEntity.Protocal parseBytesToProtocal(byte[] bytes) {
        return ProtocalFactory.parse(bytes);
    }


    /**
     * ProtoBuf序列化bytes转protocal
     *
     * @param bytes
     * @return
     */
    public static ProtocalEntity.Protocal parse(byte[] bytes) {
        ProtocalEntity.Protocal protocal = null;
        try {
            protocal = ProtocalEntity.Protocal.parseFrom(bytes);
            return protocal;
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 创建连接（登录）
     *
     * @param userId  用户id
     * @param data  其他相关内容
     * @return
     */
    public static ProtocalEntity.Protocal createConnect(String userId, String data) {
        ProtocalEntity.Protocal.Builder builder = ProtocalEntity.Protocal.newBuilder();
        builder.setProtocalType(ProtocalTypeEntity.ProtocalType.CONNECT);
        builder.setDataContent(data);
        builder.setFrom(SERVER_ID);
        builder.setTo(userId);
        ProtocalEntity.Protocal protocal = builder.build();
        return protocal;

    }

    /**
     * 创建连接回应
     * @param to_user_id   目标用户ID
     * @param data         相关内容
     * @param fingerPrint  消息的ID
     * @return
     */
    public static ProtocalEntity.Protocal createConnAck(String to_user_id, String data,String fingerPrint) {


        ProtocalEntity.Protocal.Builder builder = ProtocalEntity.Protocal.newBuilder();
        builder.setProtocalType(ProtocalTypeEntity.ProtocalType.CONNACK);
        builder.setDataContent(data);
        builder.setFrom(SERVER_ID);
        builder.setTo(to_user_id);
        builder.setQoS(true);
        builder.setId(fingerPrint);
        builder.setTypeu(-1);
        ProtocalEntity.Protocal protocal = builder.build();
        return protocal;

    }

    /**
     * 创建发送信息
     *
     * @param dataContent     相关内容
     * @param from_user_id    来源用户ID
     * @param to_user_id      目标用户ID
     * @param QoS             是否心跳
     * @param fingerPrint     消息ID
     * @param typeu
     * @return
     */
    public static ProtocalEntity.Protocal createPublish(String dataContent, String from_user_id, String to_user_id
            , boolean QoS, String fingerPrint, int typeu) {
        ProtocalEntity.Protocal.Builder builder = ProtocalEntity.Protocal.newBuilder();
        builder.setProtocalType(ProtocalTypeEntity.ProtocalType.PUBLISH);
        builder.setDataContent(dataContent);
        builder.setFrom(from_user_id);
        builder.setTo(to_user_id);
        builder.setQoS(QoS);
        builder.setId(fingerPrint);
        builder.setTypeu(typeu);

        ProtocalEntity.Protocal protocal = builder.build();
        return protocal;

    }


    /**
     * 创建发送消息回应
     *
     * @param from_user_id       来源用户ID
     * @param to_user_id         目标用户ID
     * @param fingerPrint        消息ID
     * @return
     */
    public static ProtocalEntity.Protocal createPubAck(String from_user_id, String to_user_id
            , String fingerPrint) {
        return createPubAck(from_user_id, to_user_id, fingerPrint, false);
    }


    /**
     * 创建发送消息回应
     * @param from_user_id   来源用户ID
     * @param to_user_id     目标用户ID
     * @param fingerPrint    消息ID
     * @param bridge         是否桥接跨域
     * @return
     */
    public static ProtocalEntity.Protocal createPubAck(String from_user_id, String to_user_id
            , String fingerPrint, boolean bridge) {


        ProtocalEntity.Protocal.Builder builder = ProtocalEntity.Protocal.newBuilder();
        builder.setProtocalType(ProtocalTypeEntity.ProtocalType.PUBACK);
        builder.setDataContent(fingerPrint);
        builder.setFrom(from_user_id);
        builder.setTo(to_user_id);
        builder.setId(ProtocalEntity.Protocal.getDefaultInstance().getId());
        builder.setBridge(bridge);

        ProtocalEntity.Protocal protocal = builder.build();
        return protocal;

    }

    /**
     * 创建心跳请求
     * @param from_user_id   来源用户ID
     * @param data           相关内容
     * @return
     */
    public static ProtocalEntity.Protocal createPingReq(String from_user_id, String data) {
        ProtocalEntity.Protocal.Builder builder = ProtocalEntity.Protocal.newBuilder();
        builder.setProtocalType(ProtocalTypeEntity.ProtocalType.PINGREQ);
        builder.setDataContent(data);
        builder.setFrom(from_user_id);
        builder.setTo(SERVER_ID);
        ProtocalEntity.Protocal protocal = builder.build();
        return protocal;
    }


    /**
     * 创建心跳回应
     * @param to_user_id  目标用户ID
     * @param data        相关内容
     * @return
     */
    public static ProtocalEntity.Protocal createPingResp(String to_user_id, String data) {
        ProtocalEntity.Protocal.Builder builder = ProtocalEntity.Protocal.newBuilder();
        builder.setProtocalType(ProtocalTypeEntity.ProtocalType.PINGRESP);
        builder.setDataContent(data);
        builder.setFrom(SERVER_ID);
        builder.setTo(to_user_id);
        ProtocalEntity.Protocal protocal = builder.build();
        return protocal;
    }


    /**
     * 创建错误返回
     *
     * @param to_user_id   目标用户ID
     * @param data         相关内容
     * @return
     */
    public static ProtocalEntity.Protocal createErrorResp(String to_user_id, String data) {
        ProtocalEntity.Protocal.Builder builder = ProtocalEntity.Protocal.newBuilder();
        builder.setProtocalType(ProtocalTypeEntity.ProtocalType.ERRORESP);
        builder.setDataContent(data);
        builder.setFrom(SERVER_ID);
        builder.setTo(to_user_id);
        ProtocalEntity.Protocal protocal = builder.build();
        return protocal;

    }


    /**
     * 创建断开连接
     *
     * @param from_user_id  来源用户ID
     * @return
     */
    public static ProtocalEntity.Protocal createDisConnect(String from_user_id) {
        ProtocalEntity.Protocal.Builder builder = ProtocalEntity.Protocal.newBuilder();
        builder.setProtocalType(ProtocalTypeEntity.ProtocalType.DISCONNET);
        builder.setFrom(from_user_id);
        builder.setTo(SERVER_ID);
        ProtocalEntity.Protocal protocal = builder.build();
        return protocal;
    }


}
