package org.potato.AnyThing.phoenix.config.convertor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;
import org.potato.AnyThing.phoenix.config.annotation.JsonRequestParam;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Slf4j
public class JsonStringToListMapArrayObjectConverter implements ConditionalGenericConverter {
    private static ObjectMapper mapper; // json 解析

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        // 判断目标对象是否有JsonRequestParam注解
        JsonRequestParam annotation = targetType.getAnnotation(JsonRequestParam.class);
        return annotation != null;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        // 返回可以被解析的类型
        Set<ConvertiblePair> sets = new HashSet<>();

        sets.add(new ConvertiblePair(String.class, Collection.class));
        sets.add(new ConvertiblePair(String.class, Object[].class));
        sets.add(new ConvertiblePair(String.class, Map.class));
        sets.add(new ConvertiblePair(String.class, Object.class));

        return sets;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        try {
            if (Collection.class.isAssignableFrom(targetType.getType())) {
                CollectionType collectionType = mapper.getTypeFactory().constructCollectionType((Class<? extends Collection>) targetType.getType(), targetType.getElementTypeDescriptor().getObjectType());
                return mapper.readValue(source.toString(), collectionType);
            } else if (targetType.getType().isArray()) {
                ArrayType arrayType = mapper.getTypeFactory().constructArrayType(targetType.getElementTypeDescriptor().getObjectType());
                return mapper.readValue(source.toString(), arrayType);
            } else if (Map.class.isAssignableFrom(targetType.getType())) {
                MapType mapType = mapper.getTypeFactory().constructMapType((Class<? extends Map>) targetType.getType(), targetType.getMapKeyTypeDescriptor().getType(), targetType.getMapValueTypeDescriptor().getType());
                return mapper.readValue(source.toString(), mapType);
            } else {
                return mapper.readValue(source.toString(), targetType.getType());
            }
        } catch (Exception e) {
            throw new IllegalStateException("JSON解析失败！");
        }
    }
}
