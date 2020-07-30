package com.zxelec.yhkk.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
/**
 * 解析json文件
 * @author liu.yongquan
 *
 */
public class JsonUtils {
	
	private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	
	public static String jsonRead(File file){
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }
	
	/**
	 * 
	 * @param name 文件名称
	 * @param obj  数据对象
	 */
	public static void writeReceiveJson(String name,Object obj) {
		Lock writeLock = readWriteLock.writeLock();
		try {
			writeLock.lock();
			String jsonStr = JSONObject.toJSONString(obj);
			File paths = new File(ResourceUtils.getURL("classpath:").getPath());
			Path rootLocation = Paths.get(paths.getPath());
			if (Files.notExists(rootLocation)) {
				Files.createDirectories(rootLocation);
			}
			String fileName = name+".json";
			Path path = rootLocation.resolve(fileName);
			if (!StringUtils.isEmpty(jsonStr)) {
				byte[] strToBytes = jsonStr.getBytes(Charset.defaultCharset());
				Files.write(path, strToBytes);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			writeLock.unlock();
		}
	}
	
	
}

