package com.jeesite.modules.device.web;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jeesite.modules.device.dao.DeviceEntityDao;
import com.jeesite.modules.device.entity.DeviceEntity;
import com.jeesite.modules.device.vo.TreeDataVO;
import com.jeesite.modules.screen.dao.StrapScreenEntityDao;
import com.jeesite.modules.screen.entity.StrapScreenEntity;
import com.jeesite.modules.station.dao.StationEntityDao;
import com.jeesite.modules.station.entity.StationEntity;
import com.twelvemonkeys.imageio.metadata.tiff.IFD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/${adminPath}/station")
public class StationController {

    @Autowired
    private StationEntityDao stationEntityDao;

    @Autowired
    private DeviceEntityDao deviceEntityDao;

    @Autowired
    private StrapScreenEntityDao strapScreenEntityDao;

    @PostMapping("/list")
    @ResponseBody
    public List<TreeDataVO> getStationTree(){
        List<TreeDataVO> treeDataVOList = new ArrayList<>();
        List<StationEntity> stationEntities = stationEntityDao.findAll();
        if (CollectionUtils.isNotEmpty(stationEntities)){
            stationEntities.forEach(stationEntity -> {
                treeDataVOList.add(new TreeDataVO(stationEntity.getStationId() + "", "0", stationEntity.getStationName(), false));
            });

        }
        return treeDataVOList;
    }

    @PostMapping("/device/list")
    @ResponseBody
    public List<TreeDataVO> getDeviceTree(){
        List<TreeDataVO> treeDataVOList = new ArrayList<>();
        List<StationEntity> stationEntities = stationEntityDao.findAll();
        List<DeviceEntity> deviceEntities = deviceEntityDao.findAll();
        if (CollectionUtils.isNotEmpty(stationEntities)){
            Set<Long> stationIds = new HashSet<>();
            if (CollectionUtils.isNotEmpty(deviceEntities)){
                deviceEntities.forEach(deviceEntity -> {
                    stationIds.add(deviceEntity.getStationId());
                    treeDataVOList.add(new TreeDataVO(deviceEntity.getDeviceId() + "", deviceEntity.getStationId() + "", deviceEntity.getDeviceName(), false));
                });
            }

            stationEntities.forEach(stationEntity -> {
                treeDataVOList.add(new TreeDataVO(stationEntity.getStationId() + "", "0", stationEntity.getStationName(), stationIds.contains(stationEntity.getStationId())));
            });
        }
        return treeDataVOList;
    }

    @PostMapping("/device/screen/list")
    @ResponseBody
    public List<TreeDataVO> getScreenTree(){
        List<TreeDataVO> treeDataVOList = new ArrayList<>();
        List<StationEntity> stationEntities = stationEntityDao.findAll();
        List<DeviceEntity> deviceEntities = deviceEntityDao.findAll();
        List<StrapScreenEntity> screenList = strapScreenEntityDao.findAll();
        if (CollectionUtils.isNotEmpty(stationEntities)){
            Set<Long> deviceIds = new HashSet<>();
            if (CollectionUtils.isNotEmpty(screenList)){
                screenList.forEach(screen ->{
                    deviceIds.add(screen.getDeviceId());
                    treeDataVOList.add(new TreeDataVO(screen.getScreenId() + "", screen.getDeviceId() + "", screen.getScreenName(), false));

                });
            }


            Set<Long> stationIds = new HashSet<>();
            if (CollectionUtils.isNotEmpty(deviceEntities)){
                deviceEntities.forEach(deviceEntity -> {
                    stationIds.add(deviceEntity.getStationId());
                    treeDataVOList.add(new TreeDataVO(deviceEntity.getDeviceId() + "", deviceEntity.getStationId() + "", deviceEntity.getDeviceName(), deviceIds.contains(deviceEntity.getDeviceId())));
                });
            }

            stationEntities.forEach(stationEntity -> {
                treeDataVOList.add(new TreeDataVO(stationEntity.getStationId() + "", "0", stationEntity.getStationName(), stationIds.contains(stationEntity.getStationId())));
            });
        }
        return treeDataVOList;
    }


}
