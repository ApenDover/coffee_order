package ts.andrey.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс для проверки заполненности полей объекта
 */
@UtilityClass
public class TestReflectionUtils {

    public static void assertAllFieldsNotNull(Object obj) {
        final var nullProperties = getNullProperties(obj);
        assertEquals(0, nullProperties.size(),
                "null properties are expected to be initialized: " + nullProperties
        );
    }

    public static void assertAllFieldsNotNullWithExclude(Object obj, String... excludeFieldNameList) {
        assertAllFieldsNotNullWithExclude(obj, Arrays.stream(excludeFieldNameList).toList());
    }

    public static void assertAllFieldsNotNullWithExclude(Object obj, List<String> excludeFieldNameList) {
        final var nullProperties = getNullProperties(obj).entrySet()
                .stream()
                .filter(it -> !excludeFieldNameList.contains(it.getKey()))
                .collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), HashMap::putAll);
        assertEquals(0, nullProperties.size(), "null properties are expected to be initialized: " + nullProperties);
    }

    @SneakyThrows
    private static Map<String, Object> getNullProperties(final Object o) {
        final Map<String, Object> nullProperties = new TreeMap<>();

        final BeanInfo beanInfo = Introspector.getBeanInfo(o.getClass());
        for (final PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

            final var propertyValue = descriptor.getReadMethod()
                    .invoke(o);
            if (propertyValue == null) {
                nullProperties.put(descriptor.getName(), null);
            }

        }

        return nullProperties;
    }

}
