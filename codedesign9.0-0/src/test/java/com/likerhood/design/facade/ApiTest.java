package com.likerhood.design.facade;

import com.likerhood.design.some_complex_media_library.*;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ApiTest {

    @Test
    public void test_no_design(){
        String fileName = "youtubevideo.ogg";
        String format = "mp4";

        System.out.println("Client: 开始手动处理视频转换...");

        // 1. 加载文件
        VideoFile file = new VideoFile(fileName);

        // 2. 提取源文件的编解码器
        Codec sourceCodec = CodecFactory.extract(file);

        // 3. 确定目标编解码器
        Codec destinationCodec;
        if (format.equals("mp4")) {
            destinationCodec = new MPEG4CompressionCodec();
        } else {
            destinationCodec = new OggCompressionCodec();
        }

        // 4. 读取与转换处理
        VideoFile buffer = BitrateReader.read(file, sourceCodec);
        VideoFile intermediateResult = BitrateReader.convert(buffer, destinationCodec);

        // 5. 修复音频
        File result = (new AudioMixer()).fix(intermediateResult);

        System.out.println("Client: 视频转换完成！");
    }

    @Test
    public void test_facade(){
        VideoConversionFacade converter = new VideoConversionFacade();
        File mp4Video = converter.convertVideo("youtubevideo.ogg", "mp4");
    }



}