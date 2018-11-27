package com.ychp.session.utils;


import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author yingchengpeng
 * @date 2018/8/16
 */
@Slf4j
public class SerializableUtils {

	public static byte[] serialize(Object object) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException e) {
			log.error("fail to serialize object by {}, case {}", object, Throwables.getStackTraceAsString(e));
		}
		return bytes;
	}

	public static Object deserialize(byte[] bytes) {
		Object obj;
		try {
			if(bytes == null){
				return null;
			}
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
			return obj;
		} catch (IOException | ClassNotFoundException e) {
			log.error("fail to deserialize object by {}, case {}", bytes, Throwables.getStackTraceAsString(e));
		}
		return null;
	}
}
