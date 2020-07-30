package com.zxelec.yhkk.utils;

import com.zxelec.yhkk.entity.CarpassPushEntity;
import com.zxelec.yhkk.entity.MotionVehicleType;
import com.zxelec.yhkk.entity.VehiclePictureType;
import com.zxelec.yhkk.entity.vc.MotorVehicleObject;
import com.zxelec.yhkk.entity.vc.VcSubImageInfoObject;
import com.zxelec.yhkk.entity.vc.VcSubImageList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: dily
 * @create: 2019-07-12 10:25
 * 过车记录转数据结构
 **/
public class CarpassPush2 {


    private static Logger logger= LogManager.getLogger(CarpassPush2.class);
    /**
     * 转kafka数据结构
     * @param carpass
     * @return
     */
    public static MotionVehicleType carpassPush2MotionVehicle(CarpassPushEntity carpass) {
        MotionVehicleType motionVehicle = new MotionVehicleType();
        if (carpass != null) {
            motionVehicle.setId(UUID.randomUUID().toString());
            motionVehicle.setMotorVehicleID(carpass.getMotorVehicleID());
            motionVehicle.setTollgateID(carpass.getTransportID());
            /** 设备ID **/
            motionVehicle.setDeviceID(carpass.getDeviceID());
            motionVehicle.setPassTime(carpass.getPassTime());
            /** 车牌 **/
            if (carpass.getPlateNo() != null) {
                motionVehicle.setHasPlate(true);
                motionVehicle.setPlateNo(carpass.getPlateNo());
                motionVehicle.setPlateClass(carpass.getPlateClass());
                motionVehicle.setPlateColor(carpass.getPlateColor());
            } else {
                logger.error("无车牌");
            }
            motionVehicle.setSpeed(carpass.getSpeed());
            motionVehicle.setDirection(carpass.getDirection());
            /**车辆信息**/
            motionVehicle.setVehicleClass(carpass.getVehicleClass());
            motionVehicle.setVehicleBrand(carpass.getVehicleBrand());
            motionVehicle.setVehicleLength(String.valueOf(carpass.getVehicleLength()));
            motionVehicle.setVehicleWidth(carpass.getVehicleWidth());
            motionVehicle.setVehicleHeight(carpass.getVehicleHeight());
            motionVehicle.setVehicleColor(carpass.getVehicleColor());
            motionVehicle.setVehicleColorDepth(carpass.getVehicleColorDepth());
            /** 车道号 与方向有关 **/
            motionVehicle.setLaneNo(carpass.getLaneId());
            /** 照片 **/
            List<VehiclePictureType> pictures=new ArrayList<>();
            for (int i = 0; i <=4; i++) {
                VehiclePictureType picture =new  VehiclePictureType();
                if (i == 0&& carpass.getStorageUrl1() != null) {
                    picture.setFileRef(carpass.getStorageUrl1());
                    picture.setFmt("@"+carpass.getStorageUrl1().substring(picture.getFileRef().length() - 4));
                    if (!picture.getFileRef().isEmpty())
                        picture.setType(Integer.toString(i));
                    picture.setShotTime(carpass.getPassTime());
                    pictures.add(picture);
                }
                else if (i == 1 && carpass.getStorageUrl2() !=null) {
                    picture.setFileRef(carpass.getStorageUrl2());
                    picture.setFmt("@" + carpass.getStorageUrl2().substring(picture.getFileRef().length() - 4));
                    if (!picture.getFileRef().isEmpty())
                        picture.setType(Integer.toString(i));
                    picture.setShotTime(carpass.getPassTime());
                    pictures.add(picture);
                }
                else if (i == 2 && carpass.getStorageUrl3() !=null){
                    picture.setFileRef(carpass.getStorageUrl3());
                    picture.setFmt("@" + carpass.getStorageUrl3().substring(picture.getFileRef().length() - 4));
                    if (!picture.getFileRef().isEmpty())
                        picture.setType(Integer.toString(i));
                    picture.setShotTime(carpass.getPassTime());
                    pictures.add(picture);
                }
                else if (i == 3 && carpass.getStorageUrl4() != null){
                    picture.setFileRef(carpass.getStorageUrl4());
                    picture.setFmt("@" + carpass.getStorageUrl4().substring(picture.getFileRef().length() - 4));
                    if (!picture.getFileRef().isEmpty())
                        picture.setType(Integer.toString(i));
                    picture.setShotTime(carpass.getPassTime());
                    pictures.add(picture);
                }
                else if (i == 4 && carpass.getStorageUrl5() !=null){
                    picture.setFileRef(carpass.getStorageUrl5());
                    picture.setFmt("@" + carpass.getStorageUrl5().substring(picture.getFileRef().length() - 4));
                    if (!picture.getFileRef().isEmpty())
                        picture.setType(Integer.toString(i));
                    picture.setShotTime(carpass.getPassTime());
                    pictures.add(picture);
                }

            }
            motionVehicle.setPictures(pictures);
            /** 号牌数量 默认为1 **/
            motionVehicle.setPlateNumber(1);
        }
        return motionVehicle;
    }



    /**
     * 转视图库数据结构
     * @param carpass
     * @return
     */
    public static MotorVehicleObject carpass2Vc(CarpassPushEntity carpass){
        MotorVehicleObject motorVehicle = new MotorVehicleObject();
        if (carpass!=null){
            /** 过车记录ID **/
            if (carpass.getMotorVehicleID()!=null&&carpass.getMotorVehicleID().length()<=48)
                motorVehicle.setMotorVehicleID(carpass.getMotorVehicleID());
            else logger.error("过车记录ID"+carpass.getMotorVehicleID()+"超出位数限制");

            /** 采集信息分类  0：其他   1：自动采集   2：人工采集 **/
            motorVehicle.setInfoKind(1);

            /** 来源标识 **/
            if (carpass.getSourceID()!=null&&carpass.getSourceID().length()<=33)
                motorVehicle.setSourceID(carpass.getSourceID());
            else logger.error("来源标识"+carpass.getSourceID()+"超出位数限制");

            /** 关联卡口ID **/
            if (carpass.getTransportID()!=null&&carpass.getTransportID().length()==20)
                motorVehicle.setTollgateID(carpass.getTransportID());
            else logger.error("卡口ID"+carpass.getTransportID()+"超出位数限制");

            /** 设备ID **/
            if (carpass.getDeviceID()!=null&&carpass.getDeviceID().length()==20)
                motorVehicle.setDeviceID(carpass.getDeviceID());
            else logger.error("设备ID"+carpass.getDeviceID()+"超出位数限制");

            /** 照片信息 **/
            VcSubImageList subImageList=new VcSubImageList();
            List<VcSubImageInfoObject> subImageInfoList = new ArrayList<>();
            VcSubImageInfoObject subImageInfoObject = new VcSubImageInfoObject();
            if (carpass.getStorageUrl1()!=null&&carpass.getStorageUrl1().length()>1) {
                try {
                    URL url = new URL(carpass.getStorageUrl1());
                    URLConnection connection = url.openConnection();
                    connection.setDoOutput(true);
                    BufferedImage image = ImageIO.read(connection.getInputStream());
                    subImageInfoObject.setData(HttpUtil.URLtoImageBase64(carpass.getStorageUrl1()));
                    subImageInfoObject.setDeviceID(motorVehicle.getDeviceID());
                    subImageInfoObject.setImageID("1");
                    subImageInfoObject.setHeight(image.getHeight());
                    subImageInfoObject.setWidth(image.getWidth());
                    subImageInfoObject.setFileFormat(carpass.getStorageUrl1().substring(carpass.getStorageUrl1().length()-4));
                    subImageInfoObject.setShotTime(carpass.getPassTime());
                    subImageInfoObject.setEventSort(motorVehicle.getInfoKind());
                    subImageInfoObject.setType("14");
                    subImageInfoList.add(subImageInfoObject);
                }catch (MalformedURLException e){
                    logger.error("图片 1 地址错误"+carpass.getStorageUrl1());
                }catch (IOException e){
                    logger.error("图片 1 下载失败"+carpass.getStorageUrl1());
                }
                motorVehicle.setStorageUrl1(carpass.getStorageUrl1());
            }
            else logger.error("图片1"+carpass.getStorageUrl1()+"格式不正确");
            if (carpass.getStorageUrl2()!=null&&carpass.getStorageUrl2().length()>1){
                try {
                    URL url = new URL(carpass.getStorageUrl2());
                    URLConnection connection = url.openConnection();
                    connection.setDoOutput(true);
                    BufferedImage image = ImageIO.read(connection.getInputStream());
                    subImageInfoObject.setData(HttpUtil.URLtoImageBase64(carpass.getStorageUrl2()));
                    subImageInfoObject.setDeviceID(motorVehicle.getDeviceID());
                    subImageInfoObject.setImageID("2");
                    subImageInfoObject.setHeight(image.getHeight());
                    subImageInfoObject.setWidth(image.getWidth());
                    subImageInfoObject.setFileFormat(carpass.getStorageUrl2().substring(carpass.getStorageUrl2().length()-4));
                    subImageInfoObject.setShotTime(carpass.getPassTime());
                    subImageInfoObject.setEventSort(motorVehicle.getInfoKind());
                    subImageInfoObject.setType("09");
                    subImageInfoList.add(subImageInfoObject);
                }catch (MalformedURLException e){
                    logger.error("图片 2 地址错误"+carpass.getStorageUrl2());
                }catch (IOException e){
                    logger.error("图片 2 下载失败"+carpass.getStorageUrl2());
                }
                motorVehicle.setStorageUrl2(carpass.getStorageUrl2());
            }
            if (carpass.getStorageUrl3()!=null&&carpass.getStorageUrl3().length()>1){
                try {
                    URL url = new URL(carpass.getStorageUrl3());
                    URLConnection connection = url.openConnection();
                    connection.setDoOutput(true);
                    BufferedImage image = ImageIO.read(connection.getInputStream());
                    subImageInfoObject.setData(HttpUtil.URLtoImageBase64(carpass.getStorageUrl3()));
                    subImageInfoObject.setDeviceID(motorVehicle.getDeviceID());
                    subImageInfoObject.setImageID("3");
                    subImageInfoObject.setHeight(image.getHeight());
                    subImageInfoObject.setWidth(image.getWidth());
                    subImageInfoObject.setFileFormat(carpass.getStorageUrl3().substring(carpass.getStorageUrl3().length()-4));
                    subImageInfoObject.setShotTime(carpass.getPassTime());
                    subImageInfoObject.setEventSort(motorVehicle.getInfoKind());
                    subImageInfoObject.setType("14");
                    subImageInfoList.add(subImageInfoObject);
                }catch (MalformedURLException e){
                    logger.error("图片 3 地址错误"+carpass.getStorageUrl3());
                }catch (IOException e){
                    logger.error("图片 3 下载失败"+carpass.getStorageUrl3());
                }
                motorVehicle.setStorageUrl3(carpass.getStorageUrl3());
            }
            if (carpass.getStorageUrl4()!=null&&carpass.getStorageUrl4().length()<=256){
                try {
                    URL url = new URL(carpass.getStorageUrl4());
                    URLConnection connection = url.openConnection();
                    connection.setDoOutput(true);
                    BufferedImage image = ImageIO.read(connection.getInputStream());
                    subImageInfoObject.setData(HttpUtil.URLtoImageBase64(carpass.getStorageUrl4()));
                    subImageInfoObject.setDeviceID(motorVehicle.getDeviceID());
                    subImageInfoObject.setImageID("4");
                    subImageInfoObject.setHeight(image.getHeight());
                    subImageInfoObject.setWidth(image.getWidth());
                    subImageInfoObject.setFileFormat(carpass.getStorageUrl4().substring(carpass.getStorageUrl4().length()-4));
                    subImageInfoObject.setShotTime(carpass.getPassTime());
                    subImageInfoObject.setEventSort(motorVehicle.getInfoKind());
                    subImageInfoObject.setType("100");
                    subImageInfoList.add(subImageInfoObject);
                }catch (MalformedURLException e){
                    logger.error("图片 4 地址错误"+carpass.getStorageUrl4());
                }catch (IOException e){
                    logger.error("图片 4 下载失败"+carpass.getStorageUrl4());
                }
                motorVehicle.setStorageUrl4(carpass.getStorageUrl4());
            }
            if (carpass.getStorageUrl5()!=null&&carpass.getStorageUrl5().length()<=256){
                try {
                    URL url = new URL(carpass.getStorageUrl5());
                    URLConnection connection = url.openConnection();
                    connection.setDoOutput(true);
                    BufferedImage image = ImageIO.read(connection.getInputStream());
                    subImageInfoObject.setData(HttpUtil.URLtoImageBase64(carpass.getStorageUrl5()));
                    subImageInfoObject.setDeviceID(motorVehicle.getDeviceID());
                    subImageInfoObject.setImageID("5");
                    subImageInfoObject.setHeight(image.getHeight());
                    subImageInfoObject.setWidth(image.getWidth());
                    subImageInfoObject.setFileFormat(carpass.getStorageUrl5().substring(carpass.getStorageUrl5().length()-4));
                    subImageInfoObject.setShotTime(carpass.getPassTime());
                    subImageInfoObject.setEventSort(motorVehicle.getInfoKind());
                    subImageInfoObject.setType("100");
                    subImageInfoList.add(subImageInfoObject);
                }catch (MalformedURLException e){
                    logger.error("图片 5 地址错误"+carpass.getStorageUrl5());
                }catch (IOException e){
                    logger.error("图片 5 下载失败"+carpass.getStorageUrl5());
                }
                motorVehicle.setStorageUrl5(carpass.getStorageUrl5());
            }
            subImageList.setSubImageInfoObject(subImageInfoList);
            motorVehicle.setSubImageList(subImageList);

            /** 坐标 **/
            motorVehicle.setLeftTopX(carpass.getLeftTopX());
            motorVehicle.setLeftTopY(carpass.getLeftTopY());
            motorVehicle.setRightBtmX(carpass.getRightBtmX());
            motorVehicle.setRightBtmY(carpass.getRightBtmY());

            /** 位置时间 **/
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            motorVehicle.setPassTime(formatter.format(carpass.getPassTime()));
            if (motorVehicle.getInfoKind()==2){    //人工采集时才有
                motorVehicle.setMarkTime(carpass.getPassTime());
                motorVehicle.setAppearTime(carpass.getPassTime());
                motorVehicle.setDisappearTime(carpass.getPassTime());
            }

            /** 车道号 **/
            motorVehicle.setLaneNo(carpass.getLaneId());

            /** 车牌 **/
            if (carpass.getPlateNo()=="未识别")
                motorVehicle.setHasPlate(0);
            else if (carpass.getPlateNo()!=null){
                motorVehicle.setHasPlate(1);
            }
            else
                logger.error("有无车牌"+carpass.getPlateNo()+"车牌不规范");
            if (carpass.getPlateClass().length()==2)
                motorVehicle.setPlateClass(carpass.getPlateClass());
            else if (carpass.getPlateClass().length()==1&&Integer.valueOf(carpass.getPlateClass())!=0){
                motorVehicle.setPlateClass("0"+carpass.getPlateClass());
            }
            else{
                motorVehicle.setPlateClass("99");
                logger.error("车牌种类"+carpass.getPlateClass()+"超出位数限制");
            }
            if (carpass.getPlateColor()!=null&&carpass.getPlateColor().length()<=2&&Integer.valueOf(carpass.getPlateColor())!=0)
                motorVehicle.setPlateColor(carpass.getPlateColor());
            else {
                motorVehicle.setPlateColor("99");
            }
            if (carpass.getPlateNo().length()<=15)
                motorVehicle.setPlateNo(carpass.getPlateNo());
            else logger.error("车牌号"+carpass.getPlateNo()+"超出位数限制");
            if (carpass.getPlaceCode().length()<=15)
                motorVehicle.setPlateNoAttach(carpass.getPlaceCode());
            else logger.error("挂车车牌号"+carpass.getPlaceCode()+"超出位数限制");

            /** 行驶速度 **/
            motorVehicle.setSpeed(carpass.getSpeed());

            /** 行驶方向 **/
            String strDirection = carpass.getDirection().trim();
            try{
                int iDir = Integer.valueOf(strDirection);
                if(iDir<1 || iDir>9){
                    strDirection = "9";
                }
            } catch (Exception ex){
                strDirection = "9";
                logger.error(strDirection + "车辆行驶方向转换int类型失败");
            }
            motorVehicle.setDirection(strDirection);

            /** 车辆尺寸 **/
            if (carpass.getVehicleLength()<100000)
                motorVehicle.setVehicleLength(carpass.getVehicleLength());
            else logger.error("车辆长度超出");
            if (carpass.getVehicleWidth()<10000)
                motorVehicle.setVehicleWidth(carpass.getVehicleWidth());
            else logger.error("车辆宽度超出");
            if (carpass.getVehicleHeight()<10000)
                motorVehicle.setVehicleHeight(carpass.getVehicleHeight());
            else logger.error("车辆高度超出");

            /** 车辆颜色 **/
            if (carpass.getVehicleColor()!=null&&carpass.getVehicleColor().length()<2&&Integer.valueOf(carpass.getPlateColor())!=0)
                motorVehicle.setVehicleColor(carpass.getVehicleColor());
            else
                motorVehicle.setVehicleColor("99");

            /** 车辆颜色深浅 **/
            Integer iVehicleColorDepth = carpass.getVehicleColorDepth();
            if (iVehicleColorDepth==null || iVehicleColorDepth>1 ||iVehicleColorDepth<0){
                motorVehicle.setVehicleColorDepth("0");
            }
            else {
                motorVehicle.setVehicleColorDepth(iVehicleColorDepth.toString());
            }
        }
        return motorVehicle;
    }
}