package com.aihouse.aihousecore.utils;

import org.springframework.util.SerializationUtils;

import java.io.ObjectStreamConstants;
import java.util.Arrays;

/**
 * 对象字节互转工具类
 */
public class ObjectToByteUtil {
    /**
     * null 序列化字节数组
     */
    private static final byte[] NULL_BYTES;

    static {
        final int length = (Short.BYTES << 1) + Byte.BYTES;
        final int mask = 0xFF;
        NULL_BYTES = new byte[length];
        NULL_BYTES[0] = (byte) (ObjectStreamConstants.STREAM_MAGIC >>> 8 & mask);
        NULL_BYTES[1] = (byte) (ObjectStreamConstants.STREAM_MAGIC & mask);
        NULL_BYTES[2] = (byte) (ObjectStreamConstants.STREAM_VERSION >>> 8 & mask);
        NULL_BYTES[3] = (byte) (ObjectStreamConstants.STREAM_VERSION & mask);
        NULL_BYTES[4] = ObjectStreamConstants.TC_NULL;
    }
    /**
     * 字节转对象
     */
    public static Object byteToObject(byte[] bytes) {
        return Arrays.equals(bytes, NULL_BYTES)
                ? null
                : SerializationUtils.deserialize(bytes);
    }

    /**
     * 对象转字节
     */
    public static byte[] objectToByte(Object obj) {
        return obj == null
                ? NULL_BYTES
                : SerializationUtils.serialize(obj);
    }
}
