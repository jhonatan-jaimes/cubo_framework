package com.cubo.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class CuboJson {
    
    public static String toJson(Object obj) {
        if (obj == null) return "null";
        
        if (obj instanceof String) {
            return "\"" + escapeJson((String) obj) + "\"";
        } else if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        } else if (obj.getClass().isArray()) {
            return arrayToJson(obj);
        } else if (obj instanceof Collection) {
            return collectionToJson((Collection<?>) obj);
        } else if (obj instanceof Map) {
            return mapToJson((Map<?, ?>) obj);
        } else {
            return objectToJson(obj);
        }
    }
    
    private static String objectToJson(Object obj) {
        StringBuilder json = new StringBuilder("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        
        try {
            boolean first = true;
            for (Field field : fields) {
                field.setAccessible(true);
                
                if (!first) json.append(",");
                first = false;
                
                json.append("\"").append(field.getName()).append("\":");
                Object value = field.get(obj);
                json.append(toJson(value));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error al convertir objeto a JSON", e);
        }
        
        json.append("}");
        return json.toString();
    }
    
    private static String arrayToJson(Object array) {
        StringBuilder json = new StringBuilder("[");
        int length = java.lang.reflect.Array.getLength(array);
        
        for (int i = 0; i < length; i++) {
            if (i > 0) json.append(",");
            Object element = java.lang.reflect.Array.get(array, i);
            json.append(toJson(element));
        }
        
        json.append("]");
        return json.toString();
    }
    
    private static String collectionToJson(Collection<?> collection) {
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        
        for (Object item : collection) {
            if (!first) json.append(",");
            first = false;
            json.append(toJson(item));
        }
        
        json.append("]");
        return json.toString();
    }
    
    private static String mapToJson(Map<?, ?> map) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) json.append(",");
            first = false;
            
            json.append("\"").append(entry.getKey().toString()).append("\":");
            json.append(toJson(entry.getValue()));
        }
        
        json.append("}");
        return json.toString();
    }
    
    private static String escapeJson(String str) {
        if (str == null) return "";
        
        StringBuilder escaped = new StringBuilder();
        for (char c : str.toCharArray()) {
            switch (c) {
                case '"': escaped.append("\\\""); break;
                case '\\': escaped.append("\\\\"); break;
                case '\b': escaped.append("\\b"); break;
                case '\f': escaped.append("\\f"); break;
                case '\n': escaped.append("\\n"); break;
                case '\r': escaped.append("\\r"); break;
                case '\t': escaped.append("\\t"); break;
                default:
                    if (c < 0x20) {
                        escaped.append(String.format("\\u%04x", (int) c));
                    } else {
                        escaped.append(c);
                    }
            }
        }
        return escaped.toString();
    }
}