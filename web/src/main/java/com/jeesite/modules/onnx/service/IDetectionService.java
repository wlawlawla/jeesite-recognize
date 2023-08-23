package com.jeesite.modules.onnx.service;

import ai.onnxruntime.OrtException;
import com.jeesite.modules.onnx.vo.DetectionSortVO;
import com.jeesite.modules.onnx.yolo.Detection;
import com.jeesite.modules.onnx.yolo.Yolo;
import org.opencv.core.Mat;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IDetectionService {

    /**
     * 识别
     * @param yoloModel
     * @param uploadFile
     * @param size
     * @return
     * @throws OrtException
     * @throws IOException
     */
    List<Detection> recognize(Yolo yoloModel, MultipartFile uploadFile, int size) throws OrtException, IOException;

    /**
     * 识别
     * @param img
     * @param size
     * @param strapType
     * @return
     */
    List<DetectionSortVO> recognize(Mat img, int size, String strapType);

    /**
     * 画出标记框并生成文件
     * @param img
     * @param name
     * @param strapType
     * @param drawList
     */
    void drawAndWriteImage(Mat img, String name, String strapType, List<Detection> drawList);
}
