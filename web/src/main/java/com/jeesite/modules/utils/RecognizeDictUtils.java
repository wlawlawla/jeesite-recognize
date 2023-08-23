package com.jeesite.modules.utils;

import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.modules.station.dao.StationEntityDao;
import com.jeesite.modules.station.entity.StationEntity;
import com.jeesite.modules.sys.entity.DictData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecognizeDictUtils {

    @Autowired
    private StationEntityDao stationEntityDao;

    private  List<DictData> stationVO = new ArrayList<>();

    private  Map<Long, String> stationNameMap = new ConcurrentHashMap<>();

    private ReentrantLock stationLock = new ReentrantLock();

    public Map<Long, String> getStationNameMap(){
        Map<Long, String> stationMap = new HashMap<>();
        try {
            stationLock.lock();
            stationMap.putAll(stationNameMap);
        }catch (Exception e){

        }finally {
            stationLock.unlock();
        }
        return stationMap;
    }


/*    public static String getStationListJson() {
        String r = null;
        try {
            r = JsonMapper.getInstance().writerWithView(DictData.StatusView.class).writeValueAsString(stationVO);
        }catch (Exception e){

        }
        return r;
    }*/


    @PostConstruct
    public void updateStationMap(){
        List<StationEntity> stationInfoEntityList = stationEntityDao.findAll();
        try {
            stationLock.lock();
            stationNameMap.clear();
            stationNameMap.putAll(stationInfoEntityList.stream().collect(Collectors.toMap(a -> a.getStationId(), StationEntity::getStationName)));
/*            stationVO.removeAll(stationVO);
            stationInfoEntityList.forEach(stationInfoEntity -> {
                DictData dictData = new DictData();
                dictData.setDictLabel(stationInfoEntity.getStationName());
                dictData.setDictValue(stationInfoEntity.getStationId() + "");
                stationVO.add(dictData);
            });*/
         }catch (Exception e){
            e.printStackTrace();
        }finally {
            stationLock.unlock();
        }

    }

}
