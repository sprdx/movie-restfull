package id.supriadi.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class MainUtilTest {

    private static Stream<ArrayList<Object>> parameterTestGetTimestampProvider() {
        // Test Case Name | Json | Expected Not Null
        ArrayList<Object> case1 = new ArrayList<>(Arrays.asList("givenValidTimestamp_WillReturnTimestamp", "2022-08-01 10:56:31", Boolean.TRUE));
        ArrayList<Object> case2 = new ArrayList<>(Arrays.asList("givenValidTimestamp_WillReturnNull", "01-08-2022 10:56", Boolean.FALSE));

        return Stream.of(case1, case2);
    }

    @ParameterizedTest
    @MethodSource("parameterTestGetTimestampProvider")
    void testGetTimestamp(ArrayList<Object> caseValues) {
        // Arrange
        String stringTimestamp = (String) caseValues.get(1);

        // Act
        Timestamp timetamp = MainUtil.getTimestamp(stringTimestamp);

        // Assert
        Assertions.assertEquals(caseValues.get(2), (timetamp != null));
    }
}
